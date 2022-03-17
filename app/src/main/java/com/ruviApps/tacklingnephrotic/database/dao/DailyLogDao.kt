package com.ruviApps.tacklingnephrotic.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ruviApps.tacklingnephrotic.database.entities.DailyLog
import com.ruviApps.tacklingnephrotic.database.entities.Doctor
import com.ruviApps.tacklingnephrotic.database.entities.TableName

@Dao
interface DailyLogDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLog(dailyLog: DailyLog)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllLogs(vararg dailyLog: DailyLog)

    @Query("Select * from ${TableName.DailyLogTable}")
    suspend fun getAllLogs() : List<DailyLog>

    @Query("Select * from ${TableName.DailyLogTable} where ${DailyLog.ColumnPatientId} = :id ORDER BY " +
            " ${DailyLog.ColumnRecordedDate} DESC")
    suspend fun getLogsForPatient(id: Long) : List<DailyLog>


}