package com.example.libact.lib_recycler_view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.libact.App
import com.example.libact.Kanji
import com.example.libact.R

class LibAdapter(private val LibList: ArrayList<Kanji>): RecyclerView.Adapter<LibVH>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LibVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.lib_item, parent, false)
        return LibVH(view)
    }


    override fun getItemCount() = LibList.size

    override fun onBindViewHolder(holder: LibVH, position: Int) {
        holder.bind(LibList[position])
    }
}