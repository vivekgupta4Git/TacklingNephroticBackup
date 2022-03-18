package com.ruviapps.tacklingnephrotic.domain.use_cases.result

import com.ruviapps.tacklingnephrotic.database.dto.QueryResult
import com.ruviapps.tacklingnephrotic.domain.TestResult
import com.ruviapps.tacklingnephrotic.extension.toDatabaseUrineResult
import com.ruviapps.tacklingnephrotic.repository.ResultLocalRepository
import javax.inject.Inject

class UpdateUrineResultUseCase @Inject constructor(
    val repository: ResultLocalRepository
) {
    suspend operator fun invoke(testResult: TestResult): QueryResult<Int> {
        return repository.updateResult(testResult.toDatabaseUrineResult())
    }

}