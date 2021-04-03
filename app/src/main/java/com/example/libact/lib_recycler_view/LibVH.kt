package com.example.libact.lib_recycler_view
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.libact.Kanji
import com.example.libact.R

class LibVH(itemView: View, val onClick: (Kanji) -> Unit) : RecyclerView.ViewHolder(itemView) {
    private var kanjiField: TextView = itemView.findViewById(R.id.lib_item_hieroglyph_TV)

    private var translationField: TextView =
        itemView.findViewById(R.id.lib_item_certain_translation_TV)

    fun bind(kanji: Kanji) {
        kanjiField.text = kanji.hieroglyph

        translationField.text = kanji.translation
        itemView.setOnClickListener {
            onClick(kanji)
        }
    }
}


