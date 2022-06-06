package com.ruviapps.tacklingnephrotic.domain.use_cases.patient

import com.ruviapps.tacklingnephrotic.database.dto.QueryResult
import com.ruviapps.tacklingnephrotic.database.entities.DatabasePatient
import com.ruviapps.tacklingnephrotic.repository.PatientLocalRepository
import javax.inject.Inject

class GetPatientUseCase @Inject constructor(
    private val repository: PatientLocalRepository
) {
    suspend operator fun invoke(id : Long) : QueryResult<DatabasePatient> {
      return  repository.getPatientById(id)
    }
}