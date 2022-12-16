package com.rafli.yourvideogames.repositories

import com.rafli.yourvideogames.helper.BaseHelper
import com.rafli.yourvideogames.helper.Config

class GamesRepo: BaseHelper(){

    suspend fun getListGames(page:Int, page_size:Int  = 20) = ApiServiceServer.getListGames(page, page_size, Config.RAWG_TOKEN)

    suspend fun searchGames(query:String, page:Int, page_size:Int  = 20) = ApiServiceServer.searchGames(page, page_size, query, Config.RAWG_TOKEN)

    suspend fun getDetailGame(id:Int) = ApiServiceServer.getDetailGame(id = id, key = Config.RAWG_TOKEN)

}