package com.rafli.yourvideogames.ui.fragment.favorite


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rafli.yourvideogames.app.App.Companion.db
import com.rafli.yourvideogames.model.Game
import com.rafli.yourvideogames.repositories.GamesRepo
import kotlinx.coroutines.*

class FavoriteViewModel : ViewModel() {
    val gameList = MutableLiveData<List<Game>>()
    var isLoading = MutableLiveData<Boolean>()
    var errorMsg = MutableLiveData<String>()



    fun getFavorite() {
        isLoading.postValue(true)

        db?.gameDao()?.getAll().let {
            if (it.isNullOrEmpty()){
                errorMsg.postValue("Tidak ada data tersedia")
            }else{
                gameList.postValue(it)
            }
        }

        isLoading.postValue(false)


    }

}