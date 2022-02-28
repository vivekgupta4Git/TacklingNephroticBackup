package com.ruviApps.tacklingnephrotic.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.ruviApps.tacklingnephrotic.database.entities.DatabasePatient

@Dao
interface PatientDao  {

    @Query("Select * from DatabasePatient")
    fun getPatientList(): List<DatabasePatient>


}