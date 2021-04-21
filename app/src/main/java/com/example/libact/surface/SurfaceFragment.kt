package com.example.libact.surface

import android.content.Context
import android.graphics.*
import kotlinx.coroutines.*
import android.os.Bundle
import android.util.Log

import android.view.View
import androidx.constraintlayout.solver.widgets.Rectangle
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.libact.R
import kotlinx.android.synthetic.main.test_fragment.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import java.util.*
import kotlin.coroutines.CoroutineContext


class SurfaceFragment: Fragment(R.layout.test_fragment) {
    val example = com.example.libact.surface.Example()
    val tree = Tree<String>("0")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.i("F", "fragmentCr")
    }
    override fun onResume() {
        super.onResume()
        Log.i("F", "onResume")
        surfaceView.send(example)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }
}
