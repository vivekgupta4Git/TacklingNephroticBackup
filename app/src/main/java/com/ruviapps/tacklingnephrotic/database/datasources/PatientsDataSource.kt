package com.ruviapps.tacklingnephrotic.database.datasources

import com.ruviapps.tacklingnephrotic.database.dto.QueryResult
import com.ruviapps.tacklingnephrotic.database.entities.*

interface PatientsDataSource {

    suspend fun savePatient(databasePatient: DatabasePatient) : QueryResult<Long>
    suspend fun getAllPatients() : QueryResult<List<DatabasePatient>>
    suspend fun getPatientById(id : Long) : QueryResult<DatabasePatient>
    suspend fun saveAllPatients(patient: List<DatabasePatient>) : QueryResult<Unit>
    suspend fun deleteAllPatients() : QueryResult<Unit>
    suspend fun deletePatient(databasePatient: DatabasePatient) : QueryResult<Unit>
    suspend fun getPatientsWithUrineResults(patientId : Long) : QueryResult<List<PatientWithUrineResults>>
    suspend fun getPatientsAllAdvices(patientId: Long) : QueryResult<List<PatientWithConsultations>>

}