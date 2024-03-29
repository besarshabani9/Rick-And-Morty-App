package com.example.rickandmortywatchbook.ui.characterdetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rickandmortywatchbook.repository.Repository
import com.mirtneg.rickandmorty.data.models.Character
import com.mirtneg.rickandmorty.data.models.CharacterResponse
import com.mirtneg.rickandmorty.data.models.Episode
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CharacterDetailViewModel : ViewModel() {

    private val repository = Repository()
    val character = MutableLiveData<Character>()
    val episodesRespons = MutableLiveData<List<Episode>>()
    val characterResponse = MutableLiveData<Character>()

    fun getCharacterById(characterId : String){
        repository.apiService.getCharacterById(characterId).enqueue(object : Callback<Character> {
            override fun onResponse(
                call: Call<Character>,
                response: Response<Character>
            ){
                val character = response.body()
                character?.let {
                    var episodes = ""
                    it.episode.forEach{ episode ->
                        episodes += episode.substring(
                            episode.lastIndexOf("/") + 1,
                            episode.length
                        ) +","
                    }
                    getEpisodePerCharacter(episodes)
                    characterResponse.postValue(it)
                }
            }

                override fun onFailure(call: Call<Character>, t: Throwable) {
                    t.printStackTrace()
                }
            })
    }

    private fun getEpisodePerCharacter(episodes : String){
        repository.apiService.getEpisodePerCharacter(episodes)
            .enqueue(object : Callback<List<Episode>>{
                override fun onResponse(
                    call: Call<List<Episode>>,
                    response: Response<List<Episode>>
                ) {
                    episodesRespons.postValue(response.body())
                }

                override fun onFailure(call: Call<List<Episode>>, t: Throwable) {
                    t.printStackTrace()
                }
            })
    }


}