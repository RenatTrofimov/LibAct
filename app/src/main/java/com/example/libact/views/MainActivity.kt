package com.example.libact.views

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.example.libact.R
import com.example.libact.modelsview.LibViewModel
import com.example.libact.surface.SurfaceFragment

class MainActivity : AppCompatActivity() {
    var tabSelected = 0
    val tabSelectedStr = "tabSelectedStr"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        when(tabSelected){
            0 -> {

            }
            1 ->{

            }
        }
    }
    private fun landInit(){
        val libFragment = LibFragment()
        val detailsFragment = DetailsFragment()

        Log.i("LibViewModel", "Called ViewModelProvider.get")

        val libModel = ViewModelProviders.of(this).get(LibViewModel::class.java)

        libModel.setViews( detailsFragment, libFragment)

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        if (!isLandOrientation()){
            fragmentTransaction.replace(R.id.main_container, libFragment)
        }else{

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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(tabSelectedStr, tabSelected)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        tabSelected = savedInstanceState.getInt(tabSelectedStr)
    }
}
