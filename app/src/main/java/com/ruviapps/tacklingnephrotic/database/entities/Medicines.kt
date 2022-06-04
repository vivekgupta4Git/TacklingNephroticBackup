package com.ruviapps.tacklingnephrotic.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = TableName.MedicinesTable)
data class Medicines(
    @PrimaryKey(true)
    @ColumnInfo(ColumnMedicineCode)
    val medicineCode : Long,
    @ColumnInfo(ColumnMedicineName)
    val medicineName : String,

){
    companion object{
        const val ColumnMedicineCode = "medicine_code"
        const val ColumnMedicineName = "medicine_name"
    }
}