package com.rafli.yourvideogames.network

import com.rafli.yourvideogames.model.*
import com.rafli.yourvideogames.network.response.*
import retrofit2.Response
import retrofit2.http.*

interface ApiServiceServer {

    @GET("games")
    suspend fun getListGames(
        @Query("page") page:Int,
        @Query("page_size") page_size:Int,
        @Query("key") key:String,
    ) : Response<GameListResponse>

    @GET("games")
    suspend fun searchGames(
        @Query("page") page:Int,
        @Query("page_size") page_size:Int,
        @Query("search") search:String,
        @Query("key") key:String,
    ) : Response<GameListResponse>

    @GET("games/{id}")
    suspend fun getDetailGame(
        @Path("id") id:Int,
        @Query("key") key:String,
    ) : Response<Game>


}