package com.ruviApps.tacklingnephrotic.domain.use_cases.patient

import com.ruviApps.tacklingnephrotic.database.dto.QueryResult
import com.ruviApps.tacklingnephrotic.domain.Patient
import com.ruviApps.tacklingnephrotic.domain.use_cases.caretaker.RegisterCareTakerUseCase.Companion.VALIDATION_ERROR
import com.ruviApps.tacklingnephrotic.extension.toDatabasePatient
import com.ruviApps.tacklingnephrotic.repository.PatientLocalRepository

class AddPatientUseCase(
    private val repository: PatientLocalRepository
) {
    suspend operator fun invoke  (
        patientName : String,
        patientAge : Int?,
        patientWeight : Float?,
        patientPicUri : String = "",
        underCareTakerId: Long
    ) : QueryResult<Long>
    {
        if(patientName.isEmpty()||patientName.isBlank())
            return QueryResult.Error("Name Cannot be null",VALIDATION_ERROR)

        if(patientWeight==null){
            return QueryResult.Error("Weight Cannot be null", VALIDATION_ERROR)
        }
        else if( patientWeight < 0){
                return QueryResult.Error("Weight Cannot be Negative", VALIDATION_ERROR)
        }

        if(underCareTakerId < 0 )
            return QueryResult.Error("No CareTaker is present", VALIDATION_ERROR)

        val patient = Patient(0,patientName,patientAge,patientWeight,patientPicUri,underCareTakerId)

       return repository.savePatient(patient.toDatabasePatient())
    }
}