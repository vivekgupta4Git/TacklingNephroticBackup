package com.ruviapps.tacklingnephrotic.domain.use_cases.caretaker

import com.ruviapps.tacklingnephrotic.repository.CareTakerLocalRepository
import javax.inject.Inject

class GetAllCareTakersUseCase @Inject constructor(
    private val repository: CareTakerLocalRepository
){
    suspend operator fun invoke() = repository.getCareTakers()
}
