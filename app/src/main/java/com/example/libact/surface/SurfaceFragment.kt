package com.example.libact.surface

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.libact.Actions
import com.example.libact.KanjiKey
import com.example.libact.R
import com.example.libact.lib_recycler_view.ItemAdapter
import com.example.libact.modelsview.TestViewModel
import kotlinx.android.synthetic.main.test_fragment.*

class SurfaceFragment: Fragment(R.layout.test_fragment), Actions<KanjiKey>{
    private var adapter:ItemAdapter<KanjiKey>? = null
    private lateinit var viewModel:TestViewModel

    fun setQuestion(title:String){
        adapter = ItemAdapter(viewModel.returnList(), this)
        rv_test_fragment.adapter = adapter
        question_tv.text = title
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("F", "onViewCreated")
        viewModel = ViewModelProvider(requireActivity()).get(TestViewModel::class.java)
        viewModel.setTestList(1, 10)
        viewModel.fragment = this
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i("F", "onViewCreated")
        viewModel.setQuestion()
        done_btn.setOnClickListener {
            viewModel.check()
        }
        clear_btn.setOnClickListener {
            viewModel.example.clean()
        }
    }
    override fun onResume() {
        super.onResume()
        surfaceView_test_fragment.set(viewModel.example)
        Log.i("F", "onResume")
    }
    override fun onStop() {
        super.onStop()
        Log.i("F", "onStop")
    }
    override fun onClick(item: KanjiKey) {
        viewModel.example.add(item)
    }
}
