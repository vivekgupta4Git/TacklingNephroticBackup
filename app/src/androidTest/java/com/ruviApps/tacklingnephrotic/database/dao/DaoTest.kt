package com.ruviApps.tacklingnephrotic.database.dao

import android.database.sqlite.SQLiteConstraintException
import android.util.Size
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.ruviApps.tacklingnephrotic.database.NephSyndDatabase
import com.ruviApps.tacklingnephrotic.database.entities.CareTakerWithPatients
import com.ruviApps.tacklingnephrotic.database.entities.ResultCode
import com.ruviApps.tacklingnephrotic.database.entities.UrineResult
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
import java.util.*

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class DaoTest {
    private lateinit var database : NephSyndDatabase
    private lateinit var careTakerDao: CareTakerDao
    private lateinit var patientDao: PatientDao
    private lateinit var resultDao: ResultDao
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
        resultDao = database.resultDao()
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
        assertEquals(careTaker.email,addedCareTaker?.contact?.email)

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

        //Relation between entities
        val patientsUnderCareTaker: List<CareTakerWithPatients>? = careTakerDao.getAllPatientsWithCareTakerId(careTaker.careTakerId)

        assertNotNull(addedCareTaker)
        assertEquals(careTaker.careTakerId,addedCareTaker?.ctId)

        assertNotNull(patients)
        assertEquals(patient_one.patientId,patients?.first()?.patientId)
        assertEquals(patient_two.patientId,patients?.get(1)?.patientId)

        //testing relation
        assertEquals(careTaker.careTakerId,
                    patientsUnderCareTaker?.first()?.patients?.first()?.patientCaretakerId
                )

        assertEquals(careTaker.careTakerId,
            patientsUnderCareTaker?.first()?.patients?.last()?.patientCaretakerId
        )

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


    @Test
    fun saveResult_OneCareTakerTwoPatientThreeResultsEach_addedToDatabase() = runBlockingTest {

        //one CareTaker
        val careTaker = CareTaker(1, "Vivek Gupta","itgurur4all@gmail.com",98941,0)
        careTakerDao.insertCareTaker(careTaker.toDatabaseCareTaker())
       // val addedCareTaker = careTakerDao.getCareTakerById(careTaker.careTakerId)


        //Two patients
        val patient_one = Patient(1,"Atharv Gupta",4,19.3f,"",careTaker.careTakerId)
        val patient_two = Patient(2,"Utkarsh Gupta",7,25.6f,"",careTaker.careTakerId)

        val patientList = mutableListOf<Patient>()
        patientList.add(patient_one)
        patientList.add(patient_two)
        patientDao.insertAllPatient(*patientList.toDatabasePatient().toTypedArray())

      //  val patients = patientDao.getAllPatient()

        //Three Results of Patient_one
        val Patient_one_Result_one = UrineResult(1,ResultCode.FOUR_PLUS,
            "Cold",Calendar.getInstance().time,patient_one.patientId)
        resultDao.insertResult(Patient_one_Result_one)
        val Patient_one_result_two = UrineResult(2,ResultCode.THREE_PLUS,
            "Cough",Calendar.getInstance().time,patient_one.patientId)
        resultDao.insertResult(Patient_one_result_two)
        val Patient_one_result_three = UrineResult(3,ResultCode.TWO_PLUS,
            "Recovered",Calendar.getInstance().time,patient_one.patientId)
        resultDao.insertResult(Patient_one_result_three)

        //Three Results of Patient_two
        val Patient_two_Result_one = UrineResult(1,ResultCode.ONE_PLUS,
            "",Calendar.getInstance().time,patient_two.patientId)
        resultDao.insertResult(Patient_two_Result_one)
        val Patient_two_result_two = UrineResult(2,ResultCode.ONE_PLUS,
            "",Calendar.getInstance().time,patient_two.patientId)
        resultDao.insertResult(Patient_two_result_two)
        val Patient_two_result_three = UrineResult(3,ResultCode.NEGATIVE,
            "",Calendar.getInstance().time,patient_two.patientId)
        resultDao.insertResult(Patient_two_result_three)


        val patientWithResults = patientDao.getPatientWithResults(patient_one.patientId)


        val resultsOfPatientOne = resultDao.getAllResultsForPatient(patient_one.patientId)
        val resultsOfPatientTwo = resultDao.getAllResultsForPatient(patient_two.patientId)

        assertNotNull(resultsOfPatientOne)
        assertNotNull(resultsOfPatientTwo)
      //  assertEquals(ResultCode.ONE_PLUS,resultsOfPatientTwo.first().resultCode)

        assertNotNull(patientWithResults)
        assertEquals(1,
            patientWithResults.get(0).patient.patientId
            )
        assertNotNull(patientWithResults.get(0).urineResults)
    }

}