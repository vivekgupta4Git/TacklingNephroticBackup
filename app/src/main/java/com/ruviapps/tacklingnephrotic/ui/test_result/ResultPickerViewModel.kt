package com.ruviapps.tacklingnephrotic.ui.test_result

import android.util.Log
import androidx.compose.ui.text.intl.Locale
import androidx.core.text.toSpannable
import androidx.lifecycle.*
import com.ruviapps.tacklingnephrotic.R
import com.ruviapps.tacklingnephrotic.converters.DateConverter
import com.ruviapps.tacklingnephrotic.database.dto.onFailure
import com.ruviapps.tacklingnephrotic.database.dto.onSuccess
import com.ruviapps.tacklingnephrotic.database.entities.*
import com.ruviapps.tacklingnephrotic.domain.CareTaker
import com.ruviapps.tacklingnephrotic.domain.Patient
import com.ruviapps.tacklingnephrotic.domain.TestResult
import com.ruviapps.tacklingnephrotic.domain.use_cases.caretaker.CareTakerUseCases
import com.ruviapps.tacklingnephrotic.domain.use_cases.patient.PatientUseCases
import com.ruviapps.tacklingnephrotic.domain.use_cases.result.UrineResultUseCases
import com.ruviapps.tacklingnephrotic.ui.home.HomeFragmentArgs
import com.ruviapps.tacklingnephrotic.utility.Event
import com.ruviapps.tacklingnephrotic.utility.NavigationCommand
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.text.DateFormat
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
        Log.d(TAG,"ViewModel initialized")
    }

   /* private val eventChannel = Channel<NavigationCommand>(Channel.BUFFERED)
    val eventsFlow = eventChannel.receiveAsFlow()
*/
    fun saveResult(result: TestResult) {
        viewModelScope.launch {
           val query = resultUseCases.insertUrineResultUseCase(result)
            query.onSuccess { _, _ ->
                val action =   ResultPickerFragmentDirections.actionNavResultToNavHome(result.resultCode)
        //        eventChannel.send(NavigationCommand.ToDirection(action))
              _navigateToDashBoard.value = Event(NavigationCommand.ToDirection(action))
            }
            query.onFailure { message, code ->
                Log.d(TAG,"Insert Error : $message + Code : $code")
                _navigateToDashBoard.value = Event(NavigationCommand.ShowError("One entry Per Day is allowed"))
            }
        }
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






