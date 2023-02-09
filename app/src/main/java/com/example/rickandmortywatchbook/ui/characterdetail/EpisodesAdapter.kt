package com.example.rickandmortywatchbook.ui.characterdetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortywatchbook.databinding.FragmentCharacterDetailBinding
import com.example.rickandmortywatchbook.databinding.ItemEpisodeBinding
import com.mirtneg.rickandmorty.data.models.Episode

class EpisodesAdapter(val itemClick : (episodeId : String) -> Unit) : RecyclerView.Adapter<EpisodesAdapter.ViewHolder>(){

    var episodes : List<Episode> = mutableListOf()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    class ViewHolder(val binding : ItemEpisodeBinding)
        : RecyclerView.ViewHolder(binding.root){
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemEpisodeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val episode = episodes.get(position)
        with(holder){
            binding.episodeNameTextView.text = episode.name
            binding.episodeCountTextView.text = episode.episode
            binding.episodeDateTextView.text = episode.airDate
        }

        holder.itemView.setOnClickListener{
            itemClick.invoke(episode.id.toString())
        }
    }

    override fun getItemCount(): Int = episodes.size
}