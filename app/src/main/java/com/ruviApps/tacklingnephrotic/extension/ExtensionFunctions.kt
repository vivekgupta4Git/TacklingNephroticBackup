package com.ruviApps.tacklingnephrotic.extension

import com.ruviApps.tacklingnephrotic.database.entities.ContactInfo
import com.ruviApps.tacklingnephrotic.database.entities.DatabaseCareTaker
import com.ruviApps.tacklingnephrotic.database.entities.DatabasePatient
import com.ruviApps.tacklingnephrotic.database.entities.FullName
import com.ruviApps.tacklingnephrotic.domain.CareTaker
import com.ruviApps.tacklingnephrotic.domain.Patient

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


//extension function to convert patient domain object to database object
fun Patient.toDatabasePatient() : DatabasePatient{
    val firstName= patientName.substringBefore(" ")
    val lastName = patientName.substringAfterLast(" ")
    val fullName =  FullName(firstName,lastName)          //if first name is null, We throw NPE

    return DatabasePatient(patientId,
        fullName,
        patientAge,
    patientWeight,
    patientPicUri,underCareTakerId)

}

//extension function to convert list of patient domain objects to database object list
fun List<Patient>.toDatabasePatient() : List<DatabasePatient>{

    return map {
        val firstName= it.patientName.substringBefore(" ")
        val lastName = it.patientName.substringAfterLast(" ")
        val fullName =  FullName(firstName,lastName)          //if first name is null, We throw NPE

        DatabasePatient(it.patientId,
            fullName,
            it.patientAge,
            it.patientWeight,
            it.patientPicUri,
            it.underCareTakerId)
    }
}

//extension function to convert database patient object into domain patient object
fun DatabasePatient.toDomainPatient() : Patient{

    val firstName = fullName?.firstName
    val lastName = fullName?.lastName
    val patientName = "$firstName $lastName"

    return Patient(
        patientId,
        patientName,
        age,
        weight,
        snapUri,
        patientCaretakerId
    )

}

//extension function to convert List of Patient Database Objects to list of domain Patient Objects
fun List<DatabasePatient>.toDomainPatient() : List<Patient>{
    return map{
        val firstName = it.fullName?.firstName
        val lastName = it.fullName?.lastName
        val patientName = "$firstName $lastName"

        Patient(
            it.patientId,
            patientName,
            it.age,
            it.weight,
            it.snapUri,
            it.patientCaretakerId
        )
    }
}