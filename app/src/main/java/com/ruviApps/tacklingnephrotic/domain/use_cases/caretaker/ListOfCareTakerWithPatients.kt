package com.ruviApps.tacklingnephrotic.domain.use_cases.caretaker

import com.ruviApps.tacklingnephrotic.database.dto.QueryResult
import com.ruviApps.tacklingnephrotic.database.entities.CareTakerWithPatients
import com.ruviApps.tacklingnephrotic.repository.CareTakerLocalRepository

class ListOfCareTakersWithPatients (
   private val careTakerLocalRepository: CareTakerLocalRepository
        ){
    suspend operator fun invoke(): QueryResult<List<CareTakerWithPatients>>{
        return careTakerLocalRepository.getCareTakerWithPatients()
    }
}