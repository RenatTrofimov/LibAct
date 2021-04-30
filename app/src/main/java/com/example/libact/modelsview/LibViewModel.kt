package com.example.libact.modelsview

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.libact.App
import com.example.libact.Kanji
import com.example.libact.surface.Tree
import com.example.libact.views.DetailsFragment
import com.example.libact.views.LibFragment
import com.example.libact.views.MainActivity


class LibViewModel: ViewModel() {
    //Views
    private lateinit var libView: LibFragment
    private lateinit var detailsView: DetailsFragment
    private lateinit var activity: MainActivity
    //???
    var tree = Tree<String>("")
    var kanjiList = ArrayList<Kanji>()
    var selectedKanji:Kanji

    override fun onCleared() {
        super.onCleared()
        Log.i("LibViewModel", "LibViewModel destroyed!")
    }
    fun sendDetails(kanji:Kanji){
        selectedKanji = kanji
        setTree()
        if(activity.isLandOrientation()){
            detailsView.bind()
        }else{
            activity.openDetailsFragment(detailsView)
        }
    }
    fun setViews(activity: MainActivity, detailsFragment: DetailsFragment, libFragment: LibFragment){
        libView = libFragment;
        detailsView = detailsFragment
        this.activity = activity
    }
    private fun getList():ArrayList<Kanji>{
        return ArrayList(App.getDB().kanjiDao().getAll())
    }
    private fun setTree(){
        val listId = App.getDB().keyIdDao().getKeysById(selectedKanji.id)
        tree = Tree(selectedKanji.hieroglyph)
        for(e in listId){
            tree.addChild(App.getDB().kanjiKeyDao().findById(e).hieroglyph)
        }
    }
    init{
        kanjiList = getList()
        selectedKanji = kanjiList[0]
        setTree()
        Log.i("LibViewModel", "LibViewModel created!")
    }
}