package com.ruviApps.tacklingnephrotic.domain.use_cases.result

data class UrineResultUseCases(
    val insertUrineResultUseCase: InsertUrineResultUseCase,
    val deleteAllResultUseCase: DeleteAllResultUseCase,
    val deleteUrineResultUseCase: DeleteUrineResultUseCase,
    val updateUrineResultUseCase: UpdateUrineResultUseCase,
    val resultsOfAllPatientUseCase: ResultsOfAllPatientUseCase,
    val allResultForPatientUseCase: AllResultForPatientUseCase
)
