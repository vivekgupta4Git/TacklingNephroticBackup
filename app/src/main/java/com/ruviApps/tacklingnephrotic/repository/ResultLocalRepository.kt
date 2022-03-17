package com.ruviApps.tacklingnephrotic.repository

import com.ruviApps.tacklingnephrotic.database.dao.ResultDao
import com.ruviApps.tacklingnephrotic.database.datasources.UrineResultDataSource
import com.ruviApps.tacklingnephrotic.database.dto.QueryResult
import com.ruviApps.tacklingnephrotic.database.entities.UrineResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import java.lang.Exception

class ResultLocalRepository(
    private val resultDao : ResultDao,
    private val ioDispatcher : CoroutineDispatcher = Dispatchers.IO
) : UrineResultDataSource {

    override suspend fun insertResult(urineResult: UrineResult): QueryResult<Unit> = withContext(ioDispatcher){
        return@withContext try {
            QueryResult.Success(resultDao.insertResult(urineResult),"Record Inserted")
        }catch (ex : Exception)
        {  QueryResult.Error(ex.localizedMessage) }
    }

    override suspend fun insertAllResults(urineResults : List<UrineResult>): QueryResult<Unit>  =withContext(ioDispatcher){
        return@withContext try {
            QueryResult.Success(resultDao.insertAllResult(*urineResults.toTypedArray()),"Records Inserted")
        }catch (ex : Exception)
        {  QueryResult.Error(ex.localizedMessage) }
    }


    override suspend fun removeResult(result: UrineResult): QueryResult<Unit> {
        return withContext(ioDispatcher){
         try {
             QueryResult.Success(resultDao.removeResult(result))
         } catch (ex : Exception)
         {
             QueryResult.Error(ex.localizedMessage)
         }
        }
    }

    override suspend fun deleteAllResults(): QueryResult<Unit>  = withContext(ioDispatcher){
        return@withContext try {
            QueryResult.Success(resultDao.deleteAllResults())
        }catch (ex : Exception)
        {  QueryResult.Error(ex.localizedMessage) }
    }

    override suspend fun getResultsByPatientId(id: Long): QueryResult<List<UrineResult>> = withContext(ioDispatcher){
        return@withContext try {
            QueryResult.Success(resultDao.getAllResultsForPatient(id))
        }catch (ex : Exception)
        {  QueryResult.Error(ex.localizedMessage) }
    }

    override suspend fun getResultsAllPatients(): QueryResult<List<UrineResult>>  = withContext(ioDispatcher){
        return@withContext try {
            QueryResult.Success(resultDao.getResultsForAllPatients())
        }catch (ex : Exception)
        {  QueryResult.Error(ex.localizedMessage) }
    }

    override suspend fun getFlowResultsByPatientId(id: Long): QueryResult<Flow<List<UrineResult>>>  = withContext(ioDispatcher){
        return@withContext try {
            QueryResult.Success(resultDao.getResultsForPatientUsingFlow(id))
        }catch (ex : Exception)
        {  QueryResult.Error(ex.localizedMessage) }
    }

    override suspend fun updateResult(result: UrineResult): QueryResult<Int> = withContext(ioDispatcher){
        return@withContext try{
            QueryResult.Success(resultDao.updateResult(result))
        }catch (ex : Exception){
            QueryResult.Error(ex.localizedMessage)
        }
    }
}