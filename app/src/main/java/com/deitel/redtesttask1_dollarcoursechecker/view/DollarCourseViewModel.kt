package com.deitel.redtesttask1_dollarcoursechecker.view

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.deitel.redtesttask1_dollarcoursechecker.data.BankRepository
import com.deitel.redtesttask1_dollarcoursechecker.model.Record
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * ViewModel for the MainActivity
 */
class DollarCourseViewModel @Inject constructor (private val repository: BankRepository, private val context: Context) : ViewModel() {

    private var currentSearchResult: Flow<PagingData<Record>>? = null


    fun searchData(): Flow<PagingData<Record>> {
        val newResult: Flow<PagingData<Record>> =
            repository.getSearchResultStream(context).cachedIn(viewModelScope)
        currentSearchResult = newResult
        return newResult
    }

}