package com.ruviApps.tacklingnephrotic.converters

import androidx.room.TypeConverter
import com.ruviApps.tacklingnephrotic.models.ResultCode
import java.util.*

class ResultConverter{

    /**
     * convert Result code to an integer value
     */
    @TypeConverter
    fun fromResultCode(value : ResultCode) = value.value

    /**
     * Convert an Integer to Result Code
     */
    @TypeConverter
    fun toResultCode(value : Int): ResultCode {
        return (ResultCode.values()[value])
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
