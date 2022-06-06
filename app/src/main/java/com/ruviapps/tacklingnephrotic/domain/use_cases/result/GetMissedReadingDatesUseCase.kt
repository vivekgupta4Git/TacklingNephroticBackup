package com.ruviapps.tacklingnephrotic.domain.use_cases.result

import android.annotation.SuppressLint
import android.util.Log
import com.ruviapps.tacklingnephrotic.database.dto.QueryResult
import com.ruviapps.tacklingnephrotic.database.dto.onFailure
import com.ruviapps.tacklingnephrotic.database.dto.onSuccess
import com.ruviapps.tacklingnephrotic.repository.ResultLocalRepository
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import java.util.*
import javax.inject.Inject

class GetMissedReadingDatesUseCase @Inject constructor(
    val repository: ResultLocalRepository
) {
    @SuppressLint("NewApi")
    suspend operator fun invoke(patientId : Long): QueryResult<List<LocalDate>> {
        Log.d("MYTAG","inside missed reading")
        var firstDate : LocalDate
        var daysInBetween : Int
        val localDateList = mutableListOf<LocalDate>()
        val query = repository.getResultsByPatientId(patientId)
        query.onSuccess { data, _ ->
            data.forEach {
                Log.d("MYTAG","$it")

            }
            Log.d("MYTAG","Data size : ${data.size}")
            if(data.isNotEmpty()) {
            //as repo retrns rte =eslt in descending order ..latest date to down ..
            firstDate = data[data.size-1].recordedDate
            Log.d("MYTAG","$firstDate")
            daysInBetween = ChronoUnit.DAYS.between(firstDate,LocalDate.now().plusDays(1)).toInt()
            Log.d("MYTAG","$daysInBetween")
            //difference between 14-4-2022 to 1-4-2022 is 13 days so need to add one more
            if((daysInBetween) != data.size) {


              for(i in 1..daysInBetween) {
                  //no need for 0 as it first date
                  val date = LocalDate.now().minusDays((daysInBetween - (i)).toLong())
                  Log.d("MYTAG", "Searcing for  Date = $date")

                  val findDate = data.find {
                      Log.d("MYTAG", "INSIDE FIND ${it.recordedDate}")
                      it.recordedDate.isEqual(date)
                  }

                  if (findDate == null) {
                      Log.d("MYTAG", "Adding to list $date")
                      localDateList.add(date)
                  }

              }
            }else{
                return QueryResult.Error("No missing entries",1)
            }

            }else{
                return QueryResult.Error("No entries present",1)
            }
        }
        query.onFailure { message, _ ->
            return QueryResult.Error(message)
        }
        Log.d("MYTAG","Local Date List = $localDateList")
        return QueryResult.Success(localDateList)
    }

}