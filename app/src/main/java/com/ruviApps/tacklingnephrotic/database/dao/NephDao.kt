package com.ruviApps.tacklingnephrotic.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ruviApps.tacklingnephrotic.database.entities.DatabaseCareTaker
import com.ruviApps.tacklingnephrotic.database.entities.DatabasePatient
import com.ruviApps.tacklingnephrotic.database.entities.TableName
import com.ruviApps.tacklingnephrotic.domain.CareTaker

@Dao
interface NephDao {

 /*   @Insert(onConflict = OnConflictStrategy.REPLACE)
   suspend fun insertAllCareTakers(vararg databaseCareTaker: DatabaseCareTaker)
*/
    @Insert(onConflict = OnConflictStrategy.REPLACE)
   suspend fun insertCareTaker(databaseCareTaker: DatabaseCareTaker)

  /*  @Query( "Select * from DatabaseCareTaker")
   suspend fun getAllCareTakers(): List<DatabaseCareTaker?>
*/

    @Query("Select * from " +  TableName.CaretakerTable + " where "+ DatabaseCareTaker.ColumnCareTakerId + " = :id")
    suspend fun getCareTakerById(id : Long) : DatabaseCareTaker?


  /*  @Query("Select * from DatabasePatient")
   suspend fun getPatientList(): List<DatabasePatient?>
*/
}
