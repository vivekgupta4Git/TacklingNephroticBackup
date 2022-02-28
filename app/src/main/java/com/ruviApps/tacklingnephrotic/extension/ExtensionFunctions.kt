package com.ruviApps.tacklingnephrotic.extension

import com.ruviApps.tacklingnephrotic.database.entities.ContactInfo
import com.ruviApps.tacklingnephrotic.database.entities.DatabaseCareTaker
import com.ruviApps.tacklingnephrotic.database.entities.FullName
import com.ruviApps.tacklingnephrotic.domain.CareTaker

//extension function on List of DatabaseCareTaker(Database Object) to convert it into List of CareTaker(Domain Object)
fun List<DatabaseCareTaker>.toDomainCareTaker() : List<CareTaker>{
    return map{
        CareTaker(it.ctId,
            it.fullName.firstName + " " + it.fullName.lastName,
            it.contact.email,
            it.contact.primaryContact,
            it.contact.secondaryContact)
    }
}

//extension function to convert Database object to Domain Object
fun DatabaseCareTaker.toDomainCareTaker() : CareTaker {
    return CareTaker(ctId,
        fullName.firstName+" "+ fullName.lastName,
        contact.email,
        contact.primaryContact,
        contact.secondaryContact)
}


//extension function to convert list of  domain object to  list of database object
fun List<CareTaker>.toDatabaseCareTaker() : List<DatabaseCareTaker>{
    return map{
        val firstName= it.careTakerName?.substringBefore(" ") ?: "Default"
        val lastName = it.careTakerName?.substringAfterLast(" ") ?: ""
        val fullName =  FullName(firstName,lastName)

        val email = it.email ?: ""
        val primaryContact = it.primaryContact ?: 0
        val secondaryContact = it.secondaryContact ?: 0
        val contact = ContactInfo(primaryContact,secondaryContact,email)
        DatabaseCareTaker(it.careTakerId,fullName,contact)
    }
}

//extension function to convert   domain object to database object
fun CareTaker.toDatabaseCareTaker() : DatabaseCareTaker{
    val firstName= careTakerName?.substringBefore(" ") ?: careTakerName
    val lastName = careTakerName?.substringAfterLast(" ") ?: ""
    val fullName =  FullName(firstName!!,lastName)          //if first name is null, We throw NPE

    val primaryContact = primaryContact ?: 0
    val secondaryContact = secondaryContact ?: 0
    val contact = ContactInfo(primaryContact,secondaryContact,email)

    return DatabaseCareTaker(careTakerId,fullName,contact)
}
