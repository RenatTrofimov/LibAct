package com.example.libact

import android.view.View
import android.widget.TextView
import androidx.room.*

abstract class Item(val rootId:Int){
    abstract fun bind(v: View)
}

@Dao
interface DaoBase<T>{
    @Insert
    fun insertAll(vararg item: T)
    @Insert
    fun insertItem(item: T)
    @Delete
    fun delete(item: T)
    @Delete
    fun delete(vararg item: T)
    @Update
    fun update(item: T)
}

@Entity(tableName = "manyTestManyQuestion", primaryKeys = ["test_id", "kanji_id"])
data class ManyTestManyQuestion(val test_id: Int,
                                val kanji_id: Int,
                                @ColumnInfo(name = "kun") var kunCheck: Int,
                                @ColumnInfo(name = "on") var onCheck: Int,
                                @ColumnInfo(name = "trans") var transCheck: Int)
@Dao
interface ManyTestManyQuestionDao:DaoBase<ManyTestManyQuestion>{
    @Query("SELECT * FROM manyTestManyQuestion WHERE test_id = (:id)")
    fun getKeysById(id: Int): List<ManyTestManyQuestion>
    @Query("SELECT * FROM manyTestManyQuestion WHERE test_id = (:id) AND `kun` + `on` + `trans` < 3 LIMIT (:limit)")
    fun getKeysByIdLimitedBy(id: Int, limit:Int): List<ManyTestManyQuestion>

}
@Entity(tableName = "key_id", primaryKeys = ["id", "key"])
data class KeyId(
    val id: Int,
    val key: Int
)
@Dao
interface KeyIdDao:DaoBase<KeyId>{
    @Query("SELECT `key` FROM key_id WHERE id = (:id) ")
    fun getKeysById(id: Int): List<Int>
}


@Entity(tableName = "kanjiKey", ignoredColumns = ["rootId"])
data class KanjiKey(
    @ColumnInfo(name = "key") val hieroglyph: String
):Item(R.layout.lib_item){
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
interface KanjiKeyDao:DaoBase<KanjiKey> {
    @Query("SELECT * FROM kanjiKey")
    fun getAll(): List<KanjiKey>

    @Query("SELECT * FROM kanjiKey WHERE id IN (:kanjiIds)")
    fun loadAllByIds(kanjiIds: IntArray): List<KanjiKey>

    @Query("SELECT * FROM kanjiKey WHERE id = (:id)")
    fun findById(id: Int): KanjiKey
}
