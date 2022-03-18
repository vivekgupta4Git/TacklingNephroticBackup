package com.ruviapps.tacklingnephrotic.domain.use_cases.result

import com.ruviapps.tacklingnephrotic.database.dto.QueryResult
import com.ruviapps.tacklingnephrotic.database.entities.UrineResult
import com.ruviapps.tacklingnephrotic.repository.ResultLocalRepository
import javax.inject.Inject

class ResultsOfAllPatientUseCase @Inject constructor(
    val repository: ResultLocalRepository
) {
    suspend operator fun invoke() : QueryResult<List<UrineResult>>{
      return  repository.getResultsAllPatients()
    }
}