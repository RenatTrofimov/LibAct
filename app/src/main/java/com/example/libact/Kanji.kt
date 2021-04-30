package com.example.libact

import androidx.room.*

@Entity(tableName = "kanji")
data class Kanji (
    @ColumnInfo(name = "hieroglyph") val hieroglyph: String,
    @ColumnInfo(name = "on_reading") val on: String,
    @ColumnInfo(name = "kun_reading") val kun: String,
    @ColumnInfo(name = "translation") val translation: String
){
    @PrimaryKey(autoGenerate = true) var id: Int = 0
    override fun toString(): String {
        return hieroglyph
    }
}
interface BaseDao<T> {
    @Insert
    fun insertAll(vararg users: T)
    @Insert
    fun insertItem(user: T)
    @Delete
    fun delete(user: T)
}
@Dao
interface KanjiDao:BaseDao<Kanji> {
    @Query("SELECT * FROM kanji")
    fun getAll(): List<Kanji>

    @Query("SELECT * FROM kanji WHERE id IN (:kanjiIds)")
    fun loadAllByIds(kanjiIds: IntArray): List<Kanji>

    @Query("SELECT * FROM kanji WHERE id = (:id)")
    fun findById(id: Int): Kanji
}