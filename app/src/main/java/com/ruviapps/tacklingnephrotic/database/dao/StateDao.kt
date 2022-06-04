package com.ruviapps.tacklingnephrotic.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ruviapps.tacklingnephrotic.database.entities.DatabasePatientState
import com.ruviapps.tacklingnephrotic.database.entities.TableName

@Dao
interface StateDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertState(patientState: DatabasePatientState)

    /**
     * this method may decrease performance
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllState(vararg patientState: DatabasePatientState)

    @Query("Select * from ${TableName.StateTable}")
    suspend fun getAllStatesForAllPatients() : List<DatabasePatientState>

    @Query("Select * from ${TableName.StateTable} where ${DatabasePatientState.ColumnPatientId} = :id")
    suspend fun getStatesForPatientId(id : Long) : List<DatabasePatientState>


}