package com.ruviApps.tacklingnephrotic.repository

import com.ruviApps.tacklingnephrotic.database.dao.PatientDao
import com.ruviApps.tacklingnephrotic.database.datasources.PatientsDataSource
import com.ruviApps.tacklingnephrotic.database.dto.QueryResult
import com.ruviApps.tacklingnephrotic.database.entities.DatabasePatient
import com.ruviApps.tacklingnephrotic.database.entities.PatientWithUrineResults
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.lang.Exception

class PatientLocalRepository(
    private val patientDao: PatientDao,
    private val ioDispatcher : CoroutineDispatcher
) : PatientsDataSource{
    override suspend fun savePatient(databasePatient: DatabasePatient): QueryResult<Unit>
    = withContext(ioDispatcher){
        return@withContext try {
            QueryResult.Success(patientDao.insertPatient(databasePatient))
        }catch (ex : Exception)
        {
            QueryResult.Error(ex.localizedMessage,QueryResult.QUERY_FAILURE_STATUS_CODE) }
    }

    override suspend fun getAllPatients(): QueryResult<List<DatabasePatient>> = withContext(ioDispatcher){
        return@withContext try {
            QueryResult.Success(patientDao.getAllPatients())
        }catch (ex : Exception)
        {  QueryResult.Error(ex.localizedMessage,QueryResult.QUERY_FAILURE_STATUS_CODE) }
    }

    override suspend fun getPatientById(id: Long): QueryResult<DatabasePatient> = withContext(ioDispatcher){
        return@withContext try {
            QueryResult.Success(patientDao.getPatientById(id))
        }catch (ex : Exception)
        {  QueryResult.Error(ex.localizedMessage,QueryResult.QUERY_FAILURE_STATUS_CODE) }
    }

    override suspend fun saveAllPatients(patient: List<DatabasePatient>): QueryResult<Unit> = withContext(ioDispatcher){
        return@withContext try {
            QueryResult.Success(patientDao.insertAllPatient(*patient.toTypedArray()))
        }catch (ex : Exception)
        {  QueryResult.Error(ex.localizedMessage,QueryResult.QUERY_FAILURE_STATUS_CODE) }
    }

    override suspend fun deleteAllPatients(): QueryResult<Unit> = withContext(ioDispatcher){
        return@withContext try{
            QueryResult.Success(patientDao.deleteAllPatients())
        }catch (ex  :Exception){
            QueryResult.Error(ex.localizedMessage,QueryResult.QUERY_FAILURE_STATUS_CODE)
        }
    }

    override suspend fun getPatientsWithUrineResults(patientId: Long): QueryResult<List<PatientWithUrineResults>> = withContext(ioDispatcher){
        return@withContext try{
            QueryResult.Success(patientDao.getPatientWithResults(patientId))
        }catch (ex : Exception){
            QueryResult.Error(ex.localizedMessage,QueryResult.QUERY_FAILURE_STATUS_CODE)
        }
    }
}