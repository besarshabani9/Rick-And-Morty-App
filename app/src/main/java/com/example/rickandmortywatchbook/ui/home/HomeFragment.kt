package com.example.rickandmortywatchbook.ui.home

import android.app.Dialog
import android.os.Bundle
import android.view.*
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rickandmortywatchbook.R
import com.example.rickandmortywatchbook.databinding.DialogAdvancedFiltersBinding
import com.example.rickandmortywatchbook.databinding.FragmentHomeBinding
import com.mirtneg.rickandmorty.data.models.Character

class HomeFragment : Fragment() {
    lateinit var binding : FragmentHomeBinding

    lateinit var viewMode : HomeViewModel
    lateinit var adapter : CharacterAdapter

    lateinit var dialog : Dialog
    lateinit var dialogBinding : DialogAdvancedFiltersBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container ,false)
        viewMode = ViewModelProvider(requireActivity())[HomeViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewMode.getCharacters()

        setupDialogFilter()

        binding.filterButton.setOnClickListener{
            showDetailedFilterDialog()
        }

        binding.characterList.layoutManager = LinearLayoutManager(requireActivity())
        adapter = CharacterAdapter(this::itemClick)
        binding.characterList.adapter = adapter

        viewMode.characterList.observe(viewLifecycleOwner,Observer<List<Character>>(){
            adapter.characterItem = it
        })

        viewMode.filterResultsList.observe(viewLifecycleOwner,Observer<List<Character>>(){
            adapter.characterItem = it
        })

        binding.searchEditText.doOnTextChanged{text, start, before, count ->
            viewMode.characterList.value?.let { safeCharacter ->
                adapter.characterItem = safeCharacter.filter { character ->
                    character.name.startsWith(
                        text.toString(),true)
                }
            }

        }

    }

    private fun setupDialogFilter(){
        dialogBinding = DialogAdvancedFiltersBinding.inflate(requireActivity().layoutInflater)
        dialog = Dialog(requireActivity())
        dialog.setContentView(dialogBinding.root)

        val layoutParameters = dialog.window!!.attributes
        layoutParameters.width = WindowManager.LayoutParams.MATCH_PARENT
        dialog.window!!.attributes = layoutParameters

        dialogBinding.closeButton.setOnClickListener{
            dialog.dismiss()
        }



        dialogBinding.applyButton.setOnClickListener {

            if (viewMode.showingSearchResults){

                viewMode.showingSearchResults = false
                viewMode.characterList.value?.let {
                    adapter.characterItem = it
                }
                dialogBinding.specieEditText.setText("")
                dialogBinding.genderEditText.setText("")
                dialogBinding.statusEditText.setText("")
            } else{

                viewMode.filterCharacters(
                    dialogBinding.specieEditText.text.toString(),
                    dialogBinding.genderEditText.text.toString(),
                    dialogBinding.statusEditText.text.toString())

            }
            dialog.dismiss()
        }


    }
    private fun showDetailedFilterDialog(){

        if (viewMode.showingSearchResults){
            dialogBinding.applyButton.setText("Clear Filters")
        } else{
            dialogBinding.applyButton.setText("Apply")
        }
        dialog.show()
    }

    private fun itemClick(characterId : String){
        val bundle = Bundle()
        bundle.putString("character_id", characterId)
        findNavController().navigate(R.id.action_homeFragment_to_characterDetailFragment, bundle)
    }
}