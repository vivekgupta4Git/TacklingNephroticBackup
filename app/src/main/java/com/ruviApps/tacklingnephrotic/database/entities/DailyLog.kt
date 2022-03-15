package com.ruviApps.tacklingnephrotic.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey
import java.util.*


@Entity(tableName = TableName.DailyLogTable, foreignKeys = [ForeignKey(
    entity = DatabasePatient::class,
    parentColumns = [DatabasePatient.ColumnPatientId],
    childColumns = [DailyLog.ColumnPatientId],
    onDelete = CASCADE
),ForeignKey(
    entity = UrineResult::class,
    parentColumns = [UrineResult.ColumnResultId],
    childColumns = [DailyLog.ColumnPreviousResultId],
    onDelete = CASCADE
),ForeignKey(
    entity = MedicinesAdministered::class,
    parentColumns = [MedicinesAdministered.ColumnAdministeredId],
    childColumns = [DailyLog.ColumnMedicinesAdministered],
    onDelete = CASCADE
)])
data class DailyLog(
    @PrimaryKey(true)
    @ColumnInfo(ColumnLogId)
    val logId: Long,
    @ColumnInfo(ColumnPatientId, index = true)
    val patientId: Long,
    @ColumnInfo(ColumnPreviousResultId, index= true)
    val previousUrineResult: Long,
    @ColumnInfo(ColumnMedicinesAdministered,index = true)
    val medicinesAdministeredId: Long?,
    @ColumnInfo(ColumnNephroticState)
    val nephroticState: NephroticState,
    @ColumnInfo(ColumnHealthStatus)
    val healthStatus: HealthStatus,
    @ColumnInfo(ColumnRecordedDate)
    val recordedDate: Date
    )
{
companion object{
    const val ColumnLogId = "log_id"
    const val ColumnPatientId = "patient_id"
    const val ColumnPreviousResultId ="previous_result_id"
    const val ColumnMedicinesAdministered = "medicines_administered"
    const val ColumnNephroticState = "nephrotic_state"
    const val ColumnHealthStatus = "health_status"
    const val ColumnRecordedDate = "recorded_date"
}
}

enum class NephroticState{
    REMISSION,
    RELAPSE,
    OBSERVATION
}

enum class HealthStatus {
    HEALTHY,
    UNHEALTHY
}

