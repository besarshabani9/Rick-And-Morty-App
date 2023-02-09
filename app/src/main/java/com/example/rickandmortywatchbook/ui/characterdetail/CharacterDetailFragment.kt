package com.example.rickandmortywatchbook.ui.characterdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rickandmortywatchbook.R
import com.example.rickandmortywatchbook.databinding.FragmentCharacterDetailBinding
import com.mirtneg.rickandmorty.data.models.Episode
import com.squareup.picasso.Picasso

class CharacterDetailFragment : Fragment() {

    lateinit var binding : FragmentCharacterDetailBinding
    lateinit var viewModel: CharacterDetailViewModel
    private lateinit var adapter : EpisodesAdapter
    private val args by navArgs<CharacterDetailFragmentArgs>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCharacterDetailBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity())[CharacterDetailViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = EpisodesAdapter(this::onEpisodeClick)
        viewModel.getCharacterById(args.characterId)
        binding.episodesRecyclerView.layoutManager = LinearLayoutManager(requireActivity())
        binding.episodesRecyclerView.adapter = adapter


        viewModel.characterResponse.observe(viewLifecycleOwner){
            with(it){
                Picasso.get().load(image).into(binding.characterImage)
                binding.characterName.text = name

                binding.genderInfo.tagTextView.text = "Gender"
                binding.genderInfo.dataTextView.text = gender

                binding.statusInfo.tagTextView.text = "Status"
                binding.statusInfo.dataTextView.text = status

                binding.speciesInfo.tagTextView.text = "Species"
                binding.speciesInfo.dataTextView.text = species

                binding.originInfo.tagTextView.text = "Origin"
                binding.originInfo.dataTextView.text = origin.name

                binding.locationInfo.tagTextView.text = "Location"
                binding.locationInfo.dataTextView.text = location.name

                binding.typeInfo.tagTextView.text = "Type"
                binding.typeInfo.dataTextView.text = type


            }
        }

        viewModel.episodesRespons.observe(viewLifecycleOwner, Observer<List<Episode>>(){
            adapter.episodes = it
        })


        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun onEpisodeClick(episodeId : String){
        val bundle = Bundle()
        bundle.putString("episode_id", episodeId)
        findNavController().navigate(R.id.action_characterDetailFragment_to_episodeDetailFragment, bundle)
    }
}