package com.ruviapps.tacklingnephrotic.repository

import com.ruviapps.tacklingnephrotic.database.dao.DailyLogDao
import com.ruviapps.tacklingnephrotic.database.datasources.DailyLogDataSource
import com.ruviapps.tacklingnephrotic.database.dto.QueryResult
import com.ruviapps.tacklingnephrotic.database.entities.DailyLog
import com.ruviapps.tacklingnephrotic.utility.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DailyLogLocalRepository @Inject constructor(
    val dailyLogDao: DailyLogDao,
    @IoDispatcher val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
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