package com.ruviapps.tacklingnephrotic.di.module.repomodule

import com.ruviapps.tacklingnephrotic.database.dao.CareTakerDao
import com.ruviapps.tacklingnephrotic.database.dao.PatientDao
import com.ruviapps.tacklingnephrotic.database.dao.ResultDao
import com.ruviapps.tacklingnephrotic.repository.CareTakerLocalRepository
import com.ruviapps.tacklingnephrotic.repository.PatientLocalRepository
import com.ruviapps.tacklingnephrotic.repository.ResultLocalRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Named


@InstallIn(ActivityComponent::class)
@Module
object RepositoryModule {

    /**
     * Named annotation to differentiate between same return type
     */
    @Provides
    @ActivityScoped
    fun providePatientRepo(dao: PatientDao, ioDispatcher : CoroutineDispatcher=Dispatchers.IO): PatientLocalRepository {
        return PatientLocalRepository( dao, ioDispatcher)

    }

    @Provides
    @ActivityScoped
    fun provideResultRepo(dao: ResultDao,ioDispatcher: CoroutineDispatcher=Dispatchers.IO): ResultLocalRepository{
        return ResultLocalRepository(dao,ioDispatcher)
    }

    @Provides
    @ActivityScoped
    fun provideCareTakerRepo(dao: CareTakerDao,ioDispatcher: CoroutineDispatcher=Dispatchers.IO): CareTakerLocalRepository{
        return CareTakerLocalRepository(dao,ioDispatcher)
    }



}