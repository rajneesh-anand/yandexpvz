package com.neo.yandexpvz.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "coin")
data class CoinEntity(
    @PrimaryKey(autoGenerate = true)
    val recordId :Int =0,
    @ColumnInfo(name = "id")
    val id:Int=0,
    @ColumnInfo(name = "name")
    val name:String="",
    @ColumnInfo(name = "mobile")
    val mobile : String="",
    @ColumnInfo(name = "email")
    val email:String="",
    @ColumnInfo(name = "earnedCoin")
    val earnedCoin : Int=0,
    @ColumnInfo(name = "spentCoin")
    val spentCoin:Int=0,
    @ColumnInfo(name = "createdAt")
    val date : String="",

    )
