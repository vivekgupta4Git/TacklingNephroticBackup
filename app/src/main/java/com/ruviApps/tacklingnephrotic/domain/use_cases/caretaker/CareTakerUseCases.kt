package com.ruviApps.tacklingnephrotic.domain.use_cases.caretaker

data class CareTakerUseCases(
    val registerCareTakerUseCase: RegisterCareTakerUseCase,
    val getCareTakerUseCase: GetCareTakerUseCase,
    val getAllCareTakersUseCase: GetAllCareTakersUseCase,
    val patientsOfCareTakerUseCase: PatientsOfCareTakerUseCase,
    val deleteCareTakerUseCases: DeleteCareTakerUseCases,
    val deleteAllCareTakerUseCase: DeleteAllCareTakerUseCase,
    val listOfCareTakersWithPatients: ListOfCareTakersWithPatients
)
