package com.ruviApps.tacklingnephrotic.database.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.ruviApps.tacklingnephrotic.database.NephSyndDatabase
import com.ruviApps.tacklingnephrotic.domain.CareTaker
import com.ruviApps.tacklingnephrotic.extension.toDatabaseCareTaker
import junit.framework.Assert.assertNotNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class NephDaoTest {
    private lateinit var database : NephSyndDatabase
    private lateinit var nephDao: NephDao

    @get:Rule
    val instantTaskExecutor = InstantTaskExecutorRule()

    @Before
    fun initializedDatabase(){
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            NephSyndDatabase::class.java).
                allowMainThreadQueries().build()
        nephDao =database.nephDao()
    }

    @After
    fun closeDb(){
        database.close()
    }

    @Test
    fun saveCareTaker_newCareTaker_addedToDatabase()  = runBlockingTest {
        val careTaker = CareTaker(1,
            "Vivek Gupta",
            "itguru4all@gmail.com",
            9891,0
            )

        nephDao.insertCareTaker(careTaker.toDatabaseCareTaker())
        val addedCareTaker = nephDao.getCareTakerById(careTaker.careTakerId)
        assertNotNull(addedCareTaker)


    }
}