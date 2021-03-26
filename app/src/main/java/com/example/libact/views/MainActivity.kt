package com.example.libact.views

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.libact.R
import com.example.libact.lib_recycler_view.LibAdapter
import com.example.libact.modelsview.LibViewModel

class MainActivity : AppCompatActivity() {

    lateinit var libModel : LibViewModel
    private val libFragment = LibFragment()
    private val detailsFragment = DetailsFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        libModel = ViewModelProvider(this).get(LibViewModel::class.java)
        libFragment.libViewModel = libModel
        detailsFragment.libViewModel = libModel

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        if (!isLandOrientation()){
            fragmentTransaction.replace(R.id.main_container, libFragment)
        }else{
            fragmentTransaction.replace(R.id.lnd_lib_fragment, libFragment)
            fragmentTransaction.replace(R.id.lnd_details_fragment, detailsFragment)
        }
        fragmentTransaction.commit()

    }

    private fun isLandOrientation():Boolean{
        return resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    }
}