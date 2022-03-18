package com.ruviapps.tacklingnephrotic.domain.use_cases.result

import com.ruviapps.tacklingnephrotic.database.dto.QueryResult
import com.ruviapps.tacklingnephrotic.database.entities.UrineResult
import com.ruviapps.tacklingnephrotic.domain.Patient
import com.ruviapps.tacklingnephrotic.repository.ResultLocalRepository
import javax.inject.Inject

class AllResultForPatientUseCase @Inject constructor(
   val repository: ResultLocalRepository
) {
    suspend operator fun invoke(patient : Patient) : QueryResult<List<UrineResult>>{
        return repository.getResultsByPatientId(patient.patientId)
    }
}