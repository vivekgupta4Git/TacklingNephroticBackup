package com.ruviapps.tacklingnephrotic.domain

import java.util.*

data class Patient(
    val patientId : Long,
    val patientName : String,
    val patientAge : Int?,
    val patientWeight : Float?,
    val patientPicUri : String = "",
    val underCareTakerId: Long
)
data class CareTaker(
    val careTakerId : Long,
    val careTakerName : String?,
    val email : String?,
    val primaryContact:String,
    val secondaryContact:String?
)



data class TestResult(
    val resultId : Long,
    var resultCode : String,
    val remarks : String?,
    val recordedDate : Date,
    val patientId : Long
)


data class Relapse(
    val relapseId : Long,
    val patientId : Long,
    val startDate : Date,
    val endDate : Date,
)

data class Consultation(
    val consultId : Long,
    val patientId : Long,
    val visitDate : Date,
    val consultedDoctorId : Long
)
