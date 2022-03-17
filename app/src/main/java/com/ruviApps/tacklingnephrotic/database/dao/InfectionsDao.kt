package com.ruviApps.tacklingnephrotic.database.dao

import androidx.room.*
import com.ruviApps.tacklingnephrotic.database.entities.DailyLog
import com.ruviApps.tacklingnephrotic.database.entities.Infections
import com.ruviApps.tacklingnephrotic.database.entities.TableName

@Dao
interface InfectionsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInfection(infection: Infections)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllInfections(vararg infections: Infections)

    @Query("Select * from ${TableName.InfectionTable}")
    suspend fun getAllInfections() : List<Infections>

    @Query("Select * from ${TableName.InfectionTable} where ${Infections.ColumnToPatientId} = :id ORDER BY " +
            " ${Infections.ColumnEndDate} DESC")
    suspend fun getInfectionsForPatient(id: Long) : List<Infections>

    @Delete
    suspend fun deleteInfection(infection: Infections)

    @Update
    suspend fun updateInfection(infection: Infections)
}