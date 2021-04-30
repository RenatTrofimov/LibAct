package com.example.libact.modelsview

import androidx.lifecycle.ViewModel
import com.example.libact.App
import com.example.libact.Kanji
import com.example.libact.KanjiKey
import com.example.libact.ManyTestManyQuestion
import com.example.libact.surface.TestCase
import com.example.libact.surface.SurfaceFragment
import java.util.*
import kotlin.collections.ArrayList
import kotlin.random.Random
import kotlin.random.nextInt



class TestViewModel(): ViewModel() {
    lateinit var fragment:SurfaceFragment
    private lateinit var question:Question
    private val variantsOfAnswer = ArrayList<KanjiKey>()
    private var currentPosition = 0
    val example = TestCase()

    lateinit var testList:List<ManyTestManyQuestion>
    init{

    }
    fun setQuestion(){
        var anotherAnswer = mutableListOf<Int>()
        val trueAnswer = App.getDB().keyIdDao().getKeysById(testList[currentPosition].kanji_id)

        for(e in 0 until (6 - trueAnswer.size)){
            var temp = 0
            do{
                val num = Random.nextInt(1, 314)
                temp = num
            }
            while(trueAnswer.contains(num))
            anotherAnswer.add(temp)
        }
        val variantsOfAnswer = listOf(anotherAnswer, trueAnswer).flatten()
        this.variantsOfAnswer.clear()
        for(item in variantsOfAnswer){
            this.variantsOfAnswer.add(App.getDB().kanjiKeyDao().findById(item))
        }
        val type = Random.nextInt(0, 3)
        val tempKanji = App.getDB().kanjiDao().findById(testList[currentPosition].kanji_id)
        example.kanji = tempKanji.hieroglyph
        question = when(type){
            0 ->{
                Question(tempKanji.kun, trueAnswer)
            }
            1 ->{
                Question(tempKanji.on, trueAnswer)
            }
            2 ->{
                Question(tempKanji.translation, trueAnswer)
            }
            else -> {
                Question("", trueAnswer)
            }
        }

    }
    fun setTestList(test_id:Int){
        testList = App.getDB().manyTestManyQuestionDao().getKeysById(test_id)
        for(item in testList){
            if(item.kunCheck + item.onCheck + item.transCheck <3){
                break
            }
            currentPosition++
        }
    }
    fun returnList():ArrayList<KanjiKey>{
        return variantsOfAnswer
    }
    class Question(
        private val title: String,
        private val rightAnswer: List<Int>
    ){
        fun checkRightAnswer(answer:List<Int>):Boolean{
            return (answer.containsAll(rightAnswer) && rightAnswer.containsAll(answer))
        }
        fun get() = title
    }
}