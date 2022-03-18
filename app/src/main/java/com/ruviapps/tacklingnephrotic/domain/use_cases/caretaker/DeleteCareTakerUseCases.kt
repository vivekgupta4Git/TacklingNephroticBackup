package com.ruviapps.tacklingnephrotic.domain.use_cases.caretaker

import com.ruviapps.tacklingnephrotic.database.dto.QueryResult
import com.ruviapps.tacklingnephrotic.domain.CareTaker
import com.ruviapps.tacklingnephrotic.extension.toDatabaseCareTaker
import com.ruviapps.tacklingnephrotic.repository.CareTakerLocalRepository
import javax.inject.Inject

class DeleteCareTakerUseCases @Inject constructor(
    private val careTakerLocalRepository: CareTakerLocalRepository
) {

    suspend operator fun invoke(careTaker : CareTaker): QueryResult<Unit>{
        return careTakerLocalRepository.deleteCareTaker(careTaker = careTaker.toDatabaseCareTaker())
    }
}