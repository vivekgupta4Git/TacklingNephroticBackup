package com.ruviApps.tacklingnephrotic.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ruviApps.tacklingnephrotic.database.dao.NephDao
import com.ruviApps.tacklingnephrotic.database.entities.DatabaseCareTaker


@Database(entities = [DatabaseCareTaker::class], version = 1,
exportSchema = false)
abstract class NephSyndDatabase: RoomDatabase(){
    abstract fun nephDao() : NephDao
}