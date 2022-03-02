package com.ruviApps.tacklingnephrotic.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ruviApps.tacklingnephrotic.database.entities.TableName
import com.ruviApps.tacklingnephrotic.database.entities.UrineResult

@Dao
interface ResultDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertResult(urineResult: UrineResult)

    /**
     * This method may decrease performance
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllResult(vararg urineResult: UrineResult)


    @Query("Select * from " + TableName.UrineResultTable+ " where "
            + UrineResult.ColumnPatientId + " = :id ORDER BY " + UrineResult.ColumnRecordedDate + " DESC")
    suspend fun getAllResultsForPatient(id : Long) : List<UrineResult>




}