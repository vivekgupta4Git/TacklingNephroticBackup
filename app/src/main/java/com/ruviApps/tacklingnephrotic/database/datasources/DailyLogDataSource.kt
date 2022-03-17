package com.ruviApps.tacklingnephrotic.database.datasources

import com.ruviApps.tacklingnephrotic.database.dto.QueryResult
import com.ruviApps.tacklingnephrotic.database.entities.DailyLog

interface DailyLogDataSource {

    suspend fun insertDailyLog(dailyLog: DailyLog) : QueryResult<Unit>
    suspend fun insertLogs(vararg dailyLog: DailyLog) : QueryResult<Unit>
    suspend fun getLogsForPatient(patientId : Long) : QueryResult<List<DailyLog>>
    suspend fun getLogsForAll() : QueryResult<List<DailyLog>>
}