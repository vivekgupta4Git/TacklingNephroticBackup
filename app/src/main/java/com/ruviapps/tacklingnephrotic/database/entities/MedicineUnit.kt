package com.ruviapps.tacklingnephrotic.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = TableName.MedicineUnitTable)
data class MedicineUnit(
    @PrimaryKey(true)
    @ColumnInfo(ColumnUnitId)
    val unitID : Long,
    @ColumnInfo(ColumnUnitName)
    val unitName : String
){
    companion object{
        const val ColumnUnitId = "unit_id"
        const val ColumnUnitName = "unit_name"
    }
}