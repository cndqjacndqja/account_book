package com.example.accounting.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "buget")
class Buget(@PrimaryKey(autoGenerate = true) var id : Int?,
            @ColumnInfo(name = "total_money") var TotalMoney: Int?,
            @ColumnInfo(name = "total_name") var TotalName: String?
)
{
    constructor() : this(null,null,"")
}