package com.example.libact.surface

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
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

        val tree = Tree<String>("0")
        tree.addChild("1")
        tree.addChild("2")
        tree.addChild("3")
        thread = DrawThread(surfaceHolder)
        thread?.sendTree(tree)
        thread?.setRunning(true)
        thread?.start()
        setOnClickListener{
            tree.addChild("3")
            var lock = true
            while (lock){
                try{
                    thread?.sendTree(tree)
                    lock = false
                }catch (e: ConcurrentModificationException){

                }
            }

        }
       /* val canvas = surfaceHolder.lockCanvas(null)

        canvas.drawKanji("下",0f,0f, 200f, 200f)

        surfaceHolder.unlockCanvasAndPost(canvas)*/
        Log.i("SV", "surfaceCreated")

    }

    fun sendTree(tree: Tree<String>){
        thread?.sendTree(tree)
    }
}

class DrawThread(private val surfaceHolder: SurfaceHolder):Thread(){
    private var runFlag:Boolean = false
    private var beChange:Boolean = true
    private var prevTime: Long = 0
    private var canvas: Canvas? = null
    private var paint: Paint
    private var buffer: Bitmap? = null
    private var tree: Tree<String>
    private val alphabetTestText = "溜めを経て繰り出される技を「溜め技」と呼ぶ。 為. 読み方：ため別表記：タメ · 別の人や物事にとっての利益を意味する語。役に立つこと".toCharArray()
    init{
        prevTime = System.currentTimeMillis()
        paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.color = Color.BLACK
        tree = Tree<String>("0")

    }

    override fun run() {
        super.run()
        while (runFlag){
            /*val now = System.currentTimeMillis()
            val elapsedTime = now - prevTime

            if(elapsedTime>320){

                prevTime = now
            }*/
                if(beChange){
                    beChange = false
                    canvas = null
                    try{
                        canvas = surfaceHolder.lockCanvas(null)
                    }finally {
                        if(canvas!=null){
                            canvas!!.draw(tree)
                            surfaceHolder.unlockCanvasAndPost(canvas)
                        }
                    }
                }

        }
    }
    fun setRunning(run: Boolean) {
        runFlag = run
    }
    fun sendTree(tree: Tree<String>){
        beChange = true
        this.tree = tree
    }
}
fun Canvas.draw(tree: Tree<String>){
    drawColor(Color.WHITE)
    drawRoot(tree)
}
fun Canvas.drawKanji(text:String, cx:Float, cy:Float, width: Float, height: Float){
    val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    val radius = if (height>width){
        width/2
    } else{
        height/2
    }
    paint.isAntiAlias = true
    paint.textSize = radius*2*0.8f
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
fun Canvas.drawRoot(tree:Tree<String>){
    tree.setCords(width.toFloat(), height.toFloat())
    tree.drawAll(this)
}
///Мои пытки в деревья, на будущее
class Tree<T>(rootData:T){
    private var root = Node<T>()
    private var maxDepth:Int = 1
    private val points:ArrayList<PointF>
    init{
        root.data = rootData
        root.children = ArrayList<Node<T>>()
        points = ArrayList<PointF>()
    }
    fun getChildNum():Int{
        return root.children!!.size
    }
    fun addChild(data:T){
        val child = Node<T>()
        child.data = data
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
        points.clear()
        if(root.children!!.isNotEmpty()){
            val list = ArrayList<Int>()

            root.depth = maxDepth
            root.children!!.forEach {
                points.add(PointF(root.floatX, root.floatY))
                list.add(getNextRootLevel(maxDepth+1, it))
                points.add(PointF(root.floatX, (root.floatY+it.floatY)/2))
                points.add(PointF(it.floatX, (root.floatY+it.floatY)/2))
                points.add(PointF(it.floatX, it.floatY))
                points.add(PointF(it.floatX, (root.floatY+it.floatY)/2))
                points.add(PointF(root.floatX, (root.floatY+it.floatY)/2))

            }
            maxDepth = list.max()!!
        }
        return maxDepth
    }

    private fun getNextRootLevel(level:Int, children:Node<T>):Int{
        if(children.children != null && children.children!!.isNotEmpty()){
            val list = ArrayList<Int>()
            children.depth = level
            children.children!!.forEach {
                list.add(getNextRootLevel(level+1, it))
            }
        }
        return level
    }
    fun setCords(weight: Float, height: Float){
        root.floatX = weight/2
        val step = 1f/getChildNum()
        var k = 0f
        root.children!!.forEach {
            it.floatX = weight*k
            it.floatY = height/2
            k+=step
        }
    }

    fun drawAll(canvas: Canvas){
        val width = canvas.width/(getChildNum()).toFloat()
        val height = canvas.height/(getMaxRootLevel()).toFloat()
        canvas.drawKanji(root.data.toString(), root.floatX, root.floatY,width, height)
        root.children!!.forEach {
            canvas.drawKanji(it.data.toString(), it.floatX, it.floatY,width, height)
        }
        val paintLine = Paint()
        paintLine.color = Color.BLACK
        for (i in 0..points.size-2)
            canvas.drawLine(points[i].x, points[i].y, points[i+1].x, points[i+1].y, paintLine)
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