package com.ruviApps.tacklingnephrotic.domain.use_cases.result

import com.ruviApps.tacklingnephrotic.database.dto.QueryResult
import com.ruviApps.tacklingnephrotic.database.entities.UrineResult
import com.ruviApps.tacklingnephrotic.repository.ResultLocalRepository

class ResultsOfAllPatientUseCase(
    val repository: ResultLocalRepository
) {
    suspend operator fun invoke() : QueryResult<List<UrineResult>>{
      return  repository.getResultsAllPatients()
    }
}