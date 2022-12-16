package com.rafli.yourvideogames.ui.activity.detailGame


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rafli.yourvideogames.app.App.Companion.db
import com.rafli.yourvideogames.model.Game
import com.rafli.yourvideogames.repositories.GamesRepo
import kotlinx.coroutines.*

class DetailGameViewModel : ViewModel() {
    var job: Job? = null
    private var repo = GamesRepo()
    val detailGame = MutableLiveData<Game>()
    var isLoading = MutableLiveData<Boolean>()
    var errorMsg = MutableLiveData<String>()
    var isSavedToLocal = MutableLiveData<Boolean>()

    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        setError("Exception handled: ${throwable.localizedMessage}")
    }

    private fun setError(message: String) {
        errorMsg.postValue(message)
        isLoading.postValue(false)
    }

    override fun onCleared() {
        super.onCleared()

        try{
            job?.cancel()
        }catch (_: UninitializedPropertyAccessException){

        }

    }

    fun checkSaved(id:Int){
        db?.gameDao()?.getById(id).let {
            if (it != null){
                isSavedToLocal.postValue(true)
            }else{
                isSavedToLocal.postValue(false)
            }
        }
    }

    fun addFavorite(game:Game){
        db?.gameDao()?.add(game)
        checkSaved(game.id)
    }

    fun  removeFromFavorite(game:Game){
        db?.gameDao()?.delete(game)
        checkSaved(game.id)
    }

    fun getDetail(id:Int) {
        isLoading.postValue(true)


        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val result = repo.getDetailGame(id)
            withContext(Dispatchers.Main) {
                if (result.isSuccessful){
                    val responseBody = result.body()
                    if (responseBody != null) {
                        val game = responseBody ?: Game()
                        detailGame.postValue(game)
                        isLoading.postValue(false)
                    }else {
                        setError("Tidak ada data tersedia")
                    }
                }else{
                    val responseBody = result.body()
                    if (responseBody != null) {
                        setError(responseBody.error)
                    }else{
                        setError(result.message())
                    }

                }
            }
        }
    }


}