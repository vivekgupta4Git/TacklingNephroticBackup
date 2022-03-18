package com.ruviapps.tacklingnephrotic.database.dao

import androidx.room.*
import com.ruviapps.tacklingnephrotic.database.entities.*

@Dao
interface MedicineDao {

    @Insert(onConflict =OnConflictStrategy.REPLACE)
    suspend fun insertMedicine(medicine: Medicines)

    @Insert(onConflict =OnConflictStrategy.REPLACE)
    suspend fun insertAllMedicines(vararg medicines: Medicines)

    @Query("Select * from " + TableName.MedicinesTable)
    suspend fun getAllMedicines() : List<Medicines>?

    @Query("Select * from " + TableName.MedicinesTable + " where "
            + Medicines.ColumnMedicineCode + " = :id")
    suspend fun getMedicineById(id : Long) : Medicines?

    @Query("Select ${Medicines.ColumnMedicineCode} from ${TableName.MedicinesTable} " +
            "where ${Medicines.ColumnMedicineName} like :name LIMIT 1")
    suspend fun getMedicineCodeByName(name: String) : Long
}

@Dao
interface MedicineUnitDao{

    @Insert(onConflict =OnConflictStrategy.REPLACE)
    suspend fun insertMedicineUnit(medicineUnit: MedicineUnit)

    @Query("Select * from ${TableName.MedicineUnitTable}")
    suspend fun getAllMedicineUnit() : List<MedicineUnit>?

    @Query("Select * from ${TableName.MedicineUnitTable} where ${MedicineUnit.ColumnUnitId} = :id")
    suspend fun getMedicineUnitById(id : Long) : MedicineUnit?

    @Query("Select ${MedicineUnit.ColumnUnitId} from ${TableName.MedicineUnitTable} " +
            "where ${MedicineUnit.ColumnUnitName} like :name LIMIT 1")
    suspend fun getMedicineUnitIdByName(name : String) :Long

    @Delete
    suspend fun deleteMedicineUnit(medicineUnit: MedicineUnit)
}

@Dao
interface MedicineFrequencyDao{
    @Insert(onConflict =OnConflictStrategy.REPLACE)
    suspend fun insertFrequency(frequency: Frequency)

    @Query("Select * from ${TableName.FrequencyTable} Where ${Frequency.ColumnFrequencyCode} = :id")
    suspend fun getFrequencyById(id : Long) : Frequency?

    @Query("Select ${Frequency.ColumnFrequencyCode} from ${TableName.FrequencyTable} where ${Frequency.ColumnFrequencyName} like :name LIMIT 1")
    suspend fun getFrequencyCodeByName(name : String) : Long
}

@Dao
interface SideEffectDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSideEffectName(sideEffect: SideEffect)

    @Query("Select * from ${TableName.SideEffectTable}")
    suspend fun getAllSideEffects() : List<SideEffect>?

    @Query("Select ${SideEffect.ColumnSideEffectId} from ${TableName.SideEffectTable} where ${SideEffect.ColumnsSideEffectName} like :name LIMIT 1")
    suspend fun getSideEffectIdByName(name: String) : Long
}

@Dao
interface SideEffectToPatientDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSideEffectToPatient(sideEffectToPatient: SideEffectToPatient)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllSideEffectsToPatient(vararg sideEffectToPatient: SideEffectToPatient)



    @Query("Select * from ${TableName.SideEffectToPatientTable} where ${SideEffectToPatient.ColumnPatientId} = :id")
    suspend fun getAllSideEffectsToPatientsById(id: Long) : List<SideEffectToPatient>?
}

@Dao
interface MedicinesAdministeredDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMedicinesAdministered(medicinesAdministered: MedicinesAdministered)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllMedicinesAdministered(vararg medicinesAdministered: MedicinesAdministered)

    @Query("Select * from ${TableName.MedicinesAdministeredTable}")
    suspend fun getAllMedicinesAdministered() : List<MedicinesAdministered>?

    @Query("Select * from ${TableName.MedicinesAdministeredTable}" +
            " where ${MedicinesAdministered.ColumnPatientId} = :id")
    suspend fun getMedicinesAdministeredByPatientId(id : Long) : List<MedicinesAdministered>?

    @Transaction
    @Query("Select * from ${TableName.MedicinesAdministeredTable} " +
            " where ${MedicinesAdministered.ColumnPatientId} = :id")
    suspend fun getMedicinesAdministeredWithMedicinesGivenDetailsByPatientId(id : Long)
    : List<MedicinesAdministeredWithMedicinesGivenDetails>?
}

@Dao
interface MedicinesGivenDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMedicinesGiven(medicinesGivenDetails: MedicinesGivenDetails)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllMedicinesGiven(vararg medicinesGivenDetails: MedicinesGivenDetails)

    @Query("Select * from ${TableName.MedicinesGivenDetailsTable}")
    suspend fun getAllMedicinesGiven() : List<MedicinesGivenDetails>?

    @Query("Select * from ${TableName.MedicinesGivenDetailsTable}" +
            " where ${MedicinesGivenDetails.ColumnAdministeredId} = :id")
    suspend fun getMedicinesGivenByAdministeredId(id : Long) : List<MedicinesGivenDetails>?

}

