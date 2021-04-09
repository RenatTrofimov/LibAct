package com.example.libact.surface

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PixelFormat
import android.util.AttributeSet
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.example.libact.Kanji
import kotlinx.coroutines.NonCancellable.children
import java.util.*
import kotlin.collections.ArrayList


class MyHolst(context: Context, attributeSet: AttributeSet): SurfaceView(context, attributeSet), SurfaceHolder.Callback{
    private var thread:DrawThread? = null

    private val surfaceHolder: SurfaceHolder = holder
    init{
        this.surfaceHolder.addCallback(this)
        surfaceHolder.setFormat(PixelFormat.TRANSPARENT)
    }

    override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {

    }

    override fun surfaceDestroyed(holder: SurfaceHolder?) {

        thread?.setRunning(false)
        var lock = true
        while (lock){
            try{
                thread?.join()
                lock = false
            }catch (e: InterruptedException){

            }
        }
        Log.i("SV", "surfaceDestroyed")

    }

    override fun surfaceCreated(holder: SurfaceHolder?) {
        thread = DrawThread(surfaceHolder)
        thread?.setRunning(true)
        thread?.start()
       /* val canvas = surfaceHolder.lockCanvas(null)

        canvas.drawKanji("下",0f,0f, 200f, 200f)

        surfaceHolder.unlockCanvasAndPost(canvas)*/
        Log.i("SV", "surfaceCreated")

    }
}

class DrawThread(private val surfaceHolder: SurfaceHolder):Thread(){
    private var runFlag:Boolean = false
    private var prevTime: Long = 0
    val alphabet = "溜めを経て繰り出される技を「溜め技」と呼ぶ。 為. 読み方：ため別表記：タメ · 別の人や物事にとっての利益を意味する語。役に立つこと".toCharArray()
    init{
        prevTime = System.currentTimeMillis()
    }

    override fun run() {
        super.run()
        var canvas: Canvas?
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        var intCount:Int=0
        paint.color = Color.BLACK
        while (runFlag){
            val now = System.currentTimeMillis()
            val elapsedTime = now - prevTime

            if(elapsedTime>160){
                if(alphabet.size-1>intCount)
                    intCount++
                else{
                    intCount = 0
                }
                prevTime = now
            }
            canvas = null
            try{
                canvas = surfaceHolder.lockCanvas(null)
            }finally {
                if(canvas!=null){
                    paint.setTextSize(intCount.toFloat())
                    canvas.drawColor(Color.WHITE)



                    //canvas.drawKanji(alphabet[intCount].toString(),0f,0f,canvas.width.toFloat(),canvas.height.toFloat())
                    surfaceHolder.unlockCanvasAndPost(canvas)
                }
            }
        }
    }

    fun setRunning(run: Boolean) {
        runFlag = run
    }
    fun draw(canvas: Canvas, paint: Paint): Canvas {

        return canvas
    }
}
fun Canvas.drawRoot(list:ArrayList<String>){

    val width = width/(list.size - 1)
    val height = height/2

}
fun Canvas.drawKanji(text:String, cx:Float, cy:Float, width: Float, height: Float){
    val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    val radius = if (height>width){
        width/2
    } else{
        height/2
    }
    paint.isAntiAlias = true
    paint.textSize = height*0.8f
    paint.color = Color.RED

    drawCircle(cx+radius, cy+radius, radius, paint)
    paint.color = Color.BLACK
    paint.alpha = 126
    drawText(text, cx+radius*2*0.11f, cy+radius*2*0.85f, paint)

    paint.color = Color.WHITE
    paint.alpha = 255
    drawText(text, cx+radius*2*0.1f, cy+radius*2*0.8f, paint)
    TreeMap<Int,Int>()

}
///Мои пытки в деревья, на будущее
class Tree<T>(rootData:T){
    private var root = Node<T>()
    private var maxDepth:Int = 1
    init{
        root.data = rootData
        root.children = ArrayList<Node<T>>()
    }
    fun addChild(child:Node<T>){
        if(root.children!!.isEmpty()){
            maxDepth++
        }
        child.parent = root
        root.children!!.add(child)
    }
    fun goDown(childNumber:Int){
        if(root.children != null){
            root = root.children!![childNumber-1]
        }
    }
    fun goUp(){
        if (root.parent != null) {
            root = root.parent!!
        }
    }
    fun getMaxRootLevel():Int{
        maxDepth = 0
        if(root.children!!.isNotEmpty()){
            val list = ArrayList<Int>()
            root.depth = maxDepth
            root.children!!.forEach {
                list.add(getNextRootLevel(maxDepth+1, it))
            }
        }
        return maxDepth
    }

    private fun getNextRootLevel(level:Int, children:Node<T>):Int{
        if(children.children!!.isNotEmpty()){
            val list = ArrayList<Int>()
            children.depth = level
            children.children!!.forEach {
                list.add(getNextRootLevel(level+1, it))
            }
        }
        return level
    }
}
class Node<T> {
    var depth = 0
    var floatX = 0f
    var floatY = 0f
    var data: T? = null
    var parent: Node<T>? = null
    var children: ArrayList<Node<T>>? = null
}