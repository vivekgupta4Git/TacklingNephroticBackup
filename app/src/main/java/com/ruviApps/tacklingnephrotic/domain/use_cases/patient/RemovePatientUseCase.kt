package com.ruviApps.tacklingnephrotic.domain.use_cases.patient

import com.ruviApps.tacklingnephrotic.database.dto.QueryResult
import com.ruviApps.tacklingnephrotic.domain.Patient
import com.ruviApps.tacklingnephrotic.domain.use_cases.caretaker.RegisterCareTakerUseCase.Companion.VALIDATION_ERROR
import com.ruviApps.tacklingnephrotic.extension.toDatabasePatient
import com.ruviApps.tacklingnephrotic.repository.PatientLocalRepository

class RemovePatientUseCase(
    private val repository: PatientLocalRepository
) {
    //hurray connected.
    suspend operator fun invoke  (
       patient : Patient
    ) =repository.deletePatient(patient.toDatabasePatient())

}