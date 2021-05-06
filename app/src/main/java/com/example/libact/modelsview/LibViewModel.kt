package com.example.libact.modelsview

import android.content.Context
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.example.libact.App
import com.example.libact.Kanji
import com.example.libact.R
import com.example.libact.surface.Tree
import com.example.libact.views.*
import kotlinx.android.synthetic.main.library_fragment.view.*

abstract class BaseViewModel<T:Fragment>(): ViewModel() {
    private lateinit var fragment:T
}
abstract class BaseFragment<T:ViewModel>(id:Int):Fragment(id){
    private lateinit var viewModel: T
    init{
        viewModel = viewModel.javaClass.newInstance()
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel = ViewModelProviders.of(requireActivity()).get(viewModel.javaClass)
    }
}

class LibViewModel: ViewModel() {
    //Views
    private lateinit var libraryFragment:LibraryFragment
    var isLand = false
    //???
    var tree = Tree<String>("")
    var kanjiList = ArrayList<Kanji>()
    var selectedKanji:Kanji

    init{
        kanjiList = getList()
        selectedKanji = kanjiList[0]
        setTree()
        Log.i("LibViewModel", "LibViewModel created!")
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("LibViewModel", "LibViewModel destroyed!")
    }
    fun sendDetails(kanji:Kanji){
        selectedKanji = kanji
        setTree()
        if(isLand){
            libraryFragment.bind()
        }else{
            libraryFragment.setFragment(R.id.main_lib_container, libraryFragment.getDetailsView())
        }
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

    fun setViews(libraryFragment: LibraryFragment) {
        this.libraryFragment = libraryFragment
    }
}