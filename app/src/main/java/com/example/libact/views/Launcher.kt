package com.example.libact.views

import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.os.Bundle
import com.example.libact.App
import com.example.libact.R
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class Launcher : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launcher)
        val launch = this
        val i = Intent(this, MainActivity::class.java)
        GlobalScope.async { App.getDB().kanjiKeyDao().getAll()
            if(App.isBuilded){
                delay(1000)
                startActivity(i)
                launch.finish()
            }
        }
    }

}