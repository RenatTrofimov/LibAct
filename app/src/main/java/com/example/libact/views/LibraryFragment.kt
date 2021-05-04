package com.example.libact.views

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.example.libact.R
import com.example.libact.modelsview.LibViewModel


class LibraryFragment:Fragment(R.layout.library_fragment) {
    private val libFragment = LibFragment()
    private val detailsFragment = DetailsFragment()
    private var libModel = LibViewModel()

    fun getDetailsView() = detailsFragment

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.i("LibViewModel", "Called ViewModelProvider.get")
        libModel = ViewModelProviders.of(requireActivity()).get(LibViewModel::class.java)
        libModel.setViews(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        placeFragments()
    }

    private fun placeFragments(){
        if(libModel.isLand){
            setFragment(R.id.list, libFragment)
            setFragment(R.id.details, detailsFragment)
        }
        else
            setFragment(R.id.main_lib_container, libFragment)
    }
    fun bind(){
        detailsFragment.bind()
    }
    fun <T:Fragment>setFragment(to:Int, fragment: T) {
        val fragManager  = requireActivity().supportFragmentManager
        val fragmentTransaction =  fragManager.beginTransaction()
        fragmentTransaction.replace(to, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

}