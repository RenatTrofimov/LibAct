package com.example.libact.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import androidx.lifecycle.ViewModelProvider
import com.example.libact.KanjiKey
import com.example.libact.R
import com.example.libact.interfaces.Actions
import com.example.libact.lib_recycler_view.ItemAdapter
import com.example.libact.modelsview.TestViewModel
import com.example.libact.surface.CustomCanvasForTest
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.transition.platform.MaterialContainerTransform
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback
import kotlinx.android.synthetic.main.activity_test.*
import kotlinx.android.synthetic.main.test_fragment.question_tv
import kotlinx.android.synthetic.main.test_fragment.rv_test_fragment

class TestActivity : AppCompatActivity(), Actions<KanjiKey> {

    private val isRecreatedStr = "isRecreated"
    var isRecreated = false

    private var adapter:ItemAdapter<KanjiKey>? = null
    private lateinit var viewModel:TestViewModel
    private lateinit var surface: CustomCanvasForTest

    override fun onCreate(savedInstanceState: Bundle?) {


        Log.i("TA", "onCreate")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        surface = surfaceView_test_fragment
        viewModel = ViewModelProvider(this).get(TestViewModel::class.java)
        viewModel.activity = this
        if(savedInstanceState!=null){
            isRecreated = savedInstanceState.getBoolean(isRecreatedStr)
        }
        if(!isRecreated){
            val id = intent.getIntExtra("ID", 1)
            if(viewModel.setTestList(id, 10)){
                viewModel.warring()
            }else{
                viewModel.setQuestion()
            }
            isRecreated = false


        }else{
            viewModel.reSetQuestion()
        }

        done.setOnClickListener {
            viewModel.check()
        }
        clear.setOnClickListener {
            viewModel.example.clean()
        }

    }
    override fun onStart() {
        Log.i("TA", "onStart")
        super.onStart()
        surface.set(viewModel.example)
    }
    override fun onResume() {
        Log.i("TA", "onResume")
        super.onResume()

    }
    override fun onSaveInstanceState(outState: Bundle) {
        Log.i("TA", "onSaveInstanceState")
        super.onSaveInstanceState(outState)
        isRecreated = true
        outState.putBoolean(isRecreatedStr, isRecreated)
    }
    fun setQuestion(title:String){
        adapter = ItemAdapter(viewModel.returnList(), this)
        rv_test_fragment.adapter = adapter
        question_tv.text = title
    }
    override fun onClick(item: KanjiKey) {
        viewModel.example.add(item)
    }

}