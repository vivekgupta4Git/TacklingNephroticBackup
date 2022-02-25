package com.ruviApps.tacklingnephrotic.models

import androidx.room.*
import androidx.room.ForeignKey.Companion.CASCADE
import java.util.*

@Entity(foreignKeys = [
    ForeignKey(entity = Patient::class,
    parentColumns = ["patient_id"],
    childColumns = ["urine_result_patient_id"],
    onDelete = CASCADE)])
data class UrineResult(
    @PrimaryKey(true)
    @ColumnInfo("result_id")
    val resultId : Long,
    @ColumnInfo("result_code")
    val resultCode : ResultCode,
    val remarks : String?,
    @ColumnInfo("recorded_date", defaultValue = "'CURRENT_TIMESTAMP'")
    val recordedDate : Date,
    @ColumnInfo("urine_result_patient_id")
    val urineResultOfPatientId : Long

)

data class PatientWithUrineResults(
    @Embedded val patient: Patient,
    @Relation(
        parentColumn = "patient_id",
        entityColumn = "urine_result_patient_id"
    )
    val urineResults : List<UrineResult>
)

//in dao usage
/*
@Transaction
@Query("Select * from Patient)
fun getPatientsWithUrineResults(): List<PatientWithUrineResults>
 */


enum class ResultCode(val value: Int){
    NEGATIVE(-1),
    TRACE(0),
    ONE_PLUS(1),
    TWO_PLUS(2),
    THREE_PLUS(3),
    FOUR_PLUS(4)
    }