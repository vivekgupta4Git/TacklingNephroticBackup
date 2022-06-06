package com.ruviapps.tacklingnephrotic.database.dao

import androidx.room.*
import com.ruviapps.tacklingnephrotic.database.entities.TableName
import com.ruviapps.tacklingnephrotic.database.entities.UrineResult
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import java.util.*

@Dao
interface ResultDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertResult(urineResult: UrineResult)

    @Delete
    suspend fun removeResult(result: UrineResult)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateResult(result: UrineResult) : Int

    @Query("DELETE FROM ${TableName.UrineResultTable} ")
    suspend fun deleteAllResults()

    @Query("SELECT * FROM ${TableName.UrineResultTable} where ${UrineResult.ColumnRecordedDate} = :id ")
    suspend fun getReadingById(id : LocalDate) : UrineResult

   @Query("SELECT * FROM ${TableName.UrineResultTable} where ${UrineResult.ColumnPatientId} = :id" +
            " And ${UrineResult.ColumnRecordedDate} BETWEEN :fromDate And :upToDate ORDER BY ${UrineResult.ColumnRecordedDate} DESC")
    suspend fun getReadingByDate(id : Long, fromDate : LocalDate,upToDate : LocalDate ) : List<UrineResult>

    /**
     * This method may decrease performance
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllResult(vararg urineResult: UrineResult)


    @Query("Select * from ${TableName.UrineResultTable} where ${UrineResult.ColumnPatientId} = :id" +
            " Order By ${UrineResult.ColumnRecordedDate} DESC")
    suspend fun getAllResultsForPatient(id : Long) : List<UrineResult>


    @Query("Select * from " + TableName.UrineResultTable)
    suspend fun getResultsForAllPatients() : List<UrineResult>

    /**
     * @param [id] this is the id of type Long of the patient present in the database
     * @return      Retrieving data in Flow as a List of UrineResult data
     */
    @Query("Select * from " + TableName.UrineResultTable + " where "
            + UrineResult.ColumnPatientId + "= :id ORDER BY " + UrineResult.ColumnRecordedDate + "  DESC")
    fun getResultsForPatientUsingFlow(id : Long) : Flow<List<UrineResult>>

}