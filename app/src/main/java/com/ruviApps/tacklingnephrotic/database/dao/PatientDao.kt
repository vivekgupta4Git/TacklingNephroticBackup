package com.ruviApps.tacklingnephrotic.database.dao

import androidx.room.*
import com.ruviApps.tacklingnephrotic.database.entities.DatabasePatient
import com.ruviApps.tacklingnephrotic.database.entities.PatientWithUrineResults
import com.ruviApps.tacklingnephrotic.database.entities.TableName

@Dao
interface PatientDao {

    @Insert(onConflict =OnConflictStrategy.REPLACE)
    suspend fun insertPatient(patient : DatabasePatient)


    /**
     * Insert List of patient into database. Don't use this method if device has low resources
     *Reason : we used vararg here as a parameter here so while using this method we will be
     * using Kotlin's spread operator, which causes a full copy of the array to be created before calling a method, has
     * a very high performance penalty (and that might increase with the size of the array).
     * The Java version of vararg runs 200% faster than the seemingly equivalent Kotlin version .
     * more on given link : https://sites.google.com/a/athaydes.com/renato-athaydes/posts/kotlinshiddencosts-benchmarks
     */
    @Insert(onConflict =OnConflictStrategy.REPLACE)
    suspend fun insertAllPatient(vararg patient : DatabasePatient)

    @Query("Select * from  ${TableName.PatientTable}")
    suspend fun getAllPatients() : List<DatabasePatient>

    @Query("Select * from ${TableName.PatientTable} where ${DatabasePatient.ColumnPatientId} = :id")
    suspend fun getPatientById(id : Long) : DatabasePatient

    @Query("DELETE from ${TableName.PatientTable}")
    suspend fun deleteAllPatients()

    @Transaction
    @Query("Select * from ${TableName.PatientTable} where ${DatabasePatient.ColumnPatientId} = :id")
    suspend fun getPatientWithResults(id : Long) :  List<PatientWithUrineResults>

}