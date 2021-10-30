package com.deitel.redtesttask1_dollarcoursechecker.data

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.room.withTransaction
import com.deitel.redtesttask1_dollarcoursechecker.DateProvider.getCurrentDateTime
import com.deitel.redtesttask1_dollarcoursechecker.DateProvider.getDateDaysAgo
import com.deitel.redtesttask1_dollarcoursechecker.DateProvider.toString
import com.deitel.redtesttask1_dollarcoursechecker.api.BankService
import com.deitel.redtesttask1_dollarcoursechecker.db.DollarCourseDatabase
import com.deitel.redtesttask1_dollarcoursechecker.model.Record

import javax.inject.Inject

/**
 * PagingSource to support RecyclerView Pagination
 */
class BankPagingSource @Inject constructor (private val service: BankService, private val dollarCourseDatabase : DollarCourseDatabase? = null) : PagingSource<Int, Record>() {


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Record> {
        try {
            val date = getCurrentDateTime()
            val dateInString = date.toString("dd/MM/yyyy")

            val dateAgo = getDateDaysAgo(30)
            val dateAgoString = dateAgo.toString("dd/MM/yyyy")

            var response = service.searchDollarCourse(data_req1 = dateAgoString, data_req2 = dateInString)
            var myMutableList = response.records

            val position = params.key ?: 0
            Log.d("PAGING SOURCE LOADING", "position: " + position)

            dollarCourseDatabase?.withTransaction {
                dollarCourseDatabase?.recordsDao().clearRecords()
                dollarCourseDatabase?.recordsDao().insertAll(myMutableList)
            }

            return LoadResult.Page(
                data = myMutableList,
                prevKey = if (position == 0) null else position - 1,
                nextKey = null
            )
        } catch (e: Exception) {
            e.message?.let { Log.d("PAGING SOURCE ERROR:", it) }
            return LoadResult.Error(e)
        }
    }
    override fun getRefreshKey(state: PagingState<Int, Record>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}