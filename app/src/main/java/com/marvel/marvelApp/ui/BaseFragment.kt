package com.marvel.marvelApp.ui

import androidx.fragment.app.Fragment

open class BaseFragment<T> : Fragment() {
    protected var _binding : T? = null
    protected val binding get() = _binding!!

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}