package com.example.libact.DB

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.libact.*

@Database(entities = [  Kanji::class,
                        KanjiKey::class,
                        KeyId::class,
                        ManyTestManyQuestion::class,
                        Test::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun kanjiDao(): KanjiDao
    abstract fun kanjiKeyDao(): KanjiKeyDao
    abstract fun keyIdDao(): KeyIdDao
    abstract fun manyTestManyQuestionDao(): ManyTestManyQuestionDao
    abstract fun testDao(): TestDao
}