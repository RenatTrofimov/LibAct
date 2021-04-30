package com.example.libact.lib_recycler_view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.libact.Kanji
import com.example.libact.R

class LibAdapter<T>(private val LibList: ArrayList<T>, private val onClick: (T) -> Unit): RecyclerView.Adapter<LibVH<T>>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LibVH<T> {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.lib_item, parent, false)
        return LibVH(view, onClick)
    }

    override fun getItemCount() = LibList.size

    override fun onBindViewHolder(holder: LibVH<T>, position: Int) {
        holder.bind(LibList[position])
    }
}