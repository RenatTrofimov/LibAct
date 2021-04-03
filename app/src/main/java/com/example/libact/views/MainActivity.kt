package com.example.libact.views

import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.example.libact.R
import com.example.libact.modelsview.LibViewModel

class MainActivity : AppCompatActivity() {

    private var libModel = LibViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val libFragment = LibFragment()
        val detailsFragment = DetailsFragment()
        libModel = ViewModelProviders.of(this).get(LibViewModel::class.java)
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