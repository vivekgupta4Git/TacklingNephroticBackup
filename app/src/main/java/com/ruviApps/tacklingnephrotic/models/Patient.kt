package com.ruviApps.tacklingnephrotic.models

import androidx.room.*
import androidx.room.ForeignKey.Companion.CASCADE

@Entity(tableName = "Patient", foreignKeys = [ForeignKey(entity = CareTaker::class,
    parentColumns =   ["caretaker_id"],
    childColumns = ["patient_caretaker_id"],
    onDelete = CASCADE)
])
data class Patient(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("patient_id")
    val patientId : Long,
    @Embedded val fullName : FullName?,
    /*@ColumnInfo("first_name")
    val firstName : String,
    @ColumnInfo("last_name")
    val lastName : String?,
    */
    val age : Int?,
    val weight: Float?,
    @ColumnInfo("snap_uri")
    val snapUri : String?,
    @ColumnInfo("patient_caretaker_id")
    val patientCaretakerId : Long
)

data class CareTakerWithPatients(
    @Embedded val caretaker: CareTaker,
    @Relation(
        parentColumn = "caretaker_id",
        entityColumn = "patient_caretaker_id"
    )
    val patients : List<Patient>

)


data class FullName(
    @ColumnInfo("first_name")
    val firstName: String,
    @ColumnInfo("last_name")
    val lastName: String?
)

/**
 * @Transaction
@Query("SELECT * FROM CareTaker")
fun getCareTakerWithPatients(): List<CareTakerWithPatients>

 */