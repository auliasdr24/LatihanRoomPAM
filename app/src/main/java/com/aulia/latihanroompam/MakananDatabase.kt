package com.aulia.latihanroompam

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Makanan::class], version = 1, exportSchema = false)
abstract class MakananDatabase : RoomDatabase() {

    abstract fun makananDao(): MakananDao

    companion object {
        @Volatile
        private var INSTANCE: MakananDatabase? = null

        fun getDatabase(context: Context): MakananDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MakananDatabase::class.java,
                    "makanan_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}