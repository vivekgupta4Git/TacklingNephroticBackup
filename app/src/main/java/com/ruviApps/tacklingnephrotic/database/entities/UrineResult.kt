package com.ruviApps.tacklingnephrotic.database.entities

import androidx.room.*
import androidx.room.ForeignKey.Companion.CASCADE
import java.util.*

@Entity(tableName = TableName.UrineResultTable, foreignKeys = [
    ForeignKey(entity = DatabasePatient::class,
    parentColumns = [DatabasePatient.ColumnPatientId],
    childColumns = [UrineResult.ColumnPatientId],
    onDelete = CASCADE)])
data class UrineResult(
    @PrimaryKey(true)
    @ColumnInfo(ColumnResultId)
    val resultId : Long,
    @ColumnInfo("result_code")
    val resultCode : ResultCode,
    val remarks : String?,
    @ColumnInfo(ColumnRecordedDate, defaultValue = "'CURRENT_TIMESTAMP'")
    val recordedDate : Date,
    @ColumnInfo(ColumnPatientId,index=true)
    val urineResultOfPatientId : Long

){
    companion object{
        const val ColumnResultId = "result_id"
        const val ColumnPatientId = "urine_result_patient_id"
        const val ColumnRecordedDate = "recorded_date"
    }
}

data class PatientWithUrineResults(
    @Embedded val patient: DatabasePatient,
    @Relation(
        parentColumn = DatabasePatient.ColumnPatientId,
        entityColumn = UrineResult.ColumnPatientId
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