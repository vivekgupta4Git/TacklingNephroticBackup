package com.ruviApps.tacklingnephrotic.domain.use_cases.caretaker

import com.ruviApps.tacklingnephrotic.database.dto.QueryResult
import com.ruviApps.tacklingnephrotic.domain.CareTaker
import com.ruviApps.tacklingnephrotic.extension.toDatabaseCareTaker
import com.ruviApps.tacklingnephrotic.repository.CareTakerLocalRepository
import java.util.regex.Pattern


class RegisterCareTakerUseCase(
    private val repository: CareTakerLocalRepository,
) {

    suspend operator fun invoke(
        name: String,
        email: String = "",
        primaryContact: String,
        secondaryContact: String?,

        ): QueryResult<Long> {

        if(name.isBlank()||name.isEmpty())
            return QueryResult.Error("Name is not Valid", VALIDATION_ERROR)

        if(!isValidMail(email))
            return QueryResult.Error("Email is not Valid", VALIDATION_ERROR)

        if(! isValidMobile(primaryContact)  )
            return QueryResult.Error("Contact Number is not valid", VALIDATION_ERROR)

        if(!secondaryContact.isNullOrEmpty()){
            if(!isValidMobile(secondaryContact))
            {
                return QueryResult.Error("Contact is not valid", VALIDATION_ERROR)
            }
        }

        val  careTaker = CareTaker(0,name,email,primaryContact,secondaryContact)

       return repository.saveCareTaker(careTaker.toDatabaseCareTaker())
    }

    companion object{
        const val VALIDATION_ERROR=101
      }

    private fun isValidMobile(phone :String) : Boolean {
       return android.util.Patterns.PHONE.matcher(phone).matches()
     }


    private fun isValidMail(email: String): Boolean {
      return  android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}