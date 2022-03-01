package com.ruviApps.tacklingnephrotic.database.dao

import android.database.sqlite.SQLiteConstraintException
import android.util.Size
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.ruviApps.tacklingnephrotic.database.NephSyndDatabase
import com.ruviApps.tacklingnephrotic.domain.CareTaker
import com.ruviApps.tacklingnephrotic.domain.Patient
import com.ruviApps.tacklingnephrotic.extension.toDatabaseCareTaker
import com.ruviApps.tacklingnephrotic.extension.toDatabasePatient
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.internal.matchers.Equals
import java.sql.SQLException

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class DaoTest {
    private lateinit var database : NephSyndDatabase
    private lateinit var careTakerDao: CareTakerDao
    private lateinit var patientDao: PatientDao
    @get:Rule
    val instantTaskExecutor = InstantTaskExecutorRule()

    @Before
    fun initializedDatabase(){
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            NephSyndDatabase::class.java).
                allowMainThreadQueries().build()
        careTakerDao =database.careTakerDao()
        patientDao = database.patientDao()
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

        careTakerDao.insertCareTaker(careTaker.toDatabaseCareTaker())
        val addedCareTaker = careTakerDao.getCareTakerById(careTaker.careTakerId)
        assertNotNull(addedCareTaker)
        assertEquals(careTaker.careTakerId,addedCareTaker?.ctId)
        assertThat(careTaker.email, `is`( Equals(addedCareTaker?.contact?.email)))

    }

    @Test
    fun savePatient_TwoPatientWithCareTaker_addedToDatabase() = runBlockingTest {
        val careTaker = CareTaker(1, "Vivek Gupta","itgurur4all@gmail.com",98941,0)
        careTakerDao.insertCareTaker(careTaker.toDatabaseCareTaker())
    val addedCareTaker = careTakerDao.getCareTakerById(careTaker.careTakerId)

        val patient_one = Patient(1,"Atharv Gupta",4,(19.3).toFloat(),"",careTaker.careTakerId)
        val patient_two = Patient(2,"Utkarsh Gupta",7,(25.6).toFloat(),"",careTaker.careTakerId)

        val patientList = mutableListOf<Patient>()
        patientList.add(patient_one)
        patientList.add(patient_two)
        patientDao.insertAllPatient(*patientList.toDatabasePatient().toTypedArray())

        val patients = patientDao.getAllPatient()
        assertNotNull(addedCareTaker)
        assertEquals(careTaker.careTakerId,addedCareTaker?.ctId)

        assertNotNull(patients)
        assertEquals(patient_one.patientId,patients?.first()?.patientId)
        assertEquals(patient_two.patientId,patients?.get(1)?.patientId)
    }

    @Test
    fun savePatient_ThrowError_notAddedToDatabase() = runBlockingTest {
        try{
            //no caretaker present but inserting patient
            val patient = Patient(1,"Atharv Gupta",
                4,(19.3).toFloat(),"",1)
            patientDao.insertPatient(patient.toDatabasePatient())
            Assert.fail("No CareTaker Present")
        }catch (e : SQLiteConstraintException){
            assert(true)
        }
    }

}