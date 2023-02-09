package com.example.rickandmortywatchbook.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rickandmortywatchbook.repository.Repository
import com.mirtneg.rickandmorty.data.models.Character
import com.mirtneg.rickandmorty.data.models.CharacterResponse
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class HomeViewModel : ViewModel() {

    var showingSearchResults : Boolean = false
        get() = field
        set(value) { field = value}

    val characterList : MutableLiveData<List<Character>> = MutableLiveData<List<Character>>()
    val filterResultsList : MutableLiveData<List<Character>> = MutableLiveData<List<Character>>()
    private val repository = Repository()


    fun getCharacters(){
        repository.apiService.getCharacters()
            .enqueue(object : retrofit2.Callback<CharacterResponse>{
                override fun onResponse(
                    call: Call<CharacterResponse>,
                    response: Response<CharacterResponse>
                ) {
                    characterList.value = response.body()?.results
                }

                override fun onFailure(call: Call<CharacterResponse>, t: Throwable) {
                    t.printStackTrace()
                }
            })
    }

    fun filterCharacters(
        species : String? = null,
        gender : String? = null,
        status : String? = null
    ){
        var url = "https://rickandmortyapi.com/api/character/?"
        url += "&species=$species"
        url += "&gender=$gender"
        url += "&status=$status"

        repository.apiService.filterCharacters(url)
            .enqueue(object : retrofit2.Callback<CharacterResponse>{
                override fun onResponse(
                    call: Call<CharacterResponse>,
                    response: Response<CharacterResponse>
                ) {
                    showingSearchResults = true
                    filterResultsList.value = response.body()?.results
                }

                override fun onFailure(call: Call<CharacterResponse>, t: Throwable) {
                    t.printStackTrace()
                }
            })

    }
}