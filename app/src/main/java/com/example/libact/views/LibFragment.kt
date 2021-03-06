package com.example.libact.views

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.example.libact.interfaces.Actions
import com.example.libact.Kanji
import com.example.libact.R
import com.example.libact.lib_recycler_view.ItemAdapter
import com.example.libact.modelsview.LibViewModel
import kotlinx.android.synthetic.main.lib_kanji_fragment.*

open class LibFragment():Fragment(R.layout.lib_kanji_fragment), Actions<Kanji> {
    private lateinit var libViewModel:LibViewModel
    protected lateinit var adapter:ItemAdapter<Kanji>
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i("LibViewModel", "Called ViewModelProvider.get")
        libViewModel = ViewModelProviders.of(requireActivity()).get(LibViewModel::class.java)
        setRecyclerView()
    }
    open fun setRecyclerView(){
        adapter = ItemAdapter(libViewModel.kanjiList, this)
        adapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        fragment_kanji_rv.adapter = adapter
    }
    override fun onClick(item: Kanji) {
        libViewModel.sendDetails(item)
    }
}