package com.example.accounting.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query

@Dao
interface BugetDao{
    //들어가 있는지 확인
    @Query("SELECT * FROM buget")
    fun getAll() : MutableList<BugetData>

    //현재 보유돈 만 바꾸기
    @Query("UPDATE buget SET total_money = :money WHERE total_name = '총액'")
    fun changeBuget(money :Int): Int

    //돈이 초기화가 안되어있으면  insert
    @Insert(onConflict = REPLACE)
    fun insert(buget : Buget)

    //데이터 삭제
    @Query("DELETE FROM buget")
    fun delete()

}