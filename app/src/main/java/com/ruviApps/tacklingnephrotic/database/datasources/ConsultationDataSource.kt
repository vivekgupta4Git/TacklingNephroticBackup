package com.ruviApps.tacklingnephrotic.database.datasources

import androidx.room.Query
import com.ruviApps.tacklingnephrotic.database.dto.QueryResult
import com.ruviApps.tacklingnephrotic.database.entities.ConsultationWithDetails
import com.ruviApps.tacklingnephrotic.database.entities.DatabaseConsultation
import com.ruviApps.tacklingnephrotic.database.entities.TableName

interface ConsultationDataSource {
    suspend fun addAdvice(databaseConsultation: DatabaseConsultation) : QueryResult<Unit>
    suspend fun getAllAdvices() : QueryResult<List<DatabaseConsultation>>
    suspend fun getAllAdvicesForPatient(id : Long) : QueryResult<List<DatabaseConsultation>>
    suspend fun deleteAdvice(databaseConsultation: DatabaseConsultation) : QueryResult<Unit>
    suspend fun getAdvicesByDoctor(id :Long) : QueryResult<List<DatabaseConsultation>>
    suspend fun getAdvicesList(patientId : Long, doctorId : Long) : QueryResult<List<DatabaseConsultation>>
    suspend fun getAdviceDetails(consultId : Long) : QueryResult<List<ConsultationWithDetails>>

}