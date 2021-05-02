package com.example.libact.views

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProviders
import com.example.libact.R
import com.example.libact.modelsview.LibViewModel


class LibraryFragment:Fragment(R.layout.library_fragment) {

    private val libFragment = LibFragment()
    private val detailsFragment = DetailsFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.i("LibViewModel", "Called ViewModelProvider.get")

        val libModel = ViewModelProviders.of(requireActivity()).get(LibViewModel::class.java)

        libModel.setViews(this, detailsFragment, libFragment)
    }

    fun openDetailsFragment(detailsView: DetailsFragment) {
        val fragManager  = requireActivity().supportFragmentManager
        fragManager.beginTransaction()

        fragManager.commit{

        }
    }

}