package com.ruviapps.tacklingnephrotic.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ruviapps.tacklingnephrotic.database.entities.Doctor
import com.ruviapps.tacklingnephrotic.database.entities.TableName

@Dao
interface DoctorDao {

    @Insert(onConflict =OnConflictStrategy.REPLACE)
    suspend fun insertDoctor(doctor: Doctor)

    @Insert(onConflict =OnConflictStrategy.REPLACE)
    suspend fun insertAllDoctors(vararg doctor: Doctor)

    @Query("Select * from " + TableName.DoctorsTable)
    suspend fun getAllDoctors() : List<Doctor>?

    @Query("Select * from " + TableName.DoctorsTable + " where "
            + Doctor.ColumnDoctorId + " = :id")
    suspend fun getDoctorById(id : Long) : Doctor?
}