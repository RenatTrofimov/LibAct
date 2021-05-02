package com.example.libact.modelsview

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.libact.App
import com.example.libact.Kanji
import com.example.libact.surface.Tree
import com.example.libact.views.DetailsFragment
import com.example.libact.views.LibFragment
import com.example.libact.views.LibraryFragment
import com.example.libact.views.MainActivity


class LibViewModel: ViewModel() {
    //Views
    private lateinit var libView: LibFragment
    private lateinit var detailsView: DetailsFragment
    private lateinit var libraryFragment: LibraryFragment
    var isLand = false
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
        if(isLand){
            detailsView.bind()
        }else{
            libraryFragment.openDetailsFragment(detailsView)
        }
    }
    fun setViews(lib: LibraryFragment, detailsFragment: DetailsFragment, libFragment: LibFragment){
        libView = libFragment;
        detailsView = detailsFragment
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