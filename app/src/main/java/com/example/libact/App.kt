package com.example.libact

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import javax.security.auth.callback.Callback

class App: Application() {
    private lateinit var instance:App
    private lateinit var database: AppDatabase
    override fun onCreate() {
        super.onCreate()
        instance = this
        database = Room.databaseBuilder(
            this,
            AppDatabase::class.java,
            "database").fallbackToDestructiveMigration()
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    database.userDao().insertItem(KanjiTable(null,"一", "イチ, イツ", "ひと-つ", "один, ひと(つ) тж. один год (о возрасте)"))
                    kanjiList.add()
                    kanjiList.add(Kanji("七", "シチ", "なな, なな(つ), なの", "семь"))
                    kanjiList.add(Kanji("万", "マン ,バン", "よろず", "десять тысяч"))
                    kanjiList.add(Kanji("三", "サン", "み, み(つ), み(っつ)", "три"))
                    kanjiList.add(Kanji("上", "ジョウ ", "うえ - верх, старший, あ(がる) - подниматься, повышаться", "верх, подниматься"))
                    kanjiList.add(Kanji("下", "カ, ゲ", "した ", "низ, падать, опускаться"))
                    kanjiList.add(Kanji("中", "チュウ", "なか, うち", "середина, внутренняя часть, внутри"))
                    kanjiList.add(Kanji("九", "キュウ, ク", "ここの(つ)", "девять"))
                    kanjiList.add(Kanji("二", "ニ ,ジ", "ふた, ふた(つ )", "два"))
                    kanjiList.add(Kanji("五", "ゴ", "いつ, いつ(つ)", "пять"))
                    kanjiList.add(Kanji("人", "ジン, ニン", "ひと", "человек"))
                    kanjiList.add(Kanji("今", "コン, キン", "いま", "сейчас"))
                    kanjiList.add(Kanji("休", "キュウ", "やす(む) ", "отдыхать, пропускать (занятия)"))
                    kanjiList.add(Kanji("会", "カイ ", "あ(う)", "встречаться, встреча, собрание"))
                    kanjiList.add(Kanji("住", "ジュウ, ヂュウ, チュウ", "す(む)", "жить, проживать где-л"))

                }
            })

            .build()
    }
    fun getInstance():App{
        return instance
    }
    fun getDB():AppDatabase{
        return database
    }
}