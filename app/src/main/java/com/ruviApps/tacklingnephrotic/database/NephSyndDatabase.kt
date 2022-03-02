package com.ruviApps.tacklingnephrotic.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ruviApps.tacklingnephrotic.converters.DateConverter
import com.ruviApps.tacklingnephrotic.converters.ResultConverter
import com.ruviApps.tacklingnephrotic.database.dao.CareTakerDao
import com.ruviApps.tacklingnephrotic.database.dao.PatientDao
import com.ruviApps.tacklingnephrotic.database.dao.ResultDao
import com.ruviApps.tacklingnephrotic.database.entities.DatabaseCareTaker
import com.ruviApps.tacklingnephrotic.database.entities.DatabasePatient
import com.ruviApps.tacklingnephrotic.database.entities.UrineResult


@Database(entities = [DatabaseCareTaker::class,
    DatabasePatient::class,UrineResult::class], version = 1,

exportSchema = false)
@TypeConverters(ResultConverter::class,
    DateConverter::class
)
abstract class NephSyndDatabase: RoomDatabase(){
    abstract fun careTakerDao() : CareTakerDao
    abstract fun patientDao() : PatientDao
    abstract fun resultDao() : ResultDao

}