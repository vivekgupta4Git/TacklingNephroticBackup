package com.ruviApps.tacklingnephrotic.database.entities

import androidx.room.*
import androidx.room.ForeignKey.Companion.CASCADE
import java.util.*


@Entity(tableName = TableName.CaretakerTable)
data class DatabaseCareTaker(
    @PrimaryKey(true)
    @ColumnInfo(ColumnCareTakerId)
    val ctId : Long,
    @Embedded val fullName: FullName,
    @Embedded val contact : ContactInfo,

    ){
    companion object{
        const val ColumnCareTakerId = "caretaker_id"
    }
}

data class ContactInfo(
    @ColumnInfo("primary_contact")
    val primaryContact: Int,
    @ColumnInfo("secondary_contact")
    val secondaryContact : Int?,
    @ColumnInfo("email")
    val email: String?

)


@Entity(tableName = TableName.DoctorsTable)
data class DatabaseDoctor(
    @PrimaryKey(true)
    @ColumnInfo(ColumnDoctorId)
    val doctorId : Long,
    @Embedded
    val fullName: FullName,
    @Embedded
    val contactInfo: ContactInfo,
    ){
    companion object{
        const val ColumnDoctorId = "doctor_id"
    }
}

@Entity(tableName = TableName.MedicinesTable)
data class DatabaseMedicine(
    @PrimaryKey(true)
    @ColumnInfo(ColumnMedicineCode)
    val medicineCode : Long,
    @ColumnInfo("medicine_name")
    val medicineName : String,

){
    companion object{
        const val ColumnMedicineCode = "medicine_code"
    }
}

@Entity(tableName = TableName.DiseasesTable)
data class DatabaseDiseases(
    @PrimaryKey(true)
    @ColumnInfo(ColumnDiseaseCode)
    val diseaseCode : Long,
    @ColumnInfo("disease_name")
    val diseaseName : String,
){
    companion object{
        const val ColumnDiseaseCode = "diseases_code"
    }
}



@Entity(tableName = TableName.MedicineUnitTable)
data class MedicineUnit(
    @PrimaryKey(true)
    @ColumnInfo(ColumnUnitId)
    val unitID : Long,
    @ColumnInfo("unit_name")
    val unitName : String
){
    companion object{
        const val ColumnUnitId = "unit_id"
    }
}

@Entity(tableName = TableName.FrequencyTable)
data class Frequency(
    @PrimaryKey(true)
    @ColumnInfo(ColumnFrequencyCode)
    val frequencyCode: Long,
    val name: String
){
    companion object{
        const val ColumnFrequencyCode="frequency_code"
    }
}

@Entity(tableName = TableName.RelapsesTable, foreignKeys = [ForeignKey(
    entity = DatabasePatient::class,
    parentColumns = [DatabasePatient.ColumnPatientId],
    childColumns = [DatabaseRelapse.ColumnPatientId],
    onDelete = CASCADE
)])
data class DatabaseRelapse(
    @PrimaryKey(true)
    @ColumnInfo(ColumnRelapseId)
    val relapseId : Long,
    @ColumnInfo(ColumnPatientId)
    val patientId : Long,
    @ColumnInfo("start_date")
    val startDate : Date,
    @ColumnInfo("end_date")
    val endDate : Date

){
    companion object{
        const val ColumnRelapseId ="relapse_id"
        const val ColumnPatientId = "to_patient"
    }
}

data class PatientWithRelapses(
    @Embedded val patient: DatabasePatient,
    @Relation(
        parentColumn = DatabasePatient.ColumnPatientId,
        entityColumn = DatabaseRelapse.ColumnPatientId
    )
    val relapses : List<DatabaseRelapse>
)

/**
 * @Transaction
 * @Query("Select * from Patient)
 * fun getPatientWithRelapses() : List<PatientWithRelapses>
 */

@Entity(tableName = TableName.SideEffectTable)
data class SideEffect(
    @PrimaryKey(true)
    @ColumnInfo(ColumnSideEffectId)
    val sideEffectId :Long,
    val name : String
    )
{
    companion object{
        const val ColumnSideEffectId = "side_effect_id"
    }
}


@Entity(tableName = TableName.SideEffectToPatientTable, foreignKeys = [ForeignKey(
    entity = DatabasePatient::class,
    parentColumns = [DatabasePatient.ColumnPatientId],
    childColumns = [SideEffectToPatient.ColumnPatientId],
    onDelete = CASCADE
),ForeignKey(
    entity = SideEffect::class,
    parentColumns = [SideEffect.ColumnSideEffectId],
    childColumns = [SideEffectToPatient.ColumnSideEffectId],
    onDelete = CASCADE
)])
data class SideEffectToPatient(
    val entryId : Long,
    val patientId: Long,
    val sideEffectId: Long,
    val furtherExplanation : String = "",
    val dateRecorded: Date

){
    companion object{
        const val ColumnEntryId = "entry_id"
        const val ColumnPatientId = "patient_id"
        const val ColumnSideEffectId = "side_effect_id"
    }
}


@Entity(tableName = TableName.MedicinesAdministeredTable, foreignKeys = [ForeignKey(
    entity = DatabasePatient::class,
    parentColumns = [DatabasePatient.ColumnPatientId],
    childColumns = [MedicinesAdministered.ColumnPatientId],
    onDelete = CASCADE
),ForeignKey(
    entity = DatabaseCareTaker::class,
    parentColumns = [DatabaseCareTaker.ColumnCareTakerId],
    childColumns = [MedicinesAdministered.ColumnCareTakerId],
    onDelete = CASCADE
)])
data class MedicinesAdministered(

    @PrimaryKey(true)
    @ColumnInfo(ColumnAdministeredId)
    val administeredId : Long,
    @ColumnInfo(ColumnPatientId)
    val patientId : Long,
    @ColumnInfo(ColumnCareTakerId)
    val careTakerId : Long

    )
{
        companion object MedicineAdministeredColumns{
            const val ColumnAdministeredId = "administered_id"
            const val ColumnPatientId = "patient_id"
            const val ColumnCareTakerId = "caretaker_id"
    }
}

@Entity(tableName = TableName.MedicinesGivenDetailsTable, foreignKeys = [
    ForeignKey(
        entity = MedicinesAdministered::class,
        parentColumns = [MedicinesAdministered.ColumnAdministeredId],
        childColumns = [MedicinesGivenDetails.ColumnAdministeredId],
        onDelete = CASCADE
    ),ForeignKey(
    entity = DatabaseMedicine::class,
    parentColumns = [DatabaseMedicine.ColumnMedicineCode],
    childColumns = [MedicinesGivenDetails.ColumnMedicineId]
),ForeignKey(
    entity = MedicineUnit::class,
    parentColumns = [MedicineUnit.ColumnUnitId],
    childColumns = [MedicinesGivenDetails.ColumnMedicineUnitId],
    onDelete = CASCADE
)])
data class MedicinesGivenDetails(
    @ColumnInfo(ColumnMedicineGivesDetailsId)
    val givenDetailsId : Long,
    @ColumnInfo(ColumnAdministeredId)
    val administeredId: Long,
    @ColumnInfo(ColumnMedicineId)
    val medicineId : Long,
    @ColumnInfo(ColumnDateRecorded)
    val dateRecorded : Date,
    @ColumnInfo(ColumnQuantity)
    val quantity : Int,
    @ColumnInfo(ColumnMedicineUnitId)
    val medicineUnitId :Long,
    val remarks: String = ""
    ){
    companion object{
        const val ColumnMedicineGivesDetailsId = "given_details_id"
        const val ColumnMedicineId = "medicine_id"
        const val ColumnDateRecorded = "date_recorded"
        const val ColumnQuantity = "qty"
        const val ColumnMedicineUnitId = "medicine_unit"
        const val ColumnAdministeredId = "administered_id"
    }
}


