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
import com.ruviApps.tacklingnephrotic.database.entities.ResultCode
import com.ruviApps.tacklingnephrotic.database.entities.UrineResult
import com.ruviApps.tacklingnephrotic.domain.CareTaker
import com.ruviApps.tacklingnephrotic.domain.Patient
import com.ruviApps.tacklingnephrotic.extension.toDatabaseCareTaker
import com.ruviApps.tacklingnephrotic.extension.toDatabasePatient
import com.ruviApps.tacklingnephrotic.repository.CareTakerLocalRepository
import com.ruviApps.tacklingnephrotic.repository.PatientLocalRepository
import com.ruviApps.tacklingnephrotic.repository.ResultLocalRepository
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*

@MediumTest
@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class LocalRepositoryTest {
    private lateinit var database : NephSyndDatabase
    private lateinit var careTakerRepo: CareTakerLocalRepository
    private lateinit var patientsRepo : PatientLocalRepository
    private lateinit var resultsRepo : ResultLocalRepository

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()


    @Before
    fun initializedDB(){
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            NephSyndDatabase::class.java
        ).allowMainThreadQueries().build()

        careTakerRepo = CareTakerLocalRepository(database.careTakerDao())
        patientsRepo = PatientLocalRepository(database.patientDao())
        resultsRepo = ResultLocalRepository(database.resultDao())


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

    val queryResult =  careTakerRepo.saveCareTaker(careTaker.toDatabaseCareTaker())

        queryResult.onSuccess { data, message ->
            assertEquals("Insert Successfully!",message)
        }

        val failQueryResult =  careTakerRepo.getCareTaker(2)
        failQueryResult.onFailure { message, statusCode ->
            assertEquals(QueryResult.QUERY_FAILURE_STATUS_CODE,statusCode)
        }

        val queryResultRepo = careTakerRepo.getCareTaker(careTaker.careTakerId)
        val repoSavedCareTaker = (queryResultRepo as QueryResult.Success).data
        assertEquals(careTaker.careTakerId,repoSavedCareTaker.ctId)

    /*    val repoSavedCareTaker = repository.getCareTaker(careTaker.careTakerId)
        val result = (repoSavedCareTaker as QueryResult.Success).data
        assertThat(repoSavedCareTaker,notNullValue())
        assertThat(result.ctId,`is`(careTaker.careTakerId))
*/


    }


    @Test
    fun patientsUnderCareTakerObservation_equalsToDataRetrievedFromRepo() = runBlocking{
        //given two patients are under observation for one careTaker
        val careTaker = CareTaker(1,"Vivek Gupta","itguru",9891417738L,0)
        careTakerRepo.saveCareTaker(careTaker.toDatabaseCareTaker())
        val patientOne = Patient(1,"Atharv Gupta",4,19.8f,"",1)
        val patientTwo = Patient(2,"Utkarsh",7,24f,"",1)
        patientsRepo.savePatient(patientOne.toDatabasePatient())
        patientsRepo.savePatient(patientTwo.toDatabasePatient())

        //when queried for all patients under observation for careTaker
        val result = careTakerRepo.getPatientsOfCareTaker(careTaker.careTakerId)

        //then verify that patients retrieved from repo is equal to the given
        result.onSuccess { data, message ->
                val listOfPatients = data[0].patients
                val ct = data[0].caretaker
            assertEquals(1,data.size)
           assertEquals(2,listOfPatients.size)
            assertEquals(careTaker.careTakerId,patientOne.underCareTakerId)
            assertEquals(careTaker.careTakerId,patientTwo.underCareTakerId)
            assertEquals(listOfPatients[0].patientId,patientOne.patientId)
            assertEquals(listOfPatients[1].patientId,patientTwo.patientId)
        }


    }

    @Test
    fun saveResults_twoPatientsThreeResults_equalToRepoData() = runBlocking {
        val careTaker = CareTaker(1,"Vivek Gupta","itguru",9891417738L,0)
        careTakerRepo.saveCareTaker(careTaker.toDatabaseCareTaker())
        val patientOne = Patient(1,"Atharv Gupta",4,19.8f,"",1)
        val patientTwo = Patient(2,"Utkarsh",7,24f,"",1)
        patientsRepo.savePatient(patientOne.toDatabasePatient())
        patientsRepo.savePatient(patientTwo.toDatabasePatient())

           //given results of patient one
        val resultOne = UrineResult(1,ResultCode.TWO_PLUS,null,
        Calendar.getInstance().time,1)
        val resultTwo = UrineResult(2,ResultCode.THREE_PLUS,null,
            Calendar.getInstance().time,1)
        val resultThree = UrineResult(3,ResultCode.FOUR_PLUS,null,
            Calendar.getInstance().time,1)

        val list = listOf(resultOne,resultTwo,resultThree)

        //when inserting results of patient one  only
        resultsRepo.insertAllResults(list)


        //then assert db has records of patient one only
        val qR = resultsRepo.getResultsByPatientId(patientOne.patientId)
        qR.onSuccess { data, message ->
            assertEquals(3,data.size)

        }
        //and no records of patient two
        val qR2 = resultsRepo.getResultsByPatientId(patientTwo.patientId)
        qR2.onFailure { message, statusCode ->
            assertEquals(QueryResult.QUERY_FAILURE_STATUS_CODE,statusCode)
        }

    }

}