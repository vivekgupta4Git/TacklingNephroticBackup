package com.ruviApps.tacklingnephrotic.database.dao

import androidx.room.*
import com.ruviApps.tacklingnephrotic.database.dto.QueryResult
import com.ruviApps.tacklingnephrotic.database.entities.CareTakerWithPatients
import com.ruviApps.tacklingnephrotic.database.entities.DatabaseCareTaker
import com.ruviApps.tacklingnephrotic.database.entities.DatabasePatient
import com.ruviApps.tacklingnephrotic.database.entities.TableName
import com.ruviApps.tacklingnephrotic.domain.CareTaker
import kotlinx.coroutines.Deferred

@Dao
interface CareTakerDao {

   @Insert(onConflict = OnConflictStrategy.REPLACE)
   suspend fun insertAllCareTakers(vararg databaseCareTaker: DatabaseCareTaker) : List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
   suspend fun insertCareTaker(databaseCareTaker: DatabaseCareTaker) : Long

    @Query( "Select * from " + TableName.CaretakerTable)
   suspend fun getAllCareTakers(): List<DatabaseCareTaker>

    @Query("Select * from " +  TableName.CaretakerTable + " where "+ DatabaseCareTaker.ColumnCareTakerId + " = :id")
    suspend fun getCareTakerById(id : Long) : DatabaseCareTaker

    @Transaction
    @Query("Select * from " + TableName.CaretakerTable + " where " + DatabaseCareTaker.ColumnCareTakerId + " = :id")
    suspend fun patientsOfCareTaker(id : Long) : List<CareTakerWithPatients>

    @Query("Delete from ${TableName.CaretakerTable}")
    suspend fun deleteAllCareTaker()

    @Delete
    suspend fun deleteCareTaker(careTaker: DatabaseCareTaker)

    @Transaction
    @Query("SELECT * FROM ${TableName.CaretakerTable}")
    fun getListOfCareTakersWithPatients(): List<CareTakerWithPatients>

}
