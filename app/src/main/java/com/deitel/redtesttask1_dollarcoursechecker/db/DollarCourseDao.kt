package com.deitel.redtesttask1_dollarcoursechecker.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.deitel.redtesttask1_dollarcoursechecker.model.Record

/**
 * DataAccessObject to make queries for database
 */
@Dao
interface DollarCourseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(records: List<Record>)

    @Query(
        "SELECT * FROM dollar_course_records"
    )
    fun recordsByName(): PagingSource<Int, Record>

    @Query("SELECT * FROM dollar_course_records WHERE id=(SELECT max(id) FROM dollar_course_records)")
    fun getLastRecord(): Record

    @Query("DELETE FROM dollar_course_records")
    suspend fun clearRecords()
}