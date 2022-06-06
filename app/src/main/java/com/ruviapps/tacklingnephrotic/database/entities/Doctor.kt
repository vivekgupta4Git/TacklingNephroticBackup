package com.ruviapps.tacklingnephrotic.database.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = TableName.DoctorsTable)
data class Doctor(
    @PrimaryKey(true)
    @ColumnInfo(ColumnDoctorId)
    val doctorId : Long,
    @Embedded
    val fullName: FullName,
    @Embedded
    val contactInfo: ContactInfo,
    ){
    companion object{
        const val ColumnDoctorId = "doctor_id"
    }
}