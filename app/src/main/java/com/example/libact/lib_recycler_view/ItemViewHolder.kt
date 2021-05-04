package com.example.libact.lib_recycler_view
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.libact.Item
import com.example.libact.R

class ItemViewHolder<T: Item>(itemView: View, val onClick: (T) -> Unit) : RecyclerView.ViewHolder(itemView) {
    fun bind(item: T) {
        item.bind(itemView)
        itemView.setOnClickListener {
            onClick(item)
        }
    }
}