package com.example.libact.lib_recycler_view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.libact.Item
import com.example.libact.interfaces.Actions

class ItemAdapter<T : Item>(private var list: ArrayList<T>, private val act: Actions<T>): RecyclerView.Adapter<ItemViewHolder<T>>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder<T> {
        val view = LayoutInflater.from(parent.context).inflate(list[0].rootId, parent, false)
        return ItemViewHolder(view, act)
    }
    fun setItems(persons: ArrayList<T>) {
        list = persons
    }
    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ItemViewHolder<T>, position: Int) {
        holder.bind(list[position])
    }
}