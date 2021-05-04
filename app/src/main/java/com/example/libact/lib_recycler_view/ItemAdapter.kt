package com.example.libact.lib_recycler_view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.libact.Item

class ItemAdapter<T: Item>(private val list: ArrayList<T>, private val onClick: (T) -> Unit): RecyclerView.Adapter<ItemViewHolder<T>>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder<T> {
        val view = LayoutInflater.from(parent.context).inflate(list[0].rootId, parent, false)
        return ItemViewHolder(view, onClick)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ItemViewHolder<T>, position: Int) {
        holder.bind(list[position])
    }
}