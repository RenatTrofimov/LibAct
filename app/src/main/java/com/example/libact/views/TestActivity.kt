package com.example.libact.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.example.libact.KanjiKey
import com.example.libact.R
import com.example.libact.interfaces.Actions
import com.example.libact.lib_recycler_view.ItemAdapter
import com.example.libact.modelsview.TestViewModel
import kotlinx.android.synthetic.main.activity_test.*
import kotlinx.android.synthetic.main.test_fragment.question_tv
import kotlinx.android.synthetic.main.test_fragment.rv_test_fragment

class TestActivity : AppCompatActivity(), Actions<KanjiKey> {

    private val isRecreatedStr = "isRecreated"
    var isRecreated = false

    private var adapter:ItemAdapter<KanjiKey>? = null
    private lateinit var viewModel:TestViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
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

    override fun onResume() {
        super.onResume()
        surfaceView_test_fragment.set(viewModel.example)
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