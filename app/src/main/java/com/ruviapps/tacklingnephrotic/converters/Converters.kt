package com.ruviapps.tacklingnephrotic.converters

import android.annotation.SuppressLint
import androidx.room.TypeConverter
import com.ruviapps.tacklingnephrotic.database.entities.DiseasesState
import com.ruviapps.tacklingnephrotic.database.entities.ResultCode
import com.ruviapps.tacklingnephrotic.domain.NephroticState
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

/**
 *  Room 2.3.0 added automatic conversion of an enum class value to and from its String representation.
 *  So, now you can use an enum without a @TypeConverter pair
 */

class ResultConverter{

    /**
     * convert Result code to String value
     */
    @TypeConverter
    fun fromResultCode(value : ResultCode) = value.name

    /**
     * Convert String to Result Code
     */
    @TypeConverter
    fun toResultCode(value : String): ResultCode {
        return (ResultCode.valueOf(value))
    }
}

class DateConverter {

    /**
     * Convert from timestamp(Long) to Date
     */
    @SuppressLint("NewApi")
    @TypeConverter
    fun fromTimestamp(value: Long): LocalDate {
        return LocalDate.ofEpochDay(value)
    }

    /**
     * Convert Date to timestamp (Long)
     */
    @SuppressLint("NewApi")
    @TypeConverter
    fun dateToTimestamp(date: LocalDate): Long {
        return date.toEpochDay()
    }
}

class DateConverter2 {
    /**
     * Convert from timestamp(Long) to Date
     */
    @TypeConverter
    fun fromTimestamp(value: Long): Date {
        return Date(value)
    }

    /**
     * Convert Date to timestamp (Long)
     */
    @TypeConverter
    fun dateToTimestamp(date: Date): Long {
        return date.time
    }
}

class DiseaseStateConverter{
    /**
     * convert Disease State to String value
     */
    @TypeConverter
    fun fromDiseaseState(value : DiseasesState) :String = value.name

    /**
     * Convert a String to Disease State
     */
    @TypeConverter
    fun toDiseaseState(value : String): DiseasesState {
        return (DiseasesState.valueOf(value))
    }

}
/*
class HeathStatusConverter{
    */
/**
     * convert health Status to String value
     *//*

    @TypeConverter
    fun fromHealthStatus(value : HealthStatus) = value.name

    */
/**
     * Convert a String to Health Status
     *//*

    @TypeConverter
    fun toHealthStatus(value : String): HealthStatus {
        return (HealthStatus.valueOf(value))
    }
}

*/
class NephroticStateConverter{

/**
     * convert nephrotic state to String value
     */

    @TypeConverter
    fun fromNephroticState(value : NephroticState) = value.name


/**
     * Convert a String to Nephrotic State
     */

    @TypeConverter
    fun toNephroticState(value : String): NephroticState {
        return (NephroticState.valueOf(value))
    }
}
