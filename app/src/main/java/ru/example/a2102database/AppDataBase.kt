package ru.example.a2102database

import androidx.room.Database
import androidx.room.RoomDatabase
@Database(entities =  arrayOf(Belka::class), version = 1)

abstract class AppDataBase:RoomDatabase() {
    abstract fun belkaDao():BelkaDAO

}