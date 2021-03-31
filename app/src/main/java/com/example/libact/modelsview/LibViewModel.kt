package com.example.libact.modelsview

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.room.Room
import androidx.room.Room.databaseBuilder
import com.example.libact.AppDatabase
import com.example.libact.Kanji

class LibViewModel(): ViewModel() {
    var kanjiList = ArrayList<Kanji>()
    lateinit var selectedKanji:Kanji
    lateinit var db: Room
    var translateList = ArrayList<String>()
    fun getLis() = translateList

    private fun setList(){
        translateList.add( "00) translate example  translate example  translate example  translate example  translate example  translate example  translate example  translate example ")
        translateList.add( "00) translate example  translate example  translate example  translate example  translate example  translate example  translate example  translate example ")
    }

    private fun createList():ArrayList<Kanji>{
        val kanjiList: ArrayList<Kanji> = ArrayList<Kanji>();
        kanjiList.add(Kanji("恋", "こい", "アイ", "love"))
        return kanjiList
    }
    init{
        kanjiList = createList()
        selectedKanji = kanjiList[0]
        setList()
    }
}