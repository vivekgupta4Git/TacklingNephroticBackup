package com.ruviApps.tacklingnephrotic.repository

import com.ruviApps.tacklingnephrotic.database.dao.CareTakerDao
import com.ruviApps.tacklingnephrotic.database.datasources.CareTakerDataSource
import com.ruviApps.tacklingnephrotic.database.dto.QueryResult
import com.ruviApps.tacklingnephrotic.database.entities.DatabaseCareTaker
import kotlinx.coroutines.*
import java.lang.Exception

class CareTakerLocalRepository(
    private val careTakerDao: CareTakerDao,
    private val ioDispatcher : CoroutineDispatcher
) : CareTakerDataSource{

    override suspend fun getCareTakers(): QueryResult<List<DatabaseCareTaker>> = withContext(ioDispatcher){
        return@withContext try { QueryResult.Success(careTakerDao.getAllCareTakers())
        }catch (ex : Exception)
        {  QueryResult.Error(ex.localizedMessage) }
    }

    override suspend fun getCareTaker(id :Long) : QueryResult<DatabaseCareTaker> = withContext(ioDispatcher){
      return@withContext try { QueryResult.Success(careTakerDao.getCareTakerById(id))
      }catch (ex : Exception)
      {  QueryResult.Error(ex.localizedMessage) }
  }

    override suspend fun saveCareTaker(careTaker: DatabaseCareTaker) =
        withContext(ioDispatcher){
            return@withContext try {
                QueryResult.Success(careTakerDao.insertCareTaker(careTaker))
            }catch (ex : Exception)
            {
                QueryResult.Error(ex.localizedMessage)
            }
        }


    override suspend fun deleteAllCareTakers() : QueryResult<Unit> =
        withContext(ioDispatcher) {
          return@withContext  try {
            QueryResult.Success(careTakerDao.deleteAllCareTaker())
            }catch (ex : Exception){
                QueryResult.Error(ex.localizedMessage)
            }
        }


    override suspend fun saveAllCareTakers(caretakers: List<DatabaseCareTaker>)
    =withContext(ioDispatcher){
        return@withContext try {
            QueryResult.Success(careTakerDao.insertAllCareTakers(*caretakers.toTypedArray()))
        }catch (ex : Exception)
        {
            QueryResult.Error(ex.localizedMessage)
        }
    }


}