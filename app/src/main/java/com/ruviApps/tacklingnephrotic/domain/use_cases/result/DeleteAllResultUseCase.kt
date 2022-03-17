
package com.ruviApps.tacklingnephrotic.domain.use_cases.result

import com.ruviApps.tacklingnephrotic.database.dto.QueryResult
import com.ruviApps.tacklingnephrotic.repository.ResultLocalRepository

class DeleteAllResultUseCase(
    val repository: ResultLocalRepository
) {
    suspend operator fun invoke(): QueryResult<Unit> {
        return repository.deleteAllResults()
    }

}
