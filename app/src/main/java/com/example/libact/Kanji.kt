package com.example.libact

import androidx.room.*

class Kanji(val hieroglyph: String,
            val on: String,
            val kun: String,
            val translation: String) {


}
@Entity(tableName = "kanji")
data class KanjiDraw (
    @PrimaryKey(autoGenerate = true)val kanjiId: Int,
    @ColumnInfo(name = "hieroglyph") val hieroglyph: String
)
@Entity
data class KanjiReading(
    @PrimaryKey val kanjiId: Int,
    @ColumnInfo(name = "on_reading") val onReading: String?,
    @ColumnInfo(name = "kun_reading") val kun_reading: String?
)

@Dao
interface KanjiDao {
    @Query("SELECT * FROM kanji")
    fun getAll(): List<KanjiDraw>

    @Query("SELECT * FROM kanji WHERE kanjiId IN (:kanjiIds)")
    fun loadAllByIds(kanjiIds: IntArray): List<KanjiDraw>

    @Query("SELECT * FROM kanji WHERE kanjiId = (:id)")
    fun findById(id: Int): KanjiDraw

    @Insert
    fun insertAll(vararg users: KanjiDraw)

    @Delete
    fun delete(user: KanjiDraw)
}

@Database(entities = [KanjiDraw::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): KanjiDao
}