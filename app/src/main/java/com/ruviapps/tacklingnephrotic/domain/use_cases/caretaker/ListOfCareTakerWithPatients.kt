package com.ruviapps.tacklingnephrotic.domain.use_cases.caretaker

import com.ruviapps.tacklingnephrotic.database.dto.QueryResult
import com.ruviapps.tacklingnephrotic.database.entities.CareTakerWithPatients
import com.ruviapps.tacklingnephrotic.repository.CareTakerLocalRepository
import javax.inject.Inject

class ListOfCareTakersWithPatients @Inject constructor(
   private val careTakerLocalRepository: CareTakerLocalRepository
        ){
    suspend operator fun invoke(): QueryResult<List<CareTakerWithPatients>>{
        return careTakerLocalRepository.getCareTakerWithPatients()
    }
}