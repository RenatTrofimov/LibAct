package com.example.libact.views

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.ViewAnimator
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.ContentFrameLayout
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProviders
import com.example.libact.R
import com.example.libact.modelsview.LibViewModel
import com.google.android.material.transition.platform.MaterialContainerTransform
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback

class MainActivity : AppCompatActivity() {
    private var tabSelected = 0
    private val tabSelectedStr = "tabSelectedStr"
    override fun onCreate(savedInstanceState: Bundle?) {

        window.requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)

        // Set the transition name, which matches Activity A’s start view transition name, on
        // the root view.
        findViewById<View>(android.R.id.content).transitionName = "shared_element_container"

0        // used by the container transform transition.
        setEnterSharedElementCallback(MaterialContainerTransformSharedElementCallback())

        // Set this Activity’s enter and return transition to a MaterialContainerTransform
        window.sharedElementEnterTransition = MaterialContainerTransform().apply {
            addTarget(android.R.id.content)
            duration = 300L
        }

        super.onCreate(savedInstanceState)
        if(savedInstanceState!= null){
            tabSelected = savedInstanceState.getInt(tabSelectedStr)
        }
        setContentView(R.layout.activity_main)
        landInit()
    }
    fun onCLickTestBtn(v: View){
        if(tabSelected == 0){
            tabSelected = 1
            landInit()
        }
    }
    fun onCLickLibBtn(v: View){
        if(tabSelected == 1){
            tabSelected = 0
            landInit()
        }
    }
    private fun landInit(){
        Log.i("LibViewModel", "Called ViewModelProvider.get")
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        Log.i("O", tabSelected.toString() )
        when(tabSelected){
            0 -> {
                val libModel = ViewModelProviders.of(this).get(LibViewModel::class.java)
                libModel.isLand = isLandOrientation()
                val libraryFragment = LibraryFragment()
                fragmentTransaction.replace(R.id.main_container, libraryFragment)
            }
            1 ->{
                val testList = TestList()
                fragmentTransaction.replace(R.id.main_container, testList)
            }
        }
        fragmentTransaction.commit()

    }

    private fun isLandOrientation():Boolean{
        return resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    }

    override fun onSaveInstanceState(outState: Bundle) {
        Log.i("MA", "onSaveInstanceState")
        super.onSaveInstanceState(outState)
        outState.putInt(tabSelectedStr, tabSelected)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        tabSelected = savedInstanceState.getInt(tabSelectedStr)
    }
}
