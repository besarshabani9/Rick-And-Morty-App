package com.example.rickandmortywatchbook.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortywatchbook.databinding.ListItemCharacterBinding
import com.mirtneg.rickandmorty.data.models.Character
import com.squareup.picasso.Picasso

class CharacterAdapter(val itemClick : (characterId : String) -> Unit) : RecyclerView.Adapter<CharacterAdapter.ViewHolder>() {

    var characterItem : List<Character> = mutableListOf()
    set(value){
        field = value
        notifyDataSetChanged()
    }

    class ViewHolder(val binding: ListItemCharacterBinding)
        : RecyclerView.ViewHolder(binding.root){
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding = ListItemCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var character = characterItem.get(position)
        with(holder){
            Picasso.get().load(character.image).into(binding.characterImage)
            binding.nameTextView.text = character.name
            binding.raceTextView.text = character.species
         }

        holder.itemView.setOnClickListener{
            itemClick.invoke(character.id.toString())
        }
    }

    override fun getItemCount(): Int = characterItem.size
}