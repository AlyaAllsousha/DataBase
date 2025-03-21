package ru.example.a2102database

import androidx.room.Room

object SingletonBD {
    val bd = Room.databaseBuilder(
        MainActivity.getContext(),
        AppDataBase::class.java, "Belka1"
    ).build()
}