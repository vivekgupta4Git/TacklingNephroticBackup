package com.ruviapps.tacklingnephrotic.di.module.dbmodule

import com.ruviapps.tacklingnephrotic.database.NephSyndDatabase
import com.ruviapps.tacklingnephrotic.database.dao.CareTakerDao
import com.ruviapps.tacklingnephrotic.database.dao.PatientDao
import com.ruviapps.tacklingnephrotic.database.dao.ResultDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    @Singleton
  //  @Named("Patient")
    fun providePatientDao(appDatabase : NephSyndDatabase) : PatientDao {
        return appDatabase.patientDao()
    }

    @Provides
    @Singleton
    fun provideCareTakerDao(appDatabase : NephSyndDatabase) : CareTakerDao {
        return appDatabase.careTakerDao()
    }

    @Provides
    @Singleton
    fun provideResultDao(appDatabase : NephSyndDatabase) : ResultDao {
        return appDatabase.resultDao()
    }

    /**
     * similarly more dao
     */



}