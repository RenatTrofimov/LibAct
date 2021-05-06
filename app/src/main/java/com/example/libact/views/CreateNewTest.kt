package com.example.libact

import android.content.Context
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.example.libact.DB.Test
import com.example.libact.lib_recycler_view.ItemAdapter
import com.example.libact.views.LibFragment
import kotlinx.android.synthetic.main.activity_create_new_test.*
import kotlinx.android.synthetic.main.lib_kanji_fragment.*
import kotlinx.coroutines.*
import java.util.*


class CreateNewTest : AppCompatActivity() {
    private var testName:String = ""
    private val testNameString = "testName"
    private var createFinish:Boolean = true
    private val createFinishString = "createFinish"
    private lateinit var createListVM:CreateListVM
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createListVM = ViewModelProviders.of(this).get(CreateListVM::class.java)
        setContentView(R.layout.activity_create_new_test)
        if(testName.isNotEmpty()){
            editText.setText(testName)
        }
        create_test_btn.setOnClickListener{
            testName = editText.text.toString()
            if(testName.isNotEmpty()){
                if(createListVM.getList().isNotEmpty()){
                    GlobalScope.launch{
                        shit()
                    }
                    DialogMessage(this, "Создание теста", "Создание теста...").show()
                }else{
                    DialogMessage(this, "Предупреждение", "В тесте должен быть хотя бы один вопрос!").show()
                }
            }else{
                DialogMessage(this, "Предупреждение", "Поле имени теста должно быть заполнено!").show()
            }

        }
    }

    private suspend fun shit(){
        val temp = createNewTestAsync()
        if(temp.await()){
            delay(1000L)
            this.finish()
        }
    }
    private fun createNewTestAsync() =  GlobalScope.async(Dispatchers.IO) {
        App.getDB().testDao().createTest(createListVM.getList(), Test(testName,""))
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        testName = editText.text.toString()

        outState.putString(testNameString, testName)
        outState.putBoolean(createFinishString, createFinish)
    }
}
class CreateListVM:ViewModel(){
    lateinit var selectedItemsFragment: SelectedItemsFragment
    fun add(item: Kanji) {
        if(!selectedItems.contains(item)){
            selectedItems.add(item)
            update()
        }
    }
    fun delete(item: Kanji) {
        if(selectedItems.remove(item)){
            update()
        }
    }
    fun set(selectedItemsFragmentForFun: SelectedItemsFragment){
        selectedItemsFragment = selectedItemsFragmentForFun
    }
    fun update(){
        selectedItemsFragment.setRecyclerView()
    }
    fun getList() = selectedItems
    private val selectedItems = ArrayList<Kanji>()
}
open class SelectableFragment: LibFragment(){
    var createListVM = CreateListVM()
    override fun onAttach(context: Context) {
        super.onAttach(context)
        createListVM = ViewModelProviders.of(requireActivity()).get(createListVM.javaClass)
    }
    override fun onClick(item: Kanji) {
        createListVM.add(item)
    }
}
class SelectedItemsFragment: SelectableFragment(){
    override fun setRecyclerView() {
        adapter = ItemAdapter(createListVM.getList(), this)
        fragment_kanji_rv.adapter = adapter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createListVM.set(this)
    }
    override fun onClick(item: Kanji) {
        createListVM.delete(item)
    }
}
