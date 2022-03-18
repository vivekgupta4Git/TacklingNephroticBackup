package com.ruviapps.tacklingnephrotic.database.datasources

import com.ruviapps.tacklingnephrotic.database.dto.QueryResult
import com.ruviapps.tacklingnephrotic.database.entities.ConsultationWithDetails
import com.ruviapps.tacklingnephrotic.database.entities.DatabaseConsultation

interface ConsultationDataSource {
    suspend fun addAdvice(databaseConsultation: DatabaseConsultation) : QueryResult<Unit>
    suspend fun getAllAdvices() : QueryResult<List<DatabaseConsultation>>
    suspend fun getAllAdvicesForPatient(id : Long) : QueryResult<List<DatabaseConsultation>>
    suspend fun deleteAdvice(databaseConsultation: DatabaseConsultation) : QueryResult<Unit>
    suspend fun getAdvicesByDoctor(id :Long) : QueryResult<List<DatabaseConsultation>>
    suspend fun getAdvicesList(patientId : Long, doctorId : Long) : QueryResult<List<DatabaseConsultation>>
    suspend fun getAdviceDetails(consultId : Long) : QueryResult<List<ConsultationWithDetails>>

}