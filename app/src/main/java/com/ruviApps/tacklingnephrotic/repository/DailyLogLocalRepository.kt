package com.ruviApps.tacklingnephrotic.repository

import com.ruviApps.tacklingnephrotic.database.dao.DailyLogDao
import com.ruviApps.tacklingnephrotic.database.datasources.DailyLogDataSource
import com.ruviApps.tacklingnephrotic.database.dto.QueryResult
import com.ruviApps.tacklingnephrotic.database.entities.DailyLog
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DailyLogLocalRepository(
    private val dailyLogDao: DailyLogDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
)  : DailyLogDataSource{
    override suspend fun insertDailyLog(dailyLog: DailyLog): QueryResult<Unit> {
return withContext(ioDispatcher){
    try {
        QueryResult.Success(dailyLogDao.insertLog(dailyLog))
    }catch (e : Exception){
        QueryResult.Error(e.localizedMessage)
    }
}
    }

    override suspend fun insertLogs(vararg dailyLog: DailyLog): QueryResult<Unit> {
        return withContext(ioDispatcher){
            try {
                QueryResult.Success(dailyLogDao.insertAllLogs(*dailyLog))
            }catch (e : Exception){
                QueryResult.Error(e.localizedMessage)
            }
        }

    }


    override suspend fun getLogsForPatient(patientId: Long): QueryResult<List<DailyLog>> {
        return withContext(ioDispatcher){
            try {
                QueryResult.Success(dailyLogDao.getLogsForPatient(patientId))
            }catch (e : Exception){
                QueryResult.Error(e.localizedMessage)
            }
        }

    }

    override suspend fun getLogsForAll(): QueryResult<List<DailyLog>>{
        return withContext(ioDispatcher){
            try {
                QueryResult.Success(dailyLogDao.getAllLogs())
            }catch (e : Exception){
                QueryResult.Error(e.localizedMessage)
            }
        }

    }
}