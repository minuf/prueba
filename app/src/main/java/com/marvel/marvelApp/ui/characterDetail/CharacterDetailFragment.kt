package com.marvel.marvelApp.ui.characterDetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.marvelapp.R
import com.example.marvelapp.databinding.FragmentCharacterDetailBinding

class CharacterDetailFragment : Fragment() {

    private var _binding: FragmentCharacterDetailBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentCharacterDetailBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonSecond.setOnClickListener {
            findNavController().navigate(R.id.action_CharacterDetailFragment_to_CharactersFragment)
        }

        val id = arguments?.getInt("characterId")
        println("CHARACTER ID $id")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}