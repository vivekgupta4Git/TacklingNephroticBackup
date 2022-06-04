package com.ruviapps.tacklingnephrotic.database.entities

import androidx.room.*
import androidx.room.ForeignKey.Companion.CASCADE
import java.util.*


@Entity(tableName = TableName.FrequencyTable)
data class Frequency(
    @PrimaryKey(true)
    @ColumnInfo(ColumnFrequencyCode)
    val frequencyCode: Long,
    @ColumnInfo(ColumnFrequencyName)
    val name: String
){
    companion object{
        const val ColumnFrequencyCode="frequency_code"
        const val ColumnFrequencyName = "frequency_name"
    }
}



/**
 * @Transaction
 * @Query("Select * from Patient)
 * fun getPatientWithStates() : List<PatientWithStates>
 */

@Entity(tableName = TableName.SideEffectTable)
data class SideEffect(
    @PrimaryKey(true)
    @ColumnInfo(ColumnSideEffectId)
    val sideEffectId :Long,
    @ColumnInfo(ColumnsSideEffectName)
    val name : String
    )
{
    companion object{
        const val ColumnSideEffectId = "side_effect_id"
        const val ColumnsSideEffectName = "side_effect_name"
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
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(ColumnEntryId)
    val entryId : Long,
    @ColumnInfo(ColumnPatientId,index=true)
    val patientId: Long,
    @ColumnInfo(ColumnSideEffectId,index=true)
    val sideEffectId: Long,
    @ColumnInfo(ColumnFurtherExplanation)
    val furtherExplanation : String = "",
    @ColumnInfo(ColumnDateRecorded)
    val dateRecorded: Date

){
    companion object{
        const val ColumnEntryId = "entry_id"
        const val ColumnPatientId = "patient_id"
        const val ColumnSideEffectId = "side_effect_id"
        const val ColumnDateRecorded = "date_recorded"
        const val ColumnFurtherExplanation= "explanation"
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
    @ColumnInfo(ColumnPatientId,index=true)
    val patientId : Long,
    @ColumnInfo(ColumnCareTakerId,index=true)
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
    entity = Medicines::class,
    parentColumns = [Medicines.ColumnMedicineCode],
    childColumns = [MedicinesGivenDetails.ColumnMedicineId]
),ForeignKey(
    entity = MedicineUnit::class,
    parentColumns = [MedicineUnit.ColumnUnitId],
    childColumns = [MedicinesGivenDetails.ColumnMedicineUnitId],
    onDelete = CASCADE
)])
data class MedicinesGivenDetails(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(ColumnMedicineGivesDetailsId)
    val givenDetailsId : Long,
    @ColumnInfo(ColumnAdministeredId,index=true)
    val administeredId: Long,
    @ColumnInfo(ColumnMedicineId,index=true)
    val medicineId : Long,
    @ColumnInfo(ColumnDateRecorded)
    val dateRecorded : Date,
    @ColumnInfo(ColumnQuantity)
    val quantity : Int,
    @ColumnInfo(ColumnMedicineUnitId,index=true)
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

data class MedicinesAdministeredWithMedicinesGivenDetails(
    @Embedded
    val medicinesAdministered: MedicinesAdministered,
    @Relation(
        parentColumn = MedicinesAdministered.ColumnAdministeredId,
        entityColumn = MedicinesGivenDetails.ColumnAdministeredId
    )
    val medicinesGivenDetails : List<MedicinesGivenDetails>
)
