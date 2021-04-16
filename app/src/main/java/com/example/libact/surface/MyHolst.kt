package com.example.libact.surface

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.util.AttributeSet
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import java.util.*
import kotlin.collections.ArrayList
interface OnDrawListener{

    fun draw(canvas:Canvas)

    fun getListener():OnDrawListener{
        return this
    }
}

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
        initTread()
        Log.i("SV", "surfaceCreated")
    }
    private fun initTread(){
        val tree = Tree<String>("0")
        tree.addChild("1")
        tree.addChild("2")
        tree.addChild("3")
        val example = Example()

        val sendThing = example

        thread = DrawThread(surfaceHolder)
        thread?.send(sendThing)
        thread?.setRunning(true)
        thread?.start()

        setOnClickListener{
            thread?.send(sendThing)
        }
    }
    fun getCanvas():Canvas?{
        return surfaceHolder.lockCanvas(null)
    }
    fun setCanvas(canvas: Canvas){
        surfaceHolder.unlockCanvasAndPost(canvas)
    }
    fun <T:OnDrawListener>send(thing: T){
        thread?.send(thing)
    }
}

class DrawThread(private val surfaceHolder: SurfaceHolder):Thread() {
    private var runFlag:Boolean = false
    private var beChange:Boolean = true
    private var canvas: Canvas? = null
    private var onDrawListener:OnDrawListener? = null
    private var paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    init{
        paint.color = Color.BLACK
    }
    override fun run() {
        super.run()
        while (runFlag){
            if(beChange){
                beChange = false
                canvas = null
                try{
                    canvas = surfaceHolder.lockCanvas(null)
                }finally {
                    if(canvas!=null){
                        onDrawListener?.draw(canvas!!)
                        surfaceHolder.unlockCanvasAndPost(canvas)
                    }
                }
            }
        }
    }
    fun setRunning(run: Boolean) {
        runFlag = run
    }
    fun <T:OnDrawListener>send(thing: T){
        beChange = true
        onDrawListener = thing.getListener()
    }
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

class Example:OnDrawListener {

    override fun draw(canvas: Canvas) {
        canvas.drawColor(Color.WHITE)
        val paint = Paint()
        paint.color = Color.BLACK
        paint.textSize = canvas.height*0.8f
        canvas.drawText("1", 0f, canvas.height*0.8f, paint)
    }

}
///Мои пытки в деревья, на будущее



class Tree<T>(rootData:T):OnDrawListener{
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
                setPath(it)
                list.add(getNextRootLevel(maxDepth+1, it))
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
    private fun setPath(it:Node<T>){
        points.add(PointF(root.floatX + root.floatX/2, root.floatY + root.floatX/2 ))
        points.add(PointF(root.floatX + root.floatX/2, (root.floatY+it.floatY)/2 + root.floatX/2))
        points.add(PointF(it.floatX + root.floatX/2, (root.floatY+it.floatY)/2 + root.floatX/2))
        points.add(PointF(it.floatX + root.floatX/2, it.floatY))
        points.add(PointF(it.floatX + root.floatX/2, (root.floatY+it.floatY )/2 + root.floatX/2))
        points.add(PointF(root.floatX + root.floatX/2, (root.floatY+it.floatY)/2 + root.floatX/2))
    }
    fun setCords(weight: Float, height: Float){
        root.floatX = weight/2f - weight/getChildNum()/2
        val step = 1f/getChildNum()
        var k = 0f
        root.children!!.forEach {
            it.floatX = weight*k
            it.floatY = height/2
            k+=step
        }
    }

    override fun draw(canvas: Canvas) {
        canvas.drawColor(Color.WHITE)
        setCords(canvas.width.toFloat(), canvas.height.toFloat())
        val width = canvas.width/(getChildNum()).toFloat()
        val height = canvas.height/2f
        getMaxRootLevel()
        val paintLine = Paint(Color.BLACK)
        paintLine.strokeWidth = 15f
        for (i in 0..points.size-2)
            canvas.drawLine(points[i].x, points[i].y, points[i+1].x, points[i+1].y, paintLine)
        canvas.drawKanji(root.data.toString(), root.floatX, root.floatY,width, height)
        root.children!!.forEach {
            canvas.drawKanji(it.data.toString(), it.floatX, it.floatY,width, height)
        }
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