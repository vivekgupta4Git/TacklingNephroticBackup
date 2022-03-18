package com.ruviapps.tacklingnephrotic.database.datasources

import com.ruviapps.tacklingnephrotic.database.dto.QueryResult
import com.ruviapps.tacklingnephrotic.database.entities.UrineResult
import kotlinx.coroutines.flow.Flow

interface UrineResultDataSource {
    suspend fun insertResult(urineResult: UrineResult) : QueryResult<Unit>
    suspend fun insertAllResults(urineResults: List<UrineResult>) : QueryResult<Unit>
    suspend fun removeResult(result: UrineResult) : QueryResult<Unit>
    suspend fun deleteAllResults(): QueryResult<Unit>
    suspend fun getResultsByPatientId(id: Long): QueryResult<List<UrineResult>>
    suspend fun getResultsAllPatients() : QueryResult<List<UrineResult>>
    suspend fun getFlowResultsByPatientId(id: Long) : QueryResult<Flow<List<UrineResult>>>
    suspend fun updateResult(result: UrineResult) :QueryResult<Int>

}