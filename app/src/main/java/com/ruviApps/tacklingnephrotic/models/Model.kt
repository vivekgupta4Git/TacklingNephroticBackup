package com.ruviApps.tacklingnephrotic.models

import androidx.room.*
import androidx.room.ForeignKey.Companion.CASCADE
import java.util.*


@Entity
data class CareTaker(
    @PrimaryKey(true)
    @ColumnInfo("caretaker_id")
    val ctId : Long,
    @Embedded val fullName: FullName,
    @Embedded val contact : ContactInfo,

    )

data class ContactInfo(
    @ColumnInfo("primary_contact")
    val primaryContact: Int,
    @ColumnInfo("secondary_contact")
    val secondaryContact : Int?,
    @ColumnInfo("email")
    val email: String?

)


@Entity
data class Doctor(
    @PrimaryKey(true)
    @ColumnInfo("doctor_id")
    val doctorId : Long,
    @Embedded
    val fullName: FullName,
    @Embedded
    val contactInfo: ContactInfo,
    )

@Entity
data class Medicine(
    @PrimaryKey(true)
    @ColumnInfo("medicine_code")
    val medicineCode : Long,
    @ColumnInfo("medicine_name")
    val medicineName : String,
)

@Entity
data class Diseases(
    @PrimaryKey(true)
    @ColumnInfo("diseases_code")
    val diseaseCode : Long,
    @ColumnInfo("disease_name")
    val diseaseName : String,
)


@Entity
data class MedicineUnit(
    @PrimaryKey(true)
    @ColumnInfo("unit_id")
    val unitID : Long,
    @ColumnInfo("unit_name")
    val unitName : String
)

@Entity
data class Frequency(
    @PrimaryKey(true)
    @ColumnInfo("frequency_code")
    val frequencyCode: Long,
    val name: String
)

@Entity(foreignKeys = [ForeignKey(
    entity = Patient::class,
    parentColumns = ["patient_id"],
    childColumns = ["to_patient"],
    onDelete = CASCADE
)])
data class Relapse(
    @PrimaryKey(true)
    @ColumnInfo("relapse_id")
    val relapseId : Long,
    @ColumnInfo("to_patient")
    val patientId : Long,
    @ColumnInfo("start_date")
    val startDate : Date,
    @ColumnInfo("end_date")
    val endDate : Date

)
data class PatientWithRelapses(
    @Embedded val patient: Patient,
    @Relation(
        parentColumn = "patient_id",
        entityColumn = "to_patient"
    )
    val relapses : List<Relapse>
)

/**
 * @Transaction
 * @Query("Select * from Patient)
 * fun getPatientWithRelapses() : List<PatientWithRelapses>
 */
