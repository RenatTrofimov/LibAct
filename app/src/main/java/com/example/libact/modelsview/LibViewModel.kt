package com.example.libact.modelsview

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.libact.App
import com.example.libact.Kanji
import com.example.libact.views.DetailsFragment
import com.example.libact.views.LibFragment
import com.example.libact.views.MainActivity


class LibViewModel: ViewModel() {
    //Views
    private lateinit var libView: LibFragment
    private lateinit var detailsView: DetailsFragment
    private lateinit var activity: MainActivity
    //???
    var kanjiList = ArrayList<Kanji>()
    var selectedKanji:Kanji

    override fun onCleared() {
        super.onCleared()
        Log.i("LibViewModel", "LibViewModel destroyed!")
    }
    fun sendDetails(kanji:Kanji){
        selectedKanji = kanji
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
    init{
        kanjiList = getList()
        selectedKanji = kanjiList[0]
        Log.i("LibViewModel", "LibViewModel created!")
    }
}