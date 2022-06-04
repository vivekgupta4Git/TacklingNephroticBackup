package com.ruviapps.tacklingnephrotic.domain.use_cases.result

import com.ruviapps.tacklingnephrotic.database.dto.QueryResult
import com.ruviapps.tacklingnephrotic.database.entities.UrineResult
import com.ruviapps.tacklingnephrotic.repository.ResultLocalRepository
import java.time.LocalDate
import javax.inject.Inject

class GetReadingByIdUseCase @Inject constructor(
    val repository: ResultLocalRepository
) {
    suspend operator fun invoke(id : LocalDate): QueryResult<UrineResult> {
     return repository.getReadingById(id)
    }

}