package com.example.libact.surface

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.libact.Kanji
import com.example.libact.R
import com.example.libact.lib_recycler_view.LibAdapter
import com.example.libact.modelsview.TestViewModel
import kotlinx.android.synthetic.main.test_fragment.*

class SurfaceFragment: Fragment(R.layout.test_fragment) {
    private var adapter:LibAdapter? = null
    private lateinit var viewModel:TestViewModel

    private fun adapterOnClick(kanji: Kanji) {
        viewModel.example.add(kanji.hieroglyph)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("F", "onViewCreated")
        viewModel = ViewModelProvider(requireActivity()).get(TestViewModel::class.java)
        adapter = LibAdapter(viewModel.returnList()){kanji -> adapterOnClick(kanji)}
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i("F", "onViewCreated")
        rv_test_fragment.adapter = adapter
        done_btn.setOnClickListener {
            viewModel.example.check()
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
}
