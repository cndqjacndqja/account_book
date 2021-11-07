package com.example.accounting.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Buget::class], version = 1)
abstract class BugetDb : RoomDatabase() {
    abstract fun bugetDao() : BugetDao

    companion object{
        private var INSTANCE: BugetDb? = null

        fun getInstance(context : Context): BugetDb?{
            if(INSTANCE == null){
                synchronized(BugetDb::class){
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        BugetDb::class.java,"buget.db"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE
        }
        fun destroyInstance(){
            INSTANCE = null
        }
    }
}