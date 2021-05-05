package com.example.libact

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.example.libact.DB.Test
import com.example.libact.lib_recycler_view.ItemAdapter
import com.example.libact.modelsview.LibViewModel
import kotlinx.android.synthetic.main.fragment_test_list.*
import kotlinx.android.synthetic.main.lib_kanji_fragment.*
import java.util.ArrayList

/**
 * A fragment representing a list of Items.
 */
interface Actions<T>{
    fun onLongClick(item: T):Boolean{
        return true
    }
    fun onClick(item: T)
}
class TestList : Fragment(R.layout.fragment_test_list),Actions<Test> {

    lateinit var viewModel: TestListVM
    private lateinit var adapter:ItemAdapter<Test>
    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel = ViewModelProviders.of(requireActivity()).get(TestListVM::class.java)
    }
    override fun onClick(item:Test){
        Log.i("I","onClick")
        val intent = Intent(requireActivity(), TestActivity::class.java).apply {
            putExtra("ID", item.id)
        }
        startActivity(intent)
    }
    override fun onLongClick(item: Test): Boolean {
        Log.i("I","onLongClick")
        return true
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = ItemAdapter(viewModel.testList,this)
        rv_test_list.adapter = adapter
    }
}
class TestListVM: ViewModel(){
    var testList: ArrayList<Test> = ArrayList(App.getDB().testDao().getAll())
}