package com.example.libact

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.libact.lib_recycler_view.ItemAdapter
import com.example.libact.modelsview.TestViewModel
import kotlinx.android.synthetic.main.test_fragment.*

class TestActivity : AppCompatActivity(), Actions<KanjiKey> {
    var isReCreate = false
    private var adapter:ItemAdapter<KanjiKey>? = null
    private lateinit var viewModel:TestViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        viewModel = ViewModelProvider(this).get(TestViewModel::class.java)
        viewModel.setTestList(1, 10)
        viewModel.fragment = this
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