package com.ruviApps.tacklingnephrotic.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.ruviApps.tacklingnephrotic.database.entities.DatabaseCareTaker
import com.ruviApps.tacklingnephrotic.domain.CareTaker

@Dao
interface CareTakerDao {

    @Insert
    fun insertAllCareTakers(vararg databaseCareTaker: DatabaseCareTaker)

    @Insert
    fun insertCareTaker(databaseCareTaker: DatabaseCareTaker)

    @Query( "Select * from DatabaseCareTaker")
    fun getAllCareTakers(): List<DatabaseCareTaker>



}
