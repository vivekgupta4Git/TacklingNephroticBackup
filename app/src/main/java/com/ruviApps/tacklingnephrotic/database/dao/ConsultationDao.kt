package com.ruviApps.tacklingnephrotic.database.dao

import androidx.room.*
import com.google.android.material.tabs.TabLayout
import com.ruviApps.tacklingnephrotic.database.entities.*
import java.util.*

@Dao
interface ConsultationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertConsultation(databaseConsultation: DatabaseConsultation)

    @Query("Select * from ${TableName.ConsultationTable}")
    suspend fun getAllConsultations() : List<DatabaseConsultation>

    @Query("Select * from ${TableName.ConsultationTable} where " +
            "${DatabaseConsultation.ColumnPatientId} =:id")
    suspend fun getConsultationsForPatientById(id : Long) : List<DatabaseConsultation>

    @Delete
    suspend fun deleteConsultation(databaseConsultation: DatabaseConsultation)

    @Query("Select * From ${TableName.ConsultationTable} where ${DatabaseConsultation.ColumnDoctorId} =:id")
    suspend fun getConsultationsOfDoctor(id : Long) : List<DatabaseConsultation>

    @Query("Select * from ${TableName.ConsultationTable} where " +
            " ${DatabaseConsultation.ColumnPatientId} = :patientId And ${DatabaseConsultation.ColumnDoctorId} " +
            " = :doctorId ORDER BY ${DatabaseConsultation.ColumnVisitDate} DESC")
    suspend fun getConsultationId(patientId : Long,doctorId : Long) : List<DatabaseConsultation>


    @Transaction
    @Query("Select * from ${TableName.ConsultationTable} where ${DatabaseConsultation.ColumnConsultId} = :id")
     fun getConsultationWithDetails(id : Long) : List<ConsultationWithDetails>

}

@Dao
interface PrescriptionDetailsDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPrescriptionDetails(prescriptionDetails: PrescriptionDetails)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllPrescriptionsDetails(vararg prescriptionDetails: PrescriptionDetails)

    @Query("Select * from ${TableName.PrescriptionDetailsTable}")
    suspend fun getAllPrescriptionDetails() : List<PrescriptionDetails>?

    @Query("Select * from ${TableName.PrescriptionDetailsTable}" +
            " where ${PrescriptionDetails.ColumnConsultId} = :id")
    suspend fun getPrescriptionDetailsByConsultId(id: Long) : List<PrescriptionDetails>?

    @Query("Select ${PrescriptionDetails.ColumnNextFollowUp} from ${TableName.PrescriptionDetailsTable}" +
            " where ${PrescriptionDetails.ColumnConsultId} = :id")
    suspend fun getNextFollowUpDateByConsultId(id: Long) : Date?
}

@Dao
interface PrescribedMedicinesDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPrescribedMedicines(prescribedMedicines: PrescribedMedicines)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllPrescribedMedicines(vararg prescribedMedicines: PrescribedMedicines)

    @Query("Select * from ${TableName.PrescribedMedicinesTable}")
    suspend fun getAllPrescribedMedicines() : List<PrescribedMedicines>?

    @Query("Select * from ${TableName.PrescribedMedicinesTable}" +
            " where ${PrescribedMedicines.ColumnPrescriptionId} = :id")
    suspend fun getPrescribedMedicinesByPrescriptionId(id: Long) : List<PrescribedMedicines>?

}


