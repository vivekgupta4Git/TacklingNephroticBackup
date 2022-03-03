package com.ruviApps.tacklingnephrotic.database.datasources

import com.ruviApps.tacklingnephrotic.database.dto.QueryResult
import com.ruviApps.tacklingnephrotic.database.entities.DatabaseCareTaker

interface CareTakerDataSource {

    suspend fun getCareTakers() : QueryResult<List<DatabaseCareTaker>>
    suspend fun getCareTaker(id : Long) : QueryResult<DatabaseCareTaker>
    suspend fun saveCareTaker(careTaker: DatabaseCareTaker) : QueryResult<Unit>
    suspend fun saveAllCareTakers(caretakers : List<DatabaseCareTaker>) : QueryResult<Unit>
    suspend fun deleteAllCareTakers() : QueryResult<Unit>

}