package com.example.accounting.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

//총액 소비금액 잔액
@Entity(tableName = "account" )
class Account (@PrimaryKey(autoGenerate = true) var id: Int?,
              @ColumnInfo(name = "category") var Categoty : String,
              @ColumnInfo(name = "money") var Money : Int?,
              @ColumnInfo(name = "detail") var Detail : String,
              @ColumnInfo(name = "date_time")var Date: String)
{
   constructor() : this(null,"",null,"","")
}
