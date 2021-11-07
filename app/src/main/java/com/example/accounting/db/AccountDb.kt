package com.example.accounting.db


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Account::class], version = 1)
abstract class AccountDb : RoomDatabase(){
    abstract fun accountDao(): AccountDao

    companion object{
        private var INSTANCE: AccountDb? = null

        fun getInstance(context: Context): AccountDb?{
            if(INSTANCE == null) {
                synchronized(AccountDb::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AccountDb::class.java, "account.db"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}