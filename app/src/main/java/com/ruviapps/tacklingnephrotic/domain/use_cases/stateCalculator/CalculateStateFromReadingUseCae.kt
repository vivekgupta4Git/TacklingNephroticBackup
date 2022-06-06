
package com.ruviapps.tacklingnephrotic.domain.use_cases.stateCalculator


import android.annotation.SuppressLint
import android.os.Build
import com.ruviapps.tacklingnephrotic.database.dto.onFailure
import com.ruviapps.tacklingnephrotic.database.dto.onSuccess
import com.ruviapps.tacklingnephrotic.database.entities.UrineResult
import com.ruviapps.tacklingnephrotic.domain.NephroticState
import com.ruviapps.tacklingnephrotic.repository.ResultLocalRepository
import java.time.LocalDate
import javax.inject.Inject


class CalculateStateFromReadingUseCase @Inject constructor(val  repo : ResultLocalRepository){
   @SuppressLint("NewApi")
   suspend  operator fun invoke(
        patientId : Long,
        onDate : LocalDate
    ): NephroticState {
       var readingList = listOf<UrineResult>()
       val twoDayBack = onDate.minusDays(2)
       val q = repo.getReadingByDate(patientId,twoDayBack,onDate)
       q.onSuccess { data, message ->
            readingList = data
       }
       q.onFailure { message, statusCode ->
           return NephroticState.REMISSION
       }

       if(readingList.size ==3){
         return  calculateState(readingList[0].resultCode.value,readingList[1].resultCode.value,readingList[2].resultCode.value)
       }else
       {
           return NephroticState.REMISSION
       }
   }

    private fun calculateState(tdr: Int, ydr: Int, adbyr: Int): NephroticState {
        var _state = NephroticState.REMISSION

        when(tdr) {
            in -1..1 -> {
                when (ydr) {
                    in -1..1 -> {
                        when (adbyr) {
                            in -1..1 ->
                                _state = NephroticState.REMISSION
                            in 2..4 ->
                                _state = NephroticState.OBSERVATION
                        }

                    }
                    in 2..4 -> {
                        _state = NephroticState.OBSERVATION
                    }

                }

            }
            in 2..4 -> {

                when (ydr) {
                    in -1..1 -> {
                        _state = NephroticState.OBSERVATION
// no need
//                        when (adbyr) {
//                            in -1..1 ->
//                                _state = NephroticState.OBSERVATION
//                            in 2..4 ->
//                                _state = NephroticState.OBSERVATION
//                        }

                    }
                    in 2..4 -> {
                        when (adbyr) {
                            in -1..1 ->
                                _state = NephroticState.OBSERVATION
                            in 2..4 ->
                                _state = NephroticState.RELAPSE
                        }

                    }

                }


            }
        }
        
        return _state
    }

}
