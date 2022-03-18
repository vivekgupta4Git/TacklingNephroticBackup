package com.ruviapps.tacklingnephrotic.domain.use_cases.result

import com.ruviapps.tacklingnephrotic.database.dto.QueryResult
import com.ruviapps.tacklingnephrotic.domain.TestResult
import com.ruviapps.tacklingnephrotic.extension.toDatabaseUrineResult
import com.ruviapps.tacklingnephrotic.repository.ResultLocalRepository
import javax.inject.Inject

class InsertUrineResultUseCase @Inject constructor(
    val repository : ResultLocalRepository
) {
    suspend operator fun invoke(result : TestResult) : QueryResult<Unit>{
       return repository.insertResult(result.toDatabaseUrineResult())
    }
}