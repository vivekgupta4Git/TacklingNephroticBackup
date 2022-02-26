package com.ruviApps.tacklingnephrotic.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey


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
    val logId : Long,
    @ColumnInfo(ColumnPatientId)
    val patientId : Long,
    @ColumnInfo(ColumnPreviousResultId)
    val previousUrineResult: Long,
    @ColumnInfo(ColumnMedicinesAdministered)
    val medicinesAdministeredId : Long,
    @ColumnInfo("nephrotic_state")
    val nephroticState: NephroticState,
    @ColumnInfo("health_status")
    val healthStatus: HealthStatus
    )
{
companion object{
    const val ColumnLogId = "log_id"
    const val ColumnPatientId = "patient_id"
    const val ColumnPreviousResultId ="previous_result_id"
    const val ColumnMedicinesAdministered = "medicines_administered"

}
}

enum class NephroticState{
    REMISSION,
    RELAPSE,
    OBSERVATION
}

enum class HealthStatus{
    HEALTHY,
    UNHEALTHY
}

