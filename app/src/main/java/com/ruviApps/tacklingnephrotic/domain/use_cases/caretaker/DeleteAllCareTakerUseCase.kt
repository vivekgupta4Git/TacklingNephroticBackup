package com.ruviApps.tacklingnephrotic.domain.use_cases.caretaker

import com.ruviApps.tacklingnephrotic.database.dto.QueryResult
import com.ruviApps.tacklingnephrotic.repository.CareTakerLocalRepository

class DeleteAllCareTakerUseCase(
    private val repository: CareTakerLocalRepository
) {

    suspend operator fun invoke() : QueryResult<Unit> {
        return repository.deleteAllCareTakers()
    }
}