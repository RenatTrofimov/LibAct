package com.example.libact.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.libact.modelsview.LibViewModel

class FirstScreen: Fragment() {
    lateinit var libModel:LibViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val libFragment = LibFragment()
        val detailsFragment = DetailsFragment()
        libModel = ViewModelProviders.of(requireActivity()).get(LibViewModel::class.java)
    }
}