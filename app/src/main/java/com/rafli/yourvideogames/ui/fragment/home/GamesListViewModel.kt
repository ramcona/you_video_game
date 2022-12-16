package com.rafli.yourvideogames.ui.fragment.home


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rafli.yourvideogames.model.Game
import com.rafli.yourvideogames.repositories.GamesRepo
import kotlinx.coroutines.*

class GamesListViewModel : ViewModel() {
    var job: Job? = null
    private var repo = GamesRepo()
    val gameList = MutableLiveData<List<Game>>()
    var isLoading = MutableLiveData<Boolean>()
    var totalItem = MutableLiveData<Int>()
    var errorMsg = MutableLiveData<String>()

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


    fun getListGames(page:Int) {
        isLoading.postValue(true)


        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val result = repo.getListGames(page)
            withContext(Dispatchers.Main) {
                if (result.isSuccessful){
                    val responseBody = result.body()
                    if (responseBody != null) {
                        totalItem.postValue(responseBody.count)

                        if (responseBody.results.isNotEmpty()) {
                            gameList.postValue(responseBody.results)
                            isLoading.postValue(false)
                        } else {
                            setError("Tidak ada data tersedia")
                        }
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

    fun searchGames(query:String, page:Int) {
        isLoading.postValue(true)


        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val result = repo.searchGames(query, page)
            withContext(Dispatchers.Main) {
                if (result.isSuccessful){
                    val responseBody = result.body()
                    if (responseBody != null) {
                        if (responseBody.results.isNotEmpty()) {
                            gameList.postValue(responseBody.results)
                            isLoading.postValue(false)
                        } else {
                            setError("Tidak ada data tersedia")
                        }
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