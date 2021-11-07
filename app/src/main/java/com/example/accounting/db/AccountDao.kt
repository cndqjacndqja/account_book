package com.example.accounting.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query

@Dao
interface AccountDao {
    @Query("SELECT * FROM account")
    fun getAll() : MutableList<AccountData>

    //카테고리가 00인거 전부 가져오기
    @Query("SELECT * FROM account WHERE category = :category")
    fun getAccountListByCategory(category : String): MutableList<AccountData>

    @Query("SELECT DISTINCT category FROM account")
    fun getCategory(): MutableList<String>

    //왜 account: Account를 쓸까..?
    //집어 넣을 Db에 맞게 만들은 Account데클이 있어서 데클자료형을 넣으면 알아서 들어가나 보다.
    @Insert(onConflict = REPLACE)
    fun insert(account: Account)

    //소비금액 합치기
    @Query("SELECT SUM(money) as Money FROM account")
    fun sumAccountMoney() : MutableList<Int>

    //데이터 삭제
    @Query("DELETE FROM account")
    fun delete()
}