package com.example.libact.modelsview

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.libact.Kanji

class LibViewModel: ViewModel() {
    var kanjiList = ArrayList<Kanji>()
    var translateList = ArrayList<String>()

    fun getLis() = translateList
    private fun setList(){
        translateList.add( "00) translate example  translate example  translate example  translate example  translate example  translate example  translate example  translate example ")
    }

    private fun createList():ArrayList<Kanji>{
        val kanjiList: ArrayList<Kanji> = ArrayList<Kanji>();
        kanjiList.add(Kanji("恋", "こい", "アイ", "love"))
        kanjiList.add(Kanji("恋", "こい", "アイ", "love"))
        kanjiList.add(Kanji("恋", "こい", "アイ", "love"))
        kanjiList.add(Kanji("恋", "こい", "アイ", "love"))
        kanjiList.add(Kanji("恋", "こい", "アイ", "love"))
        kanjiList.add(Kanji("恋", "こい", "アイ", "love"))
        kanjiList.add(Kanji("恋", "こい", "アイ", "love"))
        kanjiList.add(Kanji("恋", "こい", "アイ", "love"))
        kanjiList.add(Kanji("恋", "こい", "アイ", "love"))
        return kanjiList
    }
    init{
        kanjiList = createList()
        setList()
    }
}