package com.ruviApps.tacklingnephrotic.domain.use_cases.caretaker

import com.ruviApps.tacklingnephrotic.database.dto.QueryResult
import com.ruviApps.tacklingnephrotic.database.entities.CareTakerWithPatients
import com.ruviApps.tacklingnephrotic.domain.CareTaker
import com.ruviApps.tacklingnephrotic.extension.toDatabaseCareTaker
import com.ruviApps.tacklingnephrotic.repository.CareTakerLocalRepository


class PatientsOfCareTakerUseCase (
    private val repository: CareTakerLocalRepository,
) {

    suspend operator fun invoke(
        careTakerId : Long
        ): QueryResult<List<CareTakerWithPatients>> {
        return  repository.getPatientsOfCareTaker(careTakerId)

      }


}

