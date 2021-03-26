package com.example.libact.lib_recycler_view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.libact.Kanji
import com.example.libact.R

class LibAdapter : RecyclerView.Adapter<LibAdapter.LibVH> {
    private var libList: ArrayList<Kanji>
    constructor(LibList: ArrayList<Kanji>) : super() {
        this.libList = LibList
    }
    fun getLibList():ArrayList<Kanji>{
        return libList
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LibVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.lib_item, parent, false)
        return LibVH(view)
    }

    override fun getItemCount() = libList.size

    override fun onBindViewHolder(holder: LibVH, position: Int) {
        holder.bind(libList[position], position)
    }
    class LibVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var kanjiField: TextView = itemView.findViewById(R.id.lib_item_hieroglyph_TV)
        private var id_item: TextView = itemView.findViewById(R.id.id_item)

        fun bind(kanji: Kanji, position: Int) {
            kanjiField.text = kanji.hieroglyph
            id_item.text = position.toString()
        }
    }
}