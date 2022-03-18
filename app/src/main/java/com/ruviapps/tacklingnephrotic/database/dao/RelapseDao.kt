package com.ruviapps.tacklingnephrotic.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ruviapps.tacklingnephrotic.database.entities.DatabaseRelapse
import com.ruviapps.tacklingnephrotic.database.entities.TableName

@Dao
interface RelapseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRelapse(relapse: DatabaseRelapse)

    /**
     * this method may decrease performance
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllRelapse(vararg relapse: DatabaseRelapse)

    @Query("Select * from ${TableName.RelapsesTable}")
    suspend fun getAllRelapsesForAllPatients() : List<DatabaseRelapse>

    @Query("Select * from ${TableName.RelapsesTable} where ${DatabaseRelapse.ColumnPatientId} = :id")
    suspend fun getRelapsesForPatientId(id : Long) : List<DatabaseRelapse>


}