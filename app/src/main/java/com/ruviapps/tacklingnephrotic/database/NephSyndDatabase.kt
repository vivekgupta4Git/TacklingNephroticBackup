package com.ruviapps.tacklingnephrotic.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ruviapps.tacklingnephrotic.converters.DateConverter
import com.ruviapps.tacklingnephrotic.converters.HeathStatusConverter
import com.ruviapps.tacklingnephrotic.converters.NephroticStateConverter
import com.ruviapps.tacklingnephrotic.converters.ResultConverter
import com.ruviapps.tacklingnephrotic.database.dao.*
import com.ruviapps.tacklingnephrotic.database.entities.*


@Database(entities = [
    /*1*/   DatabaseCareTaker::class,
    /*2*/   DatabasePatient::class,
    /*3*/   UrineResult::class,
    /*4*/   Medicines::class,
    /*5*/   Doctor::class,
    /*6*/   MedicineUnit::class,
    /*7*/   Frequency::class,
    /*8*/   Diseases::class,
    /*9*/   DailyLog::class,
    /*10*/  MedicinesAdministered::class,
    /*11*/  DatabaseConsultation::class,
    /*12*/  MedicinesGivenDetails::class,
    /*13*/  DatabaseRelapse::class,
    /*14*/  SideEffect::class,
    /*15*/  SideEffectToPatient::class,
    /*16*/  PrescribedMedicines::class,
    /*17*/  PrescriptionDetails::class,
    /*18*/  Infections::class
                     ], version = 1, exportSchema = false)
@TypeConverters(ResultConverter::class,
    DateConverter::class,
    NephroticStateConverter::class,
    HeathStatusConverter::class
)
abstract class NephSyndDatabase: RoomDatabase(){
    abstract fun careTakerDao() : CareTakerDao
    abstract fun patientDao() : PatientDao
    abstract fun resultDao() : ResultDao
    abstract fun medicineDao() : MedicineDao
    abstract fun doctorDao() : DoctorDao
    abstract fun medicineUnitDao() : MedicineUnitDao
    abstract fun medicineFrequencyDao() : MedicineFrequencyDao
    abstract fun diseaseDao() :DiseasesDao
    abstract fun dailyLogDao() : DailyLogDao
    abstract fun medicineAdministeredDao() : MedicinesAdministeredDao
    abstract fun consultationDao() : ConsultationDao
    abstract fun medicineGivensDetailsDao() : MedicinesGivenDao
    abstract fun relapseDao() : RelapseDao
    abstract fun sideEffectDao() : SideEffectDao
    abstract fun sideEffectToPatientDao() : SideEffectToPatientDao
    abstract fun prescribedMedicinesDao() : PrescribedMedicinesDao
    abstract fun prescribtionDetailsDao() : PrescriptionDetailsDao
    abstract fun infectionsDao() : InfectionsDao





}