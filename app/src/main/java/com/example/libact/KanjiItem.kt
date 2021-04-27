package com.example.libact

import androidx.room.*

@Entity(tableName = "kanjiKey")
data class KanjiKey(
    @ColumnInfo(name = "key") val key: String
){
    @PrimaryKey(autoGenerate = true) var id: Int = 0
}
@Dao
interface KanjiKeyDao {
    @Query("SELECT * FROM kanjiKey")
    fun getAll(): List<KanjiKey>

    @Query("SELECT * FROM kanjiKey WHERE id IN (:kanjiIds)")
    fun loadAllByIds(kanjiIds: IntArray): List<KanjiKey>

    @Query("SELECT * FROM kanjiKey WHERE id = (:id)")
    fun findById(id: Int): KanjiKey

    @Insert
    fun insertAll(vararg users: KanjiKey)
    @Insert
    fun insertItem(user: KanjiKey)
    @Delete
    fun delete(user: KanjiKey)
}




@Database(entities = [Kanji::class, KanjiKey::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun kanjiDao(): KanjiDao
    abstract fun kanjiKeyDao(): KanjiKeyDao
}