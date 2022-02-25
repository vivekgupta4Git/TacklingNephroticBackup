package com.ruviApps.tacklingnephrotic.models

import android.telecom.CallAudioState
import androidx.room.*
import androidx.room.ForeignKey.Companion.CASCADE
import java.util.*


@Entity(foreignKeys = [ForeignKey(
    entity = Patient::class,
    parentColumns = ["patient_id"],
    childColumns = ["patient_id"],
    onDelete = CASCADE
)])
data class Consultation(
    @PrimaryKey(true)
    @ColumnInfo("consult_id")
    val consultId : Long,
    @ColumnInfo("patient_id")
    val patientId : Long,
    @ColumnInfo("visit_date")
    val visitDate : Date,
    @ColumnInfo("doctor_consulted_id")
    val consultedDoctorId : Long

)


/**
 * One to many relationship between patient and consultations
 */

data class PatientWithConsultations(
    @Embedded
val patient : Patient,
    @Relation(
        parentColumn = "patient_id",
        entityColumn = "patient_id"
    )
val consultations : List<Consultation>
)

/**
 *@Transaction
 * @Query("Select * from Patient)
 * fun getPatientWithConsultations(): List<patientWithConsultations>
 */

/*

@Entity(foreignKeys = [ForeignKey(
    entity = Consultation::class,
    parentColumns = ["consult_id"],
    childColumns = ["consult_key"],
    onDelete = CASCADE
), ForeignKey(entity = Doctor::class,
    parentColumns = ["doctor_id"],
    childColumns = ["consulted_doctor_id"],
    onDelete = CASCADE)])
data class ConsultationDetails(
    @PrimaryKey(true)
    @ColumnInfo("consultation_details_id")
    val consultationDetailsId : Long,
    @ColumnInfo("consult_key")
    val consultId: Long,
    @ColumnInfo("consulted_doctor_id")
    val doctorId: Long,
)
*/

/**
 * One to one Relationship with between consultation and consultation detials
 */
/*
data class ConsultationAndDetails(
    @Embedded
    val consultation: Consultation,
    @Relation(
        parentColumn = "consult_id",
        entityColumn = "consult_key"
    )
    val details : ConsultationDetails
)
*/

/**
 * @Transaction
 * @Query("Select * from Consultation)
 * fun getConsultationAndDetails() : List<ConsultationsAndDetails>
 */


// one to one relationship between Consultation and Doctor.
data class DoctorWithConsultations(
    @Embedded val doctor: Doctor,
    @Relation(
        parentColumn = "doctor_id",
        entityColumn = "doctor_consulted_id"
    )
    val consultations: List<Consultation>
)

/**
 * @Transaction
 * @Query("Select * from Doctor")
 * fun getDoctorWithConsultations() : List<DoctorWithConsultations>
 */


@Entity(foreignKeys = [ForeignKey(
    entity = Consultation::class,
    parentColumns = ["consult_id"],
    childColumns = ["consult_id"],
    onDelete = CASCADE
)])
data class PrescriptionDetails(
    @PrimaryKey(true)
    @ColumnInfo("prescription_id")
    val prescriptionId : Long,
    @ColumnInfo("consult_id")
    val consultID:Long,

    @ColumnInfo("prescription_for_diseases_id")
    val diseaseId : Long,

    val complaints : String?,
    val diagnosis : String?,
    val treatment : String?,

    @ColumnInfo("next_follow_up_date")
    val nextFollowUpDate : Date

)

/**
 * In each Consultation  , there can be many prescription details for different diseases
 * so one to many relationship between Consultation  and Prescription Details
 */
data class ConsultationWithPrescriptionDetails(
    @Embedded
    val consultation: Consultation,
    @Relation(
        parentColumn = "consult_id",
        entityColumn = "consult_id"
    )
    val prescriptionDetails: List<PrescriptionDetails>
)

/**
 * @Transaction
 * @Query("Select * from ConsultationDetails")
 * fun getConsultationWithPrescriptionDetails() : List<ConsultationWithPrescriptionDetails>
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


@Entity(foreignKeys = [ForeignKey(
    entity = PrescriptionDetails::class,
    parentColumns = ["prescription_id"],
    childColumns = ["prescribed_details_id"],
    onDelete = CASCADE
),
    ForeignKey(
        entity = MedicineUnit::class,
        parentColumns = ["unit_id"],
        childColumns = ["medicine_unit_code"],
        onDelete = CASCADE
    ),

    ForeignKey(
        entity = Frequency::class,
        parentColumns = ["frequency_code"],
        childColumns = ["medicine_frequency_code"],
        onDelete = CASCADE
    ),

    ForeignKey(
        entity = Medicine::class,
        parentColumns = ["medicine_code"],
        childColumns = ["prescribed_medicine_id"],
        onDelete = CASCADE
    )


])
data class MedicineDetails(
    @PrimaryKey(true)
    @ColumnInfo("medicine_details_id")
    val medicineDetailsId : Long,
    @ColumnInfo("prescribed_details_id")
    val prescriptionId: Long,
    @ColumnInfo("prescribed_medicine_id")
    val medicineId : Long,
    @ColumnInfo("medicine_qty")
    val medicineQty : String,
    @ColumnInfo("medicine_unit_code")
    val medicineUnit : Long,
    @ColumnInfo("medicine_frequency_code")
    val medicineFrequency: Long
    )


data class PrescriptionDetailsWithMedicineDetails(
    @Embedded
    val prescriptionDetails: PrescriptionDetails,
    @Relation(
        parentColumn = "prescription_id",
        entityColumn = "prescribed_details_id"
    )
    val medicine_details : List<MedicineDetails>
)

/**
 * @Transaction
 * @Query("Select * from PrescriptionDetails")
 * fun getPrescriptionDetailsWithMedicineDetails():
 * List<PrescriptionDetailsWithMedicineDetails>
 */

data class MedicineWithPrescriptionDetails(
    @Embedded val medicine: Medicine,
    @Relation(
        parentColumn = "medicine_code",
        entityColumn = "prescribed_medicine_id"
    )
    val prescriptionDetails : List<PrescriptionDetails>
)
/**
 * @Transaction
 * @Query("Select * from medicine")
 * fun getMedicineWithPrescriptionDetails():
 * List<MedicineWithPrescriptionDetails>
 */

data class MedicineUnitWithPrescriptionDetails(
    @Embedded val medicineUnit: MedicineUnit,
    @Relation(
        parentColumn = "unit_id",
        entityColumn = "medicine_unit_code"
    )
    val prescriptionDetails: List<PrescriptionDetails>
)

data class FrequencyWithPrescriptionDetails(
    @Embedded val medicineFrequency: Frequency,
    @Relation(
        parentColumn = "frequency_code",
        entityColumn = "medicine_frequency_code"
    )
    val prescriptionDetails: List<PrescriptionDetails>
)