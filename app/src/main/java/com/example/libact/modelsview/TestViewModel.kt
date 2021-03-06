package com.example.libact.modelsview

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import com.example.libact.*
import com.example.libact.surface.TestCase
import com.example.libact.views.TestActivity
import kotlin.collections.ArrayList
import kotlin.random.Random


class TestViewModel(): ViewModel() {
    @SuppressLint("StaticFieldLeak")
    lateinit var activity: TestActivity
    private lateinit var question:Question
    private val variantsOfAnswer = ArrayList<KanjiKey>()
    var currentPosition = 0
    val example = TestCase()
    private var repeats = 1
    private var testListId = 1

    private fun ManyTestManyQuestion.sum():Int{
        return kunCheck + onCheck + transCheck
    }
    private lateinit var testList:List<ManyTestManyQuestion>
    fun reSetQuestion(){
        activity.setQuestion(question.title)
    }
    fun setQuestion(){
        example.clean()
        val anotherAnswer = mutableListOf<Int>()
        do{
            currentPosition = Random.nextInt(0, testList.size)
            if(testList[currentPosition].sum() == repeats*3)
                if(setTestList(testListId, testList.size))
                    return
        }while(testList[currentPosition].sum() >= repeats*3)
        val testCase = testList[currentPosition]

        val trueAnswer = App.getDB().keyIdDao().getKeysById(testCase.kanji_id)

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
        val tempKanji = App.getDB().kanjiDao().findById(testList[currentPosition].kanji_id)
        example.kanji = tempKanji.hieroglyph
        do{
            val type = Random.nextInt(0, 3)

            val title = when(type){
                0 ->{
                    tempKanji.kun
                }
                1 ->{
                    tempKanji.on
                }
                2 ->{
                    tempKanji.translation
                }
                else -> {
                    ""
                }
            }
            question = Question(title, trueAnswer, type)

        }while(when(type){
                0 ->{
                    testCase.kunCheck
                }
                1 ->{
                    testCase.onCheck
                }
                2 ->{
                    testCase.transCheck
                }
                else -> {
                    repeats
                }} >=  repeats)
                activity.setQuestion(question.title)
    }

    fun check(){
       if( question.checkRightAnswer(example.getIdList())
           &&
           example.check()){
               val testCase =  testList[currentPosition]
               when(question.typeQuestion){
                   0 ->{
                       testCase.kunCheck++
                   }
                   1 ->{
                       testCase.onCheck++
                   }
                   2 ->{
                       testCase.transCheck++
                   }
               }
           App.getDB().manyTestManyQuestionDao().update(testCase)
       }
        setQuestion()
    }

    fun setTestList(test_id:Int, window:Int):Boolean{
        testListId = test_id
        testList = App.getDB().manyTestManyQuestionDao().getKeysByIdLimitedBy(testListId, window)
        if(testList.isEmpty()){
            warring()
        }
        return testList.isEmpty()
    }
    fun warring(){
        val temp = DialogMessage(activity, "????????????????????????????", "???????? ????????????????, ???????????? ?????????????")
        temp.negativeAction { activity.finish() }
        temp.positiveAction {  }
        temp.show()
    }
    fun returnList():ArrayList<KanjiKey>{
        return variantsOfAnswer
    }
    class Question(
        val title: String,
        private val rightAnswer: List<Int>,
        val typeQuestion: Int
    ){
        fun checkRightAnswer(answer:List<Int>):Boolean{
            return (answer.containsAll(rightAnswer))
        }
    }
}