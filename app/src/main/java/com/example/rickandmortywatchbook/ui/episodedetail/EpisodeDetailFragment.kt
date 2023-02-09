package com.example.rickandmortywatchbook.ui.episodedetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortywatchbook.R
import com.example.rickandmortywatchbook.databinding.FragmentEpisodeDetailBinding
import com.example.rickandmortywatchbook.ui.home.CharacterAdapter

class EpisodeDetailFragment : Fragment() {

    lateinit var binding : FragmentEpisodeDetailBinding
    lateinit var viewModel : EpisodeDetailViewModel
    private val args by navArgs<EpisodeDetailFragmentArgs>()
    lateinit var adapter : CharacterAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEpisodeDetailBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity())[EpisodeDetailViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getEpisodeById(args.episodeId)

        binding.characterRecyclerView.layoutManager = LinearLayoutManager(requireActivity())
        adapter = CharacterAdapter(this::onEpisodeClick)
        binding.characterRecyclerView.adapter = adapter

        viewModel.characterResponse.observe(viewLifecycleOwner){
            adapter.characterItem = it
        }

        viewModel.episodeResponse.observe(viewLifecycleOwner){
            with(it){
                binding.episodeTitle.text = name.toString()

                binding.episodeInfo.tagTextView.text = "Episode"
                binding.episodeInfo.dataTextView.text = episode

                binding.airDate.tagTextView.text = "Date"
                binding.airDate.dataTextView.text = airDate


            }
        }



    }

    fun onEpisodeClick(characterId : String){
        val bundle = Bundle()
        bundle.putString("characterId", characterId)
        findNavController().navigate(
            R.id.action_episodeDetailFragment_to_characterDetailFragment
            ,bundle)

    }

}