package com.ruviApps.tacklingnephrotic.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ruviApps.tacklingnephrotic.database.dao.CareTakerDao
import com.ruviApps.tacklingnephrotic.database.dao.PatientDao
import com.ruviApps.tacklingnephrotic.database.entities.DatabaseCareTaker
import com.ruviApps.tacklingnephrotic.database.entities.DatabasePatient


@Database(entities = [DatabaseCareTaker::class,
    DatabasePatient::class], version = 1,
exportSchema = false)
abstract class NephSyndDatabase: RoomDatabase(){
    abstract fun careTakerDao() : CareTakerDao
    abstract fun patientDao() : PatientDao

}