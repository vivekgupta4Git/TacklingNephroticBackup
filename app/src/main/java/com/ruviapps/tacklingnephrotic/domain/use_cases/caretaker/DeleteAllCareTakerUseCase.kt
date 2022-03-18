package com.ruviapps.tacklingnephrotic.domain.use_cases.caretaker

import com.ruviapps.tacklingnephrotic.database.dto.QueryResult
import com.ruviapps.tacklingnephrotic.repository.CareTakerLocalRepository
import javax.inject.Inject

class DeleteAllCareTakerUseCase @Inject constructor(
    private val repository: CareTakerLocalRepository
) {

    suspend operator fun invoke() : QueryResult<Unit> {
        return repository.deleteAllCareTakers()
    }
}