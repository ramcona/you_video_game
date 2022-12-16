package com.rafli.yourvideogames.dao

import androidx.room.*
import com.rafli.yourvideogames.model.Game


@Dao
interface GameDao {

    @Query("SELECT * FROM game ORDER BY id DESC")
    fun getAll(): List<Game>

    @Query("SELECT * FROM game WHERE id = :id")
    fun getById(id:Int): Game

    @Insert
    fun add(vararg data: Game)

    @Update
    fun update(vararg data: Game)

    @Delete
    fun delete(vararg data: Game)

}