package com.deitel.redtesttask1_dollarcoursechecker.data

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.deitel.redtesttask1_dollarcoursechecker.Constants
import com.deitel.redtesttask1_dollarcoursechecker.api.BankService
import com.deitel.redtesttask1_dollarcoursechecker.db.DollarCourseDatabase
import com.deitel.redtesttask1_dollarcoursechecker.model.Record
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Main repository to get data from BankService or DollarCourseDatabase
 */
class BankRepository @Inject constructor (private val service: BankService) {

    lateinit var dollarCourseDatabase : DollarCourseDatabase

    fun getSearchResultStream(context: Context): Flow<PagingData<Record>> {

        dollarCourseDatabase = DollarCourseDatabase.getInstance(context)
        val pagingSourceFactory = { dollarCourseDatabase.recordsDao().recordsByName() }


        val connectivityManager =
            ContextCompat.getSystemService(context, ConnectivityManager::class.java)
        val activeNetwork: NetworkInfo? = connectivityManager?.activeNetworkInfo
        val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true


        if(isConnected) {
            //Log.d("CONNECTION IS: ", "TRUE!")
            return Pager(
                config = PagingConfig(
                    enablePlaceholders = false,
                    pageSize = Constants.NETWORK_PAGE_SIZE
                ),
                pagingSourceFactory = { BankPagingSource(service, dollarCourseDatabase) }
            ).flow
        } else {
            //Log.d("CONNECTION IS: ", "FALSE!")
            Toast.makeText(
                context,
                "Похоже, нет подключения к Интернету. Показаны последние сохраненные данные",
                Toast.LENGTH_LONG
            ).show()
            return Pager(
                config = PagingConfig(
                    enablePlaceholders = false,
                    pageSize = Constants.NETWORK_PAGE_SIZE
                ),
                pagingSourceFactory = pagingSourceFactory
            ).flow
        }
    }

}