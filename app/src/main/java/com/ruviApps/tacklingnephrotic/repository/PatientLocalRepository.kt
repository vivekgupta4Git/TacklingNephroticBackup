package com.ruviApps.tacklingnephrotic.repository

import com.ruviApps.tacklingnephrotic.database.dao.PatientDao
import com.ruviApps.tacklingnephrotic.database.datasources.PatientsDataSource
import com.ruviApps.tacklingnephrotic.database.dto.QueryResult
import com.ruviApps.tacklingnephrotic.database.entities.DatabasePatient
import com.ruviApps.tacklingnephrotic.database.entities.PatientWithConsultations
import com.ruviApps.tacklingnephrotic.database.entities.PatientWithUrineResults
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

class PatientLocalRepository(
    private val patientDao: PatientDao,
    private val ioDispatcher : CoroutineDispatcher = Dispatchers.IO
) : PatientsDataSource{
    override suspend fun savePatient(databasePatient: DatabasePatient): QueryResult<Long>
    = withContext(ioDispatcher){
        return@withContext try {
            QueryResult.Success(patientDao.insertPatient(databasePatient))
        }catch (ex : Exception)
        {
            QueryResult.Error(ex.localizedMessage) }
    }

    override suspend fun getAllPatients(): QueryResult<List<DatabasePatient>> = withContext(ioDispatcher){
        return@withContext try {
            QueryResult.Success(patientDao.getAllPatients())
        }catch (ex : Exception)
        {  QueryResult.Error(ex.localizedMessage) }
    }

    override suspend fun getPatientById(id: Long): QueryResult<DatabasePatient> = withContext(ioDispatcher){
        return@withContext try {
            QueryResult.Success(patientDao.getPatientById(id))
        }catch (ex : Exception)
        {  QueryResult.Error(ex.localizedMessage) }
    }

    override suspend fun saveAllPatients(patient: List<DatabasePatient>): QueryResult<Unit> = withContext(ioDispatcher){
        return@withContext try {
            QueryResult.Success(patientDao.insertAllPatient(*patient.toTypedArray()))
        }catch (ex : Exception)
        {  QueryResult.Error(ex.localizedMessage )}
    }

       override suspend fun deleteAllPatients(): QueryResult<Unit> = withContext(ioDispatcher){
        return@withContext try{
            QueryResult.Success(patientDao.deleteAllPatients())
        }catch (ex  :Exception){
            QueryResult.Error(ex.localizedMessage)
        }
    }

    override suspend fun deletePatient(databasePatient: DatabasePatient): QueryResult<Long> = withContext(ioDispatcher){
        return@withContext try{
            QueryResult.Success(patientDao.deletePatient(databasePatient))
        }catch (e : Exception){
            QueryResult.Error(e.localizedMessage)
        }

    }

    override suspend fun getPatientsWithUrineResults(patientId: Long): QueryResult<List<PatientWithUrineResults>> = withContext(ioDispatcher){
        return@withContext try{
            QueryResult.Success(patientDao.getPatientWithResults(patientId))
        }catch (ex : Exception){
            QueryResult.Error(ex.localizedMessage)
        }
    }

    override suspend fun getPatientsAllAdvices(patientId: Long): QueryResult<List<PatientWithConsultations>> {
        return withContext(ioDispatcher){
            try{
                QueryResult.Success(patientDao.getAdvicesForPatient(patientId))
            }catch (ex : Exception)
            {
                QueryResult.Error(ex.localizedMessage)
            }
        }
    }
}