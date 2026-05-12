package de.multiplebytes.votemaster.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import de.multiplebytes.votemaster.domain.model.Vote

@Database(entities = [Vote::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun voteDao(): VoteDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context, AppDatabase::class.java, "app_database"
                ).build().also { instance = it }
            }
        }
    }
}
