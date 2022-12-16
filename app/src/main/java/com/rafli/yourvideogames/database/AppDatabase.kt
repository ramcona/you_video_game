package com.rafli.yourvideogames.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rafli.yourvideogames.dao.GameDao
import com.rafli.yourvideogames.model.Game

@Database(entities = [Game::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun gameDao() : GameDao?

}