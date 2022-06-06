package com.ruviapps.tacklingnephrotic.domain.use_cases.stateCalculator

import javax.inject.Inject

data class CalculateStateUseCases @Inject constructor(
    val calculateStateFromReadingUseCae : CalculateStateFromReadingUseCase

)