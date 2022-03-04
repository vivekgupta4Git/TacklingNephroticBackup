package com.ruviApps.tacklingnephrotic.database.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.ruviApps.tacklingnephrotic.database.NephSyndDatabase
import com.ruviApps.tacklingnephrotic.database.dto.QueryResult
import com.ruviApps.tacklingnephrotic.database.dto.onFailure
import com.ruviApps.tacklingnephrotic.database.dto.onSuccess
import com.ruviApps.tacklingnephrotic.domain.CareTaker
import com.ruviApps.tacklingnephrotic.extension.toDatabaseCareTaker
import com.ruviApps.tacklingnephrotic.repository.CareTakerLocalRepository
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@MediumTest
@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class LocalRepositoryTest {
    private lateinit var database : NephSyndDatabase
    private lateinit var repository: CareTakerLocalRepository
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()


    @Before
    fun initializedDB(){
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            NephSyndDatabase::class.java
        ).allowMainThreadQueries().build()

        repository = CareTakerLocalRepository(database.careTakerDao(),Dispatchers.IO)

    }

    @After
    fun closeDB(){
        database.close()
    }

    @Test
    fun savedCareTaker_equalsToReposSavedCareTaker() = runBlocking {

        val careTaker = CareTaker(1,
                                    "Vivek Gupta",
                                    "itguru4all@gmail.com",
                                    9891417738L,
                                            0L
                                )

    val queryResult =  repository.saveCareTaker(careTaker.toDatabaseCareTaker())

        queryResult.onSuccess { data, message ->
            assertEquals("Insert Successfully!",message)
        }

        val failQueryResult =  repository.getCareTaker(2)
        failQueryResult.onFailure { message, statusCode ->
            assertEquals(QueryResult.QUERY_FAILURE_STATUS_CODE,statusCode)
        }

        val queryResultRepo = repository.getCareTaker(careTaker.careTakerId)
        val repoSavedCareTaker = (queryResultRepo as QueryResult.Success).data
        assertEquals(careTaker.careTakerId,repoSavedCareTaker.ctId)

    /*    val repoSavedCareTaker = repository.getCareTaker(careTaker.careTakerId)
        val result = (repoSavedCareTaker as QueryResult.Success).data
        assertThat(repoSavedCareTaker,notNullValue())
        assertThat(result.ctId,`is`(careTaker.careTakerId))
*/


    }


}