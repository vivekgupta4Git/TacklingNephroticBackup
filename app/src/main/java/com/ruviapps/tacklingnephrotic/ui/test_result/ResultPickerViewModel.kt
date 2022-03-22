package com.ruviapps.tacklingnephrotic.ui.test_result

import android.util.Log
import androidx.core.text.toSpannable
import androidx.lifecycle.*
import com.ruviapps.tacklingnephrotic.R
import com.ruviapps.tacklingnephrotic.database.dto.onFailure
import com.ruviapps.tacklingnephrotic.database.dto.onSuccess
import com.ruviapps.tacklingnephrotic.database.entities.*
import com.ruviapps.tacklingnephrotic.domain.CareTaker
import com.ruviapps.tacklingnephrotic.domain.Patient
import com.ruviapps.tacklingnephrotic.domain.TestResult
import com.ruviapps.tacklingnephrotic.domain.use_cases.caretaker.CareTakerUseCases
import com.ruviapps.tacklingnephrotic.domain.use_cases.patient.PatientUseCases
import com.ruviapps.tacklingnephrotic.domain.use_cases.result.UrineResultUseCases
import com.ruviapps.tacklingnephrotic.utility.Event
import com.ruviapps.tacklingnephrotic.utility.NavigationCommand
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


private const val TAG = "myTag"

@HiltViewModel
class ResultPickerViewModel
    @Inject constructor
    (
    val resultUseCases: UrineResultUseCases
) : ViewModel() {

    private val _navigateToDashBoard = MutableLiveData<Event<NavigationCommand>>()

    val navigateToDashBoard : LiveData<Event<NavigationCommand>>
        get() = _navigateToDashBoard


    @Inject
    lateinit var caretakerUseCases: CareTakerUseCases
    @Inject
    lateinit var patientUseCaess : PatientUseCases
    init {
        Log.d(TAG,"ViewModel initiated")
    }
    private var _pickedResult = MutableLiveData<String>()
    val pickedResult: LiveData<String> = _pickedResult

    fun setPickedResult(result: String) {
        val resultCode = ResultCode.valueOf(result)
        Log.i(TAG, "Value of Result code = $resultCode")
        _pickedResult.value = resultCode.name
    }

     fun saveResult(result: TestResult) {
        viewModelScope.launch {
           val query = resultUseCases.insertUrineResultUseCase(result)
            query.onSuccess { data, message ->
                Log.d("myTag","Success! $data  with $message")
                _navigateToDashBoard.value = Event(NavigationCommand.To(R.id.action_nav_result_to_nav_home))
            }
            query.onFailure { message, statusCode ->
                _navigateToDashBoard.value = Event(NavigationCommand.ShowError(message))
            }

        }
    }

    fun setLog(){
        Log.d("myTag","ViewMOdel ")
    }

    fun initializeDatabase() {
        viewModelScope.launch {
            caretakerUseCases.registerCareTakerUseCase("Vivek Gupta",
                "itguru4all@gmail.com",
                "9891417738",
                null)
            patientUseCaess.addPatientUseCase("Atharv Gupta",4,19.2f,
            "",1)
        }
    }




}



/*
class ResultPickerModelFactory(private val resultUseCases: UrineResultUseCases) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
     if(modelClass.isAssignableFrom(ResultPickerViewModel::class.java)) {
         return ResultPickerViewModel(resultUseCases) as T
     }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}
*/


