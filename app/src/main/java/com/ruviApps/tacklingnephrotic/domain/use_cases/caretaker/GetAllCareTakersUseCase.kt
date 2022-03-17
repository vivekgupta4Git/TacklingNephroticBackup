package com.ruviApps.tacklingnephrotic.domain.use_cases.caretaker

import com.ruviApps.tacklingnephrotic.repository.CareTakerLocalRepository

class GetAllCareTakersUseCase(
    private val repository: CareTakerLocalRepository
){
    suspend operator fun invoke() = repository.getCareTakers()
}
