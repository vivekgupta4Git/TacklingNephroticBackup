package com.ruviApps.tacklingnephrotic.domain.use_cases.result

import com.ruviApps.tacklingnephrotic.database.dto.QueryResult
import com.ruviApps.tacklingnephrotic.database.entities.UrineResult
import com.ruviApps.tacklingnephrotic.domain.Patient
import com.ruviApps.tacklingnephrotic.repository.ResultLocalRepository

class AllResultForPatientUseCase(
   val repository: ResultLocalRepository
) {
    suspend operator fun invoke(patient : Patient) : QueryResult<List<UrineResult>>{
        return repository.getResultsByPatientId(patient.patientId)
    }
}