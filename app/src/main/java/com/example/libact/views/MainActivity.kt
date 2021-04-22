package com.example.libact.views

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.SurfaceView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.libact.R
import com.example.libact.modelsview.LibViewModel
import com.example.libact.surface.SurfaceFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        testSurface(SurfaceFragment())

        //landInit()
    }
    private fun landInit(){
        val libFragment = LibFragment()
        val detailsFragment = DetailsFragment()

        Log.i("LibViewModel", "Called ViewModelProvider.get")

        val libModel = ViewModelProviders.of(this).get(LibViewModel::class.java)

        libModel.setViews(this, detailsFragment, libFragment)

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
    fun openDetailsFragment(fragment:  DetailsFragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction().addToBackStack(null)
        fragmentTransaction.replace(R.id.main_container, fragment)
        fragmentTransaction.commit()
    }
    fun isLandOrientation():Boolean{
        return resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    }
    fun testSurface(fragment: SurfaceFragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.main_container, fragment)
        fragmentTransaction.commit()
    }
}