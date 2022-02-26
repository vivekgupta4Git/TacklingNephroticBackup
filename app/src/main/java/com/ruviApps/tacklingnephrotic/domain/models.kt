package com.ruviApps.tacklingnephrotic.domain

import java.util.*

data class Patient(
    val patientId : Long,
    val patientName : String,
    val patientAge : Int?,
    val patientWeight : Float?,
    val patientPicUri : String = "",
)
data class CareTaker(
    val careTakerId : Long,
    val careTakerName : String?,
    val email : String?,
    val primaryContact:Int?,
    val secondaryContact:Int?
)
data class Result(
    val resultId : Long,
    val resultCode : String,
    val remarks : String?,
    val recordedDate : Date
)

