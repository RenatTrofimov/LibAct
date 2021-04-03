package com.example.libact.views

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.libact.Kanji
import com.example.libact.R
import com.example.libact.lib_recycler_view.LibAdapter
import com.example.libact.modelsview.LibViewModel
import kotlinx.android.synthetic.main.lib_kanji_fragment.*

class LibFragment():Fragment(R.layout.lib_kanji_fragment) {
    var libViewModel = LibViewModel()
    lateinit var adapter:LibAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = LibAdapter(libViewModel.kanjiList) { kanji -> adapterOnClick(kanji)}
        fragment_kanji_rv.adapter = adapter
    }
    private fun adapterOnClick(kanji: Kanji) {
        libViewModel.selectedKanji = kanji
    }

}