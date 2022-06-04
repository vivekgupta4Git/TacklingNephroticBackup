package com.ruviapps.tacklingnephrotic.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = TableName.DiseasesTable)
data class Diseases(
    @PrimaryKey(true)
    @ColumnInfo(ColumnDiseaseCode)
    val diseaseCode : Long,
    @ColumnInfo(ColumnDiseaseName)
    val diseaseName : String,
){
    companion object{
        const val ColumnDiseaseCode = "diseases_code"
        const val ColumnDiseaseName = "disease_name"
    }
}