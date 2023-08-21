package com.neo.yandexpvz.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.neo.yandexpvz.model.CoinEntity


@Dao
interface CoinDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCoin(coinEntity: List<CoinEntity>)

    @Update
    fun updateCoin(coinEntity: CoinEntity)

    @Delete
    fun deleteCoin(coinEntity: CoinEntity)

    @Query("SELECT * FROM coin WHERE mobile=:mobile ")
    fun getCoins(mobile:String) : MutableList<CoinEntity>

    @Query("SELECT COUNT(*) FROM coin WHERE mobile=:mobile ")
    fun getNumberOfRecords(mobile:String) : Int

    @Query("SELECT SUM(earnedCoin) FROM coin WHERE mobile=:mobile ")
    fun sumEarnedCoins(mobile:String) : Int


    @Query("SELECT SUM(spentCoin) FROM coin WHERE mobile=:mobile ")
    fun sumSpentCoins(mobile:String) : Int
}