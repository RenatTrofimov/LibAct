package com.example.libact

import android.view.View
import android.widget.TextView
import androidx.room.*

@Entity(tableName = "kanji", ignoredColumns = ["rootId"])
data class Kanji(
    @ColumnInfo(name = "hieroglyph") val hieroglyph: String,
    @ColumnInfo(name = "on_reading") val on: String,
    @ColumnInfo(name = "kun_reading") val kun: String,
    @ColumnInfo(name = "translation") val translation: String
):Item(R.layout.lib_item) {
    @PrimaryKey(autoGenerate = true) var id: Int = 0
    override fun toString(): String {
        return hieroglyph
    }
    override fun bind(v: View) {
        val kanjiField: TextView = v.findViewById(R.id.lib_item_hieroglyph_TV)
        kanjiField.text = hieroglyph
    }
}




@Dao
interface KanjiDao:DaoBase<Kanji> {
    @Query("SELECT * FROM kanji")
    fun getAll(): List<Kanji>

    @Query("SELECT * FROM kanji WHERE id IN (:kanjiIds)")
    fun loadAllByIds(kanjiIds: IntArray): List<Kanji>

    @Query("SELECT * FROM kanji WHERE id = (:id)")
    fun findById(id: Int): Kanji
}