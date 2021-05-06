package com.example.libact.views

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.example.libact.R
import com.example.libact.TestList
import com.example.libact.modelsview.LibViewModel

class MainActivity : AppCompatActivity() {
    private var tabSelected = 0
    private val tabSelectedStr = "tabSelectedStr"
    override fun onCreate(savedInstanceState: Bundle?) {
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
