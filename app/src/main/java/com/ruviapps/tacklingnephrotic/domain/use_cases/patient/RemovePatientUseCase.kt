package com.ruviapps.tacklingnephrotic.domain.use_cases.patient

import com.ruviapps.tacklingnephrotic.domain.Patient
import com.ruviapps.tacklingnephrotic.extension.toDatabasePatient
import com.ruviapps.tacklingnephrotic.repository.PatientLocalRepository
import javax.inject.Inject

class RemovePatientUseCase @Inject constructor(
    private val repository: PatientLocalRepository
) {
    //hurray connected.
    suspend operator fun invoke  (
       patient : Patient
    ) =repository.deletePatient(patient.toDatabasePatient())

}