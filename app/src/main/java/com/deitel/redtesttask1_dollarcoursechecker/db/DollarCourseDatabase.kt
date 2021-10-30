package com.deitel.redtesttask1_dollarcoursechecker.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.deitel.redtesttask1_dollarcoursechecker.model.Record

/**
 * Main database to save the last downloaded DollarCourse data, if InternetConnection is lost
 */
@Database(
    entities = [Record::class],
    version = 1,
    exportSchema = false
)
abstract class DollarCourseDatabase : RoomDatabase() {

    abstract fun recordsDao(): DollarCourseDao

    companion object {
        @Volatile
        private var sINSTANCE: DollarCourseDatabase? = null

        fun getInstance(context: Context): DollarCourseDatabase =
            sINSTANCE ?: synchronized(this) {
                sINSTANCE
                    ?: buildDatabase(context).also { sINSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                DollarCourseDatabase::class.java, "DollarCourseDB.db"
            )
                .build()
    }
}