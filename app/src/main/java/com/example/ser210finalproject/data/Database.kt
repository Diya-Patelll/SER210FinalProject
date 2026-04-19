package com.example.ser210finalproject.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// Room Database for app
// defines entities and provides access to DAOs
@Database(
    entities = [Student::class, Listing::class, Purchase::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun studentDao(): StudentDAO
    abstract fun listingDao(): ListingDAO
    abstract fun purchaseDao(): PurchaseDAO

    companion object {
        @Volatile
        private var Instance: AppDatabase? = null

        // ensures only one instance of database exists
        fun getDatabase(context: Context): AppDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, AppDatabase::class.java, "ser210_database")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}