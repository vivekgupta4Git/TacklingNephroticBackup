package com.ruviApps.tacklingnephrotic.models

import android.telecom.CallAudioState
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey
import java.util.*


@Entity(foreignKeys = [ForeignKey(
    entity = Patient::class,
    parentColumns = ["patient_id"],
    childColumns = ["patient_consulted_id"],
    onDelete = ForeignKey.CASCADE
)])
data class Consultation(
    @PrimaryKey(true)
    @ColumnInfo("consult_id")
    val consultId : Long,
    @ColumnInfo("patient_consulted_id")
    val patientId : Long,
    val recordedDate : Date
)

@Entity(foreignKeys = [ForeignKey(
    entity = Consultation::class,
    parentColumns = ["consult_id"],
    childColumns = ["consult_key"],
    onDelete = ForeignKey.CASCADE
), ForeignKey(entity = Doctor::class,
    parentColumns = ["doctor_id"],
    childColumns = ["consulted_doctor_id"],
    onDelete = ForeignKey.CASCADE)])
data class ConsultationDetails(
    @PrimaryKey(true)
    @ColumnInfo("consultation_details_id")
    val consultationDetailsId : Long,
    @ColumnInfo("consult_key")
    val consultId: Long,
    @ColumnInfo("consulted_doctor_id")
    val doctorId: Long,
)

@Entity(foreignKeys = [ForeignKey(
    entity = ConsultationDetails::class,
    parentColumns = ["consultation_details_id"],
    childColumns = ["consult_details_id"],
    onDelete = CASCADE
)])
data class PrescriptionDetails(
    @PrimaryKey(true)
    @ColumnInfo("prescription_id")
    val prescriptionId : Long,
    @ColumnInfo("consult_details_id")
    val consultDetailsID:Long,

    val complaints : String?,
    val diagnosis : String?,
    val treatment : String?,

    @ColumnInfo("next_follow_up_date")
    val nextFollowUpDate : Date

)

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