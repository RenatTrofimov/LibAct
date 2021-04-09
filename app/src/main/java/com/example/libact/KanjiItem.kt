package com.example.libact

import androidx.room.*

class KanjiItem {
}

@Entity(tableName = "kanji")
data class KanjiTable (
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "hieroglyph") val hieroglyph: String,
    @ColumnInfo(name = "on_reading") val on: String,
    @ColumnInfo(name = "kun_reading") val kun: String,
    @ColumnInfo(name = "translation") val translation: String
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
    fun getAll(): List<KanjiTable>

    @Query("SELECT * FROM kanji WHERE id IN (:kanjiIds)")
    fun loadAllByIds(kanjiIds: IntArray): List<KanjiTable>

    @Query("SELECT * FROM kanji WHERE id = (:id)")
    fun findById(id: Int): KanjiTable

    @Insert
    fun insertAll(vararg users: KanjiTable)

    @Delete
    fun delete(user: KanjiTable)
}

@Database(entities = [KanjiTable::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): KanjiDao
}