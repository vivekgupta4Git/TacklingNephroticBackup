package com.ruviApps.tacklingnephrotic.domain.use_cases.caretaker

import com.ruviApps.tacklingnephrotic.repository.CareTakerLocalRepository

class GetCareTakerUseCase(
    private val repository: CareTakerLocalRepository
){
    suspend operator fun invoke(id : Long) = repository.getCareTaker(id)
}
