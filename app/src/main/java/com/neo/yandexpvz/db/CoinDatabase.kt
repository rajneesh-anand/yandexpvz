package com.neo.yandexpvz.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.neo.yandexpvz.model.CoinEntity


@Database(entities = [CoinEntity::class], version = 1)

abstract class CoinDatabase : RoomDatabase(){
    abstract val coinDao : CoinDao
}

