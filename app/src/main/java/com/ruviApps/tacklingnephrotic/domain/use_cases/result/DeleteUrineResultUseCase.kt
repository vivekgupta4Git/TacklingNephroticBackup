package com.ruviApps.tacklingnephrotic.domain.use_cases.result

import com.ruviApps.tacklingnephrotic.database.dto.QueryResult
import com.ruviApps.tacklingnephrotic.domain.TestResult
import com.ruviApps.tacklingnephrotic.extension.toDatabaseUrineResult
import com.ruviApps.tacklingnephrotic.repository.ResultLocalRepository

class DeleteUrineResultUseCase(
    val repository: ResultLocalRepository
) {
    suspend operator fun invoke(testResult: TestResult): QueryResult<Unit> {
        return repository.removeResult(testResult.toDatabaseUrineResult())
    }

}
