package com.example.libact.modelsview

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.libact.App
import com.example.libact.Kanji
import com.example.libact.views.DetailsFragment
import com.example.libact.views.LibFragment
import com.example.libact.views.MainActivity


class LibViewModel: ViewModel() {
    //Views
    lateinit var libView: LibFragment
    lateinit var detailsView: DetailsFragment
    lateinit var activity: MainActivity
    //???
    var kanjiList = ArrayList<Kanji>()
    var translateList = ArrayList<String>()
    var selectedKanji:Kanji

    fun getLis() = translateList

    fun sendDetails(){
        
    }
    private fun setList(){
        translateList.add(kanjiList[0].translation)
    }
    fun setViews(activity: MainActivity, detailsFragment: DetailsFragment, libFragment: LibFragment){
        libView = libFragment;
        detailsView = detailsFragment;
        this.activity = activity;
    }
    private fun createList():ArrayList<Kanji>{
        val kanjiList: ArrayList<Kanji> = ArrayList<Kanji>()
        kanjiList.add(Kanji("一", "イチ, イツ", "ひと(つ)", "один, ひと(つ) тж. один год (о возрасте)"))
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
        kanjiList.add(Kanji("", "", "", ""))

        return kanjiList
    }
    init{
        kanjiList = createList()
        selectedKanji = kanjiList[0]
        setList()
    }
}