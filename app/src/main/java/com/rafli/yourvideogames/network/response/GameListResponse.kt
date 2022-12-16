package com.rafli.yourvideogames.network.response

import com.rafli.yourvideogames.model.Game
import java.io.Serializable

class GameListResponse : Serializable {
    var results:List<Game> = ArrayList()
    var error:String = ""
    var count:Int = 0

}