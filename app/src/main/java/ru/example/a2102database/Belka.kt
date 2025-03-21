package ru.example.a2102database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Belka(
    @PrimaryKey(autoGenerate = true)
    val id:Int=0,
    //@ColumnInfo(name = "bugaga")
    val colorTail:String,
    val name:String
)
