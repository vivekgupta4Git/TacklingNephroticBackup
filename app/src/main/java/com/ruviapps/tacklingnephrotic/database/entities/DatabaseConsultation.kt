package com.ruviapps.tacklingnephrotic.database.entities

import androidx.room.*
import androidx.room.ForeignKey.Companion.CASCADE
import java.util.*


@Entity(tableName = TableName.ConsultationTable, foreignKeys = [ForeignKey(
    entity = DatabasePatient::class,
    parentColumns = [DatabasePatient.ColumnPatientId],
    childColumns = [DatabaseConsultation.ColumnPatientId],
    onDelete = CASCADE
),ForeignKey(
    entity = Doctor::class,
    parentColumns = [Doctor.ColumnDoctorId],
    childColumns = [DatabaseConsultation.ColumnDoctorId],
    onDelete = CASCADE
)])
data class DatabaseConsultation(
    @PrimaryKey(true)
    @ColumnInfo(ColumnConsultId)
    val consultId : Long,
    @ColumnInfo(ColumnPatientId,index=true)
    val patientId : Long,
    @ColumnInfo(ColumnVisitDate)
    val visitDate : Date,
    @ColumnInfo(ColumnDoctorId,index=true)
    val consultedDoctorId : Long

){
    companion object{
        const val ColumnConsultId = "consult_id"
        const val ColumnPatientId = "patient_id"
        const val ColumnDoctorId = "doctor_consulted_id"
        const val ColumnVisitDate = "visit_date"
    }
}


/**
 * One to many relationship between patient and consultations
 */

data class PatientWithConsultations(
    @Embedded
val patient : DatabasePatient,
    @Relation(
        parentColumn = DatabasePatient.ColumnPatientId,
        entityColumn = DatabaseConsultation.ColumnPatientId
    )
val databaseConsultations : List<DatabaseConsultation>?
)

/**
 *@Transaction
 * @Query("Select * from Patient)
 * fun getPatientWithConsultations(): List<patientWithConsultations>
 */


// one to one relationship between Consultation and Doctor.
data class DoctorWithConsultations(
    @Embedded val doctor: Doctor,
    @Relation(
        parentColumn = Doctor.ColumnDoctorId,
        entityColumn = DatabaseConsultation.ColumnDoctorId
    )
    val consultations: List<DatabaseConsultation>
)

/**
 * @Transaction
 * @Query("Select * from Doctor")
 * fun getDoctorWithConsultations() : List<DoctorWithConsultations>
 */


@Entity(tableName = TableName.PrescriptionDetailsTable, foreignKeys = [ForeignKey(
    entity = DatabaseConsultation::class,
    parentColumns = [DatabaseConsultation.ColumnConsultId],
    childColumns = [PrescriptionDetails.ColumnConsultId],
    onDelete = CASCADE
),ForeignKey(
    entity = Diseases::class,
    parentColumns = [Diseases.ColumnDiseaseCode],
    childColumns = [PrescriptionDetails.ColumnDiseaseId]
)])
data class PrescriptionDetails(
    @PrimaryKey(true)
    @ColumnInfo(ColumnPrescriptionId)
    val prescriptionId : Long,
    @ColumnInfo(ColumnConsultId,index=true)
    val consultID:Long,
    @ColumnInfo(ColumnDiseaseId,index=true)
    val diseaseId : Long,
    val complaints : String = "",
    val diagnosis : String = "",
    val treatment : String = "",
    @ColumnInfo(ColumnNextFollowUp)
    val nextFollowUpDate : Date,
    @ColumnInfo(ColumnPrescriptionSnaps)
    val snap : String

){
    companion object{
        const val ColumnPrescriptionId = "prescription_id"
        const val ColumnConsultId = "consult_id"
        const val ColumnDiseaseId = "disease_id"
        const val ColumnNextFollowUp = "next_follow_up_date"
        const val ColumnPrescriptionSnaps = "pics_of_prescription"
    }
}

/**
 * In each Consultation  , there can be many prescription details for different diseases
 * so one to many relationship between Consultation  and Prescription Details
 */
data class ConsultationWithDetails(
    @Embedded
    val consultation: DatabaseConsultation,
    @Relation(
        parentColumn = DatabaseConsultation.ColumnConsultId,
        entityColumn = PrescriptionDetails.ColumnConsultId
    )
    val prescriptionDetails: List<PrescriptionDetails>
)

/**
 * @Transaction
 * @Query("Select * from DatabaseConsultation")
 * fun getConsultationWithDetails() : List<ConsultationWithDetails>
 */


/**
 * There can be one Prescription Details and it can have multiple Medicine Details,
 * So one to many
 *
 * One medicine Unit can be in multiple Prescription Details
 * so one to many
 * similarly
 * One frequency unit can be in multiple prescription Details
 * so one to many
 *
 *And also,
 * one medicine can be in multiple prescription details
 * so one to many
 */


@Entity(tableName = TableName.PrescribedMedicinesTable, foreignKeys = [ForeignKey(
    entity = PrescriptionDetails::class,
    parentColumns = [PrescriptionDetails.ColumnPrescriptionId],
    childColumns = [PrescribedMedicines.ColumnPrescriptionId],
    onDelete = CASCADE
),
    ForeignKey(
        entity = MedicineUnit::class,
        parentColumns = [MedicineUnit.ColumnUnitId],
        childColumns = [PrescribedMedicines.ColumnMedicineUnitCode],
        onDelete = CASCADE
    ),

    ForeignKey(
        entity = Frequency::class,
        parentColumns = [Frequency.ColumnFrequencyCode],
        childColumns = [PrescribedMedicines.ColumnMedicineFrequencyId],
        onDelete = CASCADE
    ),

    ForeignKey(
        entity = Medicines::class,
        parentColumns = [Medicines.ColumnMedicineCode],
        childColumns = [PrescribedMedicines.ColumnMedicineId],
        onDelete = CASCADE
    )


])
data class PrescribedMedicines(
    @PrimaryKey(true)
    @ColumnInfo(ColumnMedicineDetailsId)
    val medicineDetailsId : Long,
    @ColumnInfo(ColumnPrescriptionId,index=true)
    val prescriptionId: Long,
    @ColumnInfo(ColumnMedicineId,index=true)
    val medicineId : Long,
    @ColumnInfo("medicine_qty")
    val medicineQty : String,
    @ColumnInfo(ColumnMedicineUnitCode,index=true)
    val medicineUnit : Long,
    @ColumnInfo(ColumnMedicineFrequencyId,index=true)
    val medicineFrequency: Long
    ){
    companion object{
        const val ColumnMedicineDetailsId = "medicine_details_id"
        const val ColumnPrescriptionId = "prescribed_id"
        const val ColumnMedicineUnitCode = "medicine_unit_code"
        const val ColumnMedicineId = "medicine_id"
        const val ColumnMedicineFrequencyId ="medicine_frequency_code"
    }
}


data class PrescriptionDetailsWithMedicineDetails(
    @Embedded
    val prescriptionDetails: PrescriptionDetails,
    @Relation(
        parentColumn = PrescriptionDetails.ColumnPrescriptionId,
        entityColumn = PrescribedMedicines.ColumnPrescriptionId
    )
    val medicine_details : List<PrescribedMedicines>
)

/**
 * @Transaction
 * @Query("Select * from PrescriptionDetails where prescriptionId = :id")
 * fun getPrescriptionDetailsWithMedicineDetails(id : Long):
 * List<PrescriptionDetailsWithMedicineDetails>?
 */

data class MedicineWithMedicineDetails(
    @Embedded val medicine: Medicines,
    @Relation(
        parentColumn = Medicines.ColumnMedicineCode,
        entityColumn = PrescribedMedicines.ColumnMedicineId
    )
    val medicineDetails : List<PrescribedMedicines>
)
/**
 * @Transaction
 * @Query("Select * from medicine")
 * fun getMedicineWithMedicineDetails():
 * List<MedicineWithMedicineDetails>
 */

data class MedicineUnitWithMedicineDetails(
    @Embedded val medicineUnit: MedicineUnit,
    @Relation(
        parentColumn = MedicineUnit.ColumnUnitId,
        entityColumn = PrescribedMedicines.ColumnMedicineUnitCode
    )
    val medicineDetails: List<PrescribedMedicines>
)

data class FrequencyWithMedicineDetails(
    @Embedded val medicineFrequency: Frequency,
    @Relation(
        parentColumn = Frequency.ColumnFrequencyCode,
        entityColumn = PrescribedMedicines.ColumnMedicineFrequencyId
    )
    val medicineDetails : List<PrescribedMedicines>
)