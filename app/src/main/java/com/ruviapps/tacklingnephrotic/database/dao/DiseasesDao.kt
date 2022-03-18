package com.ruviapps.tacklingnephrotic.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ruviapps.tacklingnephrotic.database.entities.Diseases
import com.ruviapps.tacklingnephrotic.database.entities.TableName


@Dao
interface DiseasesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDisease(disease : Diseases)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllDiseases(vararg disease : Diseases)

    @Query("Select * from ${TableName.DiseasesTable}")
    suspend fun getAllDiseases() : List<Diseases>?

    @Query("Select * from ${TableName.DiseasesTable} where ${Diseases.ColumnDiseaseCode} = :id")
    suspend fun getDiseaseById(id :Long) : Diseases?
}