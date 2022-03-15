package com.ruviApps.tacklingnephrotic.converters

import androidx.room.TypeConverter
import com.ruviApps.tacklingnephrotic.database.entities.DiseasesState
import com.ruviApps.tacklingnephrotic.database.entities.HealthStatus
import com.ruviApps.tacklingnephrotic.database.entities.NephroticState
import com.ruviApps.tacklingnephrotic.database.entities.ResultCode
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
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    /**
     * Convert Date to timestamp (Long)
     */
    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
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
class HeathStatusConverter{
    /**
     * convert health Status to String value
     */
    @TypeConverter
    fun fromHealthStatus(value : HealthStatus) = value.name

    /**
     * Convert a String to Health Status
     */
    @TypeConverter
    fun toHealthStatus(value : String): HealthStatus {
        return (HealthStatus.valueOf(value))
    }
}
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