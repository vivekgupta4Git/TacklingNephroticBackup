package com.ruviapps.tacklingnephrotic.domain.use_cases.result

import javax.inject.Inject

data class UrineResultUseCases @Inject constructor(
    val insertUrineResultUseCase: InsertUrineResultUseCase,
    val deleteAllResultUseCase: DeleteAllResultUseCase,
    val deleteUrineResultUseCase: DeleteUrineResultUseCase,
    val updateUrineResultUseCase: UpdateUrineResultUseCase,
    val resultsOfAllPatientUseCase: ResultsOfAllPatientUseCase,
    val allResultForPatientUseCase: AllResultForPatientUseCase
)
