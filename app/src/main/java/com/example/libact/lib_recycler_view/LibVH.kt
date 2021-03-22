package com.example.libact.lib_recycler_view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.libact.Kanji
import com.example.libact.R
import java.util.*

class LibVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private var kanjiField: TextView = itemView.findViewById(R.id.lib_item_hieroglyph_TV)
    private var kunField: TextView = itemView.findViewById(R.id.lib_item_kun_TV)
    private var onField: TextView = itemView.findViewById(R.id.lib_item_on_TV)
    private var translationField: TextView = itemView.findViewById(R.id.lib_item_certain_translation_TV)
    fun bind(kanji: Kanji){
        kanjiField.text = kanji.hieroglyph
        kunField.text = kanji.kun
        onField.text = kanji.on
        translationField.text = kanji.translation
    }
}




