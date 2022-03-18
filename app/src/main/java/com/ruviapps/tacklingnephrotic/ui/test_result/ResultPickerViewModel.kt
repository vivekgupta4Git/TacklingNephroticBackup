package com.ruviapps.tacklingnephrotic.ui.test_result

import android.util.Log
import androidx.lifecycle.*
import com.ruviapps.tacklingnephrotic.database.entities.*
import com.ruviapps.tacklingnephrotic.domain.TestResult
import com.ruviapps.tacklingnephrotic.domain.use_cases.result.UrineResultUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


private const val TAG = "myTag"

@HiltViewModel
class ResultPickerViewModel
    @Inject constructor
    (
    val resultUseCases: UrineResultUseCases
) : ViewModel() {


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

    suspend fun saveResult(result: TestResult) {
        resultUseCases.insertUrineResultUseCase(result)
    }

    fun setLog(){
        Log.d("myTag","ViewMOdel ")
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


