package com.ruviApps.tacklingnephrotic.domain.use_cases.caretaker

import com.ruviApps.tacklingnephrotic.database.dto.QueryResult
import com.ruviApps.tacklingnephrotic.domain.CareTaker
import com.ruviApps.tacklingnephrotic.extension.toDatabaseCareTaker
import com.ruviApps.tacklingnephrotic.repository.CareTakerLocalRepository

class DeleteCareTakerUseCases(
    private val careTakerLocalRepository: CareTakerLocalRepository
) {

    suspend operator fun invoke(careTaker : CareTaker): QueryResult<Unit>{
        return careTakerLocalRepository.deleteCareTaker(careTaker = careTaker.toDatabaseCareTaker())
    }
}