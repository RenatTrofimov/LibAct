package com.example.libact.modelsview

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.libact.Kanji


class LibViewModel: ViewModel() {
    var kanjiList = ArrayList<Kanji>()
    var translateList = ArrayList<String>()
    lateinit var selectedKanji:Kanji
    fun getLis() = translateList
    private fun setList(){
        translateList.add( "00) translate example  translate example  translate example  translate example  translate example  translate example  translate example  translate example ")
    }

    private fun createList():ArrayList<Kanji>{
        val kanjiList: ArrayList<Kanji> = ArrayList<Kanji>();
        kanjiList.add(Kanji("恋", "こい", "アイ", "love"))
        kanjiList.add(Kanji("一", "こい", "アイ", "love"))
        kanjiList.add(Kanji("二", "こい", "アイ", "love"))
        kanjiList.add(Kanji("世", "こい", "アイ", "love"))
        kanjiList.add(Kanji("遊", "こい", "アイ", "love"))

        return kanjiList
    }
    init{
        kanjiList = createList()
        selectedKanji = kanjiList[0]
        setList()
    }
}