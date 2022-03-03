package com.ruviApps.tacklingnephrotic.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ruviApps.tacklingnephrotic.converters.DateConverter
import com.ruviApps.tacklingnephrotic.converters.ResultConverter
import com.ruviApps.tacklingnephrotic.database.dao.*
import com.ruviApps.tacklingnephrotic.database.entities.*


@Database(entities = [
    DatabaseCareTaker::class,
    DatabasePatient::class,
    UrineResult::class,
    Medicines::class,
    Doctor::class,
    MedicineUnit::class,
    Frequency::class,
    Diseases::class
                     ], version = 1, exportSchema = false)
@TypeConverters(ResultConverter::class,
    DateConverter::class
)
abstract class NephSyndDatabase: RoomDatabase(){
    abstract fun careTakerDao() : CareTakerDao
    abstract fun patientDao() : PatientDao
    abstract fun resultDao() : ResultDao
    abstract fun medicineDao() : MedicineDao
    abstract fun doctorDao() : DoctorDao
    abstract fun diseaseDao() :DiseasesDao
    abstract fun medicineUnitDao() : MedicineUnitDao
    abstract fun medicineFrequencyDao() : MedicineFrequencyDao


}