package com.example.libact.DB

import android.view.View
import android.widget.TextView
import androidx.room.*
import com.example.libact.BaseDao
import com.example.libact.Item
import com.example.libact.R

@Entity(tableName = "test", ignoredColumns = ["rootId"])
data class Test(
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "count") val count: String
): Item(R.layout.test_list_item) {
    @PrimaryKey(autoGenerate = true) var id: Int = 0
    override fun toString(): String {
        return name
    }
    override fun bind(v: View) {
        val testName: TextView = v.findViewById(R.id.test_name)
        val testCount: TextView = v.findViewById(R.id.numberQuestion)
        testName.text = name
        testCount.text = count
    }
}
@Dao
interface TestDao: BaseDao<Test> {
    @Query("SELECT * FROM test")
    fun getAll(): List<Test>

    @Query("SELECT * FROM test WHERE id = (:id)")
    fun findById(id: Int): Test
}