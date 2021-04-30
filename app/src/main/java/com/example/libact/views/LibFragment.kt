package com.example.libact.views

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.libact.Kanji
import com.example.libact.R
import com.example.libact.lib_recycler_view.LibAdapter
import com.example.libact.modelsview.LibViewModel
import kotlinx.android.synthetic.main.lib_kanji_fragment.*

class LibFragment():Fragment(R.layout.lib_kanji_fragment) {
    lateinit var libViewModel:LibViewModel
    private lateinit var adapter:LibAdapter<Kanji>
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i("LibViewModel", "Called ViewModelProvider.get")
        libViewModel = ViewModelProviders.of(requireActivity()).get(LibViewModel::class.java)
        adapter = LibAdapter(libViewModel.kanjiList) { kanji -> adapterOnClick(kanji)}
        fragment_kanji_rv.adapter = adapter
    }
    private fun adapterOnClick(kanji: Kanji) {
        libViewModel.sendDetails(kanji)
    }


}