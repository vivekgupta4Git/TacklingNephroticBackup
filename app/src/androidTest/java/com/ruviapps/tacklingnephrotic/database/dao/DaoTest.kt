package com.ruviapps.tacklingnephrotic.database.dao

import android.database.sqlite.SQLiteConstraintException
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.ruviapps.tacklingnephrotic.database.NephSyndDatabase
import com.ruviapps.tacklingnephrotic.database.entities.*
import com.ruviapps.tacklingnephrotic.domain.CareTaker
import com.ruviapps.tacklingnephrotic.domain.Patient
import com.ruviapps.tacklingnephrotic.extension.toDatabaseCareTaker
import com.ruviapps.tacklingnephrotic.extension.toDatabasePatient
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.*
import org.junit.runner.RunWith
import java.util.*

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class DaoTest {
    private lateinit var database : NephSyndDatabase
    private lateinit var careTakerDao: CareTakerDao
    private lateinit var patientDao: PatientDao
    private lateinit var resultDao: ResultDao
    private lateinit var doctorDao : DoctorDao
    private lateinit var medicineDao: MedicineDao
    private lateinit var medicineUnitDao: MedicineUnitDao
    private lateinit var frequencyDao: MedicineFrequencyDao
    private lateinit var diseasesDao: DiseasesDao


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
        doctorDao = database.doctorDao()
        medicineDao =database.medicineDao()
        medicineUnitDao = database.medicineUnitDao()
        frequencyDao = database.medicineFrequencyDao()
            diseasesDao = database.diseaseDao()

    }


suspend fun loadDatabaseForTesting(){
//Insert a CareTaker
    val careTaker = CareTaker(1,
        "Vivek Gupta",
        "itguru4all@gmail.com",
        "9891417738",null
    )
    careTakerDao.insertCareTaker(careTaker.toDatabaseCareTaker())

//Insert two Patient
    val patientOne = Patient(1,"Atharv Gupta",4,(19.3).toFloat(),"",careTaker.careTakerId)
    val patientTwo = Patient(2,"Utkarsh Gupta",7,(25.6).toFloat(),"",careTaker.careTakerId)

    val patientList = mutableListOf<Patient>()
    patientList.add(patientOne)
    patientList.add(patientTwo)
    patientDao.insertAllPatient(*patientList.toDatabasePatient().toTypedArray())

    ///Insert Results for both patients

    //Three Results of patientOne
    val patientOne_Result_one = UrineResult(1,ResultCode.FOUR_PLUS,
        "Cold",Calendar.getInstance().time,patientOne.patientId)
    resultDao.insertResult(patientOne_Result_one)
    val patientOne_result_two = UrineResult(2,ResultCode.THREE_PLUS,
        "Cough",Calendar.getInstance().time,patientOne.patientId)
    resultDao.insertResult(patientOne_result_two)
    val patientOne_result_three = UrineResult(3,ResultCode.TWO_PLUS,
        "Recovered",Calendar.getInstance().time,patientOne.patientId)
    resultDao.insertResult(patientOne_result_three)

    //Three Results of patientTwo
    val patientTwo_Result_one = UrineResult(1,ResultCode.ONE_PLUS,
        "",Calendar.getInstance().time,patientTwo.patientId)
    resultDao.insertResult(patientTwo_Result_one)
    val patientTwo_result_two = UrineResult(2,ResultCode.ONE_PLUS,
        "",Calendar.getInstance().time,patientTwo.patientId)
    resultDao.insertResult(patientTwo_result_two)
    val patientTwo_result_three = UrineResult(3,ResultCode.NEGATIVE,
        "",Calendar.getInstance().time,patientTwo.patientId)
    resultDao.insertResult(patientTwo_result_three)

//insert doctor
    val fullName = FullName("AS ","Vasudev")
    val contact = ContactInfo("8745",null,null)
    val doctor = Doctor(1,fullName,contact)
    doctorDao.insertDoctor(doctor)

    //insert medicine
    val medicine = Medicines(1,"Omnacortil Forte")
    medicineDao.insertMedicine(medicine)

//insert diseases
    val diseases = Diseases(1,"Fever")
    diseasesDao.insertDisease(diseases)

//insert unit and frequency
    val medicineUnit = MedicineUnit(1,"ml")
    medicineUnitDao.insertMedicineUnit(medicineUnit)

    val frequency = Frequency(1,"Once a Day")
    frequencyDao.insertFrequency(frequency)


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
            "9891417738",null
            )


        careTakerDao.insertCareTaker(careTaker.toDatabaseCareTaker())
        val addedCareTaker = careTakerDao.getCareTakerById(careTaker.careTakerId)


        assertThat(addedCareTaker,notNullValue())
        assertEquals(careTaker.careTakerId,addedCareTaker.ctId)
        assertEquals(careTaker.email,addedCareTaker.contact.email)

    }

    @Test
    fun savepatientTwoPatientWithCareTaker_addedToDatabase() = runBlockingTest {
        val careTaker = CareTaker(1, "Vivek Gupta","itgurur4all@gmail.com","",null)
        careTakerDao.insertCareTaker(careTaker.toDatabaseCareTaker())
    val addedCareTaker = careTakerDao.getCareTakerById(careTaker.careTakerId)

        val patientOne = Patient(1,"Atharv Gupta",4,(19.3).toFloat(),"",careTaker.careTakerId)
        val patientTwo = Patient(2,"Utkarsh Gupta",7,(25.6).toFloat(),"",careTaker.careTakerId)

        val patientList = mutableListOf<Patient>()
        patientList.add(patientOne)
        patientList.add(patientTwo)
        patientDao.insertAllPatient(*patientList.toDatabasePatient().toTypedArray())

        val patients = patientDao.getAllPatients()

        //Relation between entities
        val patientsUnderCareTaker: List<CareTakerWithPatients> = careTakerDao.patientsOfCareTaker(careTaker.careTakerId)

        assertNotNull(addedCareTaker)
        assertEquals(careTaker.careTakerId,addedCareTaker.ctId)

        assertNotNull(patients)
        assertEquals(patientOne.patientId,patients.first().patientId)
        assertEquals(patientTwo.patientId, patients[1].patientId)

        //testing relation
        assertEquals(careTaker.careTakerId,
                    patientsUnderCareTaker.first().patients.first().patientCaretakerId
                )

        assertEquals(careTaker.careTakerId,
            patientsUnderCareTaker.first().patients.last().patientCaretakerId
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
        val careTaker = CareTaker(1, "Vivek Gupta","itgurur4all@gmail.com","",null)
        careTakerDao.insertCareTaker(careTaker.toDatabaseCareTaker())
       // val addedCareTaker = careTakerDao.getCareTakerById(careTaker.careTakerId)


        //Two patients
        val patientOne = Patient(1,"Atharv Gupta",4,19.3f,"",careTaker.careTakerId)
        val patientTwo = Patient(2,"Utkarsh Gupta",7,25.6f,"",careTaker.careTakerId)

        val patientList = mutableListOf<Patient>()
        patientList.add(patientOne)
        patientList.add(patientTwo)
        patientDao.insertAllPatient(*patientList.toDatabasePatient().toTypedArray())

      //  val patients = patientDao.getAllPatient()

        //Three Results of patientOne
        val patientOne_Result_one = UrineResult(1,ResultCode.FOUR_PLUS,
            "Cold",Calendar.getInstance().time,patientOne.patientId)
        resultDao.insertResult(patientOne_Result_one)
        val patientOne_result_two = UrineResult(2,ResultCode.THREE_PLUS,
            "Cough",Calendar.getInstance().time,patientOne.patientId)
        resultDao.insertResult(patientOne_result_two)
        val patientOne_result_three = UrineResult(3,ResultCode.TWO_PLUS,
            "Recovered",Calendar.getInstance().time,patientOne.patientId)
        resultDao.insertResult(patientOne_result_three)

        //Three Results of patientTwo
        val patientTwo_Result_one = UrineResult(1,ResultCode.ONE_PLUS,
            "",Calendar.getInstance().time,patientTwo.patientId)
        resultDao.insertResult(patientTwo_Result_one)
        val patientTwo_result_two = UrineResult(2,ResultCode.ONE_PLUS,
            "",Calendar.getInstance().time,patientTwo.patientId)
        resultDao.insertResult(patientTwo_result_two)
        val patientTwo_result_three = UrineResult(3,ResultCode.NEGATIVE,
            "",Calendar.getInstance().time,patientTwo.patientId)
        resultDao.insertResult(patientTwo_result_three)


        val patientWithResults = patientDao.getPatientWithResults(patientOne.patientId)


        val resultsOfPatientOne = resultDao.getAllResultsForPatient(patientOne.patientId)
        val resultsOfPatientTwo = resultDao.getAllResultsForPatient(patientTwo.patientId)

        assertNotNull(resultsOfPatientOne)
        assertNotNull(resultsOfPatientTwo)
      //  assertEquals(ResultCode.ONE_PLUS,resultsOfPatientTwo.first().resultCode)

        assertNotNull(patientWithResults)
        assertEquals(1,
            patientWithResults[0].patient.patientId
            )
        assertNotNull(patientWithResults[0].urineResults)
    }


@Test
fun testDifferentDao_newInsert_addedToDatabase()= runBlockingTest {
    val fullName = FullName("AS ","Vasudev")
    val contact = ContactInfo("",null,null)
    val doctor = Doctor(1,fullName,contact)
    doctorDao.insertDoctor(doctor)
    val insertedDoctor = doctorDao.getDoctorById(doctor.doctorId)

    val medicine = Medicines(1,"Omnacortil Forte")
    medicineDao.insertMedicine(medicine)

    val insertedMedicine = medicineDao.getMedicineById(medicine.medicineCode)

    val diseases = Diseases(1,"Fever")
    diseasesDao.insertDisease(diseases)
    val insertedDiseases = diseasesDao.getDiseaseById(diseases.diseaseCode)

    val medicineUnit = MedicineUnit(1,"ml")
    medicineUnitDao.insertMedicineUnit(medicineUnit)

    val frequency = Frequency(1,"Once a Day")
    frequencyDao.insertFrequency(frequency)

    val insertedUnit = medicineUnitDao.getMedicineUnitIdByName(medicineUnit.unitName)
    val insertedFrequency = frequencyDao.getFrequencyCodeByName(frequency.name)

    assertEquals(1L,insertedDiseases?.diseaseCode)
    assertThat(insertedDoctor?.doctorId,`is`(1))
    assertThat(insertedMedicine?.medicineCode,`is`(1))
    assertEquals(1L,insertedUnit)
    assertEquals(1L,insertedFrequency)
  //  assertThat(1L,`is`(insertedUnit))
  //  assertThat(1L,`is`(insertedFrequency))

}




}


