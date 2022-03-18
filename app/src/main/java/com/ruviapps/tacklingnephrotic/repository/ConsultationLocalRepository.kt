package com.ruviapps.tacklingnephrotic.repository

import com.ruviapps.tacklingnephrotic.database.dao.ConsultationDao
import com.ruviapps.tacklingnephrotic.database.datasources.ConsultationDataSource
import com.ruviapps.tacklingnephrotic.database.dto.QueryResult
import com.ruviapps.tacklingnephrotic.database.entities.ConsultationWithDetails
import com.ruviapps.tacklingnephrotic.database.entities.DatabaseConsultation
import com.ruviapps.tacklingnephrotic.utility.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.Exception

class ConsultationLocalRepository(
     val adviceDao : ConsultationDao,
   @IoDispatcher  val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ConsultationDataSource {
    override suspend fun addAdvice(databaseConsultation: DatabaseConsultation): QueryResult<Unit> {
        return withContext(ioDispatcher){
            try {
                QueryResult.Success(adviceDao.insertConsultation(databaseConsultation))
            }catch (ex : Exception)
            {
                QueryResult.Error(ex.localizedMessage)
            }
        }
    }

    override suspend fun getAllAdvices(): QueryResult<List<DatabaseConsultation>> {
        return withContext(ioDispatcher){
            try {
                QueryResult.Success(adviceDao.getAllConsultations())
            }catch (ex : Exception)
            {
                QueryResult.Error(ex.localizedMessage)
            }
        }

    }

    override suspend fun getAllAdvicesForPatient(id: Long): QueryResult<List<DatabaseConsultation>> {
        return withContext(ioDispatcher){
            try {
                QueryResult.Success(adviceDao.getConsultationsForPatientById(id))
            }catch (ex : Exception)
            {
                QueryResult.Error(ex.localizedMessage)
            }
        }

    }

    override suspend fun deleteAdvice(databaseConsultation: DatabaseConsultation): QueryResult<Unit> {
        return withContext(ioDispatcher){
            try {
                QueryResult.Success(adviceDao.deleteConsultation(databaseConsultation))
            }catch (ex : Exception)
            {
                QueryResult.Error(ex.localizedMessage)
            }
        }

    }

    override suspend fun getAdvicesByDoctor(id : Long): QueryResult<List<DatabaseConsultation>> {
        return withContext(ioDispatcher){
            try {
                QueryResult.Success(adviceDao.getConsultationsOfDoctor(id))
            }catch (ex : Exception)
            {
                QueryResult.Error(ex.localizedMessage)
            }
        }

    }

    override suspend fun getAdvicesList(
        patientId: Long,
        doctorId: Long,
    ): QueryResult<List<DatabaseConsultation>> {
        return withContext(ioDispatcher){
            try {
                QueryResult.Success(adviceDao.getConsultationId(patientId,doctorId))
            }catch (ex : Exception)
            {
                QueryResult.Error(ex.localizedMessage)
            }
        }

    }

    override suspend fun getAdviceDetails(consultId: Long): QueryResult<List<ConsultationWithDetails>> {
        return withContext(ioDispatcher){
            try{
                QueryResult.Success(adviceDao.getConsultationWithDetails(consultId))
            }catch (e : Exception){
                QueryResult.Error(e.localizedMessage)
            }
        }
    }
}