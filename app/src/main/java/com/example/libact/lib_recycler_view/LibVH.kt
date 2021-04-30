package com.example.libact.lib_recycler_view
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.libact.Kanji
import com.example.libact.R

class LibVH<T>(itemView: View, val onClick: (T) -> Unit) : RecyclerView.ViewHolder(itemView) {
    private var kanjiField: TextView = itemView.findViewById(R.id.lib_item_hieroglyph_TV)
    fun bind(kanji: T) {
        kanjiField.text = kanji.toString()
        itemView.setOnClickListener {
            onClick(kanji)
        }
    }
}


