package com.example.rickandmortywatchbook.ui.episodedetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rickandmortywatchbook.repository.Repository
import com.mirtneg.rickandmorty.data.models.Character
import com.mirtneg.rickandmorty.data.models.Episode
import com.mirtneg.rickandmorty.data.models.EpisodeResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EpisodeDetailViewModel : ViewModel() {

    val episodeResponse = MutableLiveData<Episode>()
    val characterResponse =  MutableLiveData<List<Character>>()
    private  val repository = Repository()

    fun getEpisodeById(episodeId : String){
        repository.apiService.getEpisodeById(episodeId)
            .enqueue(object : Callback<Episode>{
                override fun onResponse(call: Call<Episode>, response: Response<Episode>) {

                    //episodeResponse.value = response.body()
                    val episode = response.body()
                    episode?.let {
                        var episodeCharacter = ""
                        it.characters.forEach { character ->
                            episodeCharacter += character.substring(
                                character.lastIndexOf("/") + 1, character.length) + ","
                        }

                        getCharactersByEpisode(episodeCharacter)
                        episodeResponse.postValue(it)
                    }
                }

                override fun onFailure(call: Call<Episode>, t: Throwable) {
                    t.printStackTrace()
                }
            })
    }

    fun getCharactersByEpisode(characters : String){
        repository.apiService.getCharacterPerEpisode(characters)
            .enqueue(object : Callback<List<Character>>{
                override fun onResponse(
                    call: Call<List<Character>>,
                    response: Response<List<Character>>
                ) {
                    characterResponse.postValue(response.body())
                }

                override fun onFailure(call: Call<List<Character>>, t: Throwable) {
                    t.printStackTrace()
                }
            })
    }
}