package com.example.libact.modelsview

import androidx.lifecycle.ViewModel
import com.example.libact.Kanji
import com.example.libact.surface.TestCase
import com.example.libact.surface.SurfaceFragment

class TestViewModel(): ViewModel() {
    lateinit var fragment:SurfaceFragment
    val example = TestCase()

    fun returnList():ArrayList<Kanji>{
        val kanjiList: ArrayList<Kanji> = ArrayList<Kanji>()
        kanjiList.add(Kanji("糹", "イチ, イツ", "ひと(つ)", "один, ひと(つ) тж. один год (о возрасте)"))
        kanjiList.add(Kanji("氏", "シチ", "なな, なな(つ), なの", "семь"))
        kanjiList.add(Kanji("万", "マン ,バン", "よろず", "десять тысяч"))
        kanjiList.add(Kanji("三", "サン", "み, み(つ), み(っつ)", "три"))
        kanjiList.add(Kanji("上", "ジョウ ", "うえ - верх, старший, あ(がる) - подниматься, повышаться", "верх, подниматься"))
        kanjiList.add(Kanji("下", "カ, ゲ", "した ", "низ, падать, опускаться"))
        return kanjiList
    }
}