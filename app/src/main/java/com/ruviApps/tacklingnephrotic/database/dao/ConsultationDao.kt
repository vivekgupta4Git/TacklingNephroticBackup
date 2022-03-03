package com.ruviApps.tacklingnephrotic.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ruviApps.tacklingnephrotic.database.entities.DatabaseConsultation
import com.ruviApps.tacklingnephrotic.database.entities.PrescribedMedicines
import com.ruviApps.tacklingnephrotic.database.entities.PrescriptionDetails
import com.ruviApps.tacklingnephrotic.database.entities.TableName
import java.util.*

@Dao
interface ConsultationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertConsultation(databaseConsultation: DatabaseConsultation)

    @Query("Select * from ${TableName.ConsultationTable}")
    suspend fun getAllConsultations() : List<DatabaseConsultation>?

    @Query("Select * from ${TableName.ConsultationTable} where " +
            "${DatabaseConsultation.ColumnPatientId} =:id")
    suspend fun getConsultationsForPatientById(id : Long) : List<DatabaseConsultation>?

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


