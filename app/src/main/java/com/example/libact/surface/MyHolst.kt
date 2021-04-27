package com.example.libact.surface

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import kotlinx.coroutines.*
import java.util.*
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.collections.ArrayList
import kotlin.math.abs
import kotlin.math.min


interface OnDrawListener{
    fun draw(canvas: Canvas)
    fun sendTouch(event: MotionEvent)
}
class MyHolstForTest(context: Context, attributeSet: AttributeSet): MyHolst<TestCase>(context, attributeSet){
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        thing!!.sendTouch(event!!)
        return super.onTouchEvent(event)
    }

    override fun surfaceCreated(holder: SurfaceHolder?) {
        super.surfaceCreated(holder)
        setOnClickListener{
            GlobalScope.async(Dispatchers.Default) {
                send(thing!!)
            }
        }
    }
}
open class MyHolst<T:OnDrawListener>(context: Context, attributeSet: AttributeSet): SurfaceView(context, attributeSet), SurfaceHolder.Callback{

    protected var thing:T? = null
    private val surfaceHolder: SurfaceHolder = holder
    private val isRunning = AtomicBoolean(false)
    init{
        Log.i("SV", "init")
        this.surfaceHolder.addCallback(this)
        surfaceHolder.setFormat(PixelFormat.TRANSPARENT)

    }
    override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {
        Log.i("SV", "surfaceChanged")
    }

    override fun surfaceDestroyed(holder: SurfaceHolder?) {
        isRunning.set(false)
        Log.i("SV", "surfaceDestroyed")
    }
    fun set(thing: T){
        isRunning.set(true)
        this.thing = thing
    }
    protected fun send(thing: T){
        var canvas:Canvas?=null
        try{
            canvas = surfaceHolder.lockCanvas(null)
        }finally {
            if(canvas!=null){
                thing.draw(canvas)
                surfaceHolder.unlockCanvasAndPost(canvas!!)
            }
        }
    }
    override fun surfaceCreated(holder: SurfaceHolder?) {
        GlobalScope.async(Dispatchers.Default) {
            while(isRunning.get())
                send(thing!!)
        }
    }

    fun getCanvas():Canvas?{
        return surfaceHolder.lockCanvas(null)

    }
    fun setCanvas(canvas: Canvas){
        surfaceHolder.unlockCanvasAndPost(canvas)
    }
}

fun Canvas.drawKanji(text: String, cx: Float, cy: Float, width: Float, height: Float){
    val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    val radius = if (height>width){
        width/2
    } else{
        height/2
    }
    paint.isAntiAlias = true
    paint.textSize = radius*2*0.8f
    paint.color = Color.RED

    drawCircle(cx + radius, cy + radius, radius, paint)
    paint.color = Color.BLACK
    paint.alpha = 126
    drawText(text, cx + radius * 2 * 0.11f, cy + radius * 2 * 0.85f, paint)

    paint.color = Color.WHITE
    paint.alpha = 255
    drawText(text, cx + radius * 2 * 0.1f, cy + radius * 2 * 0.8f, paint)
    TreeMap<Int, Int>()
}

class TestCase:OnDrawListener {
    private var draw = true
    private val en = ArrayList<Entity>()
    private var result:Bitmap? = null
    init{

    }
    fun add(str:String){
        en.add(Entity(str))
    }
    fun check(){
        var trueResult = Bitmap.createBitmap(result!!)
        val c = Canvas(trueResult)
        c.drawColor(Color.WHITE)
        c.drawBitmap(drawTextBitmap("紙", result!!.width,result!!.height), 0f,0f, Paint())
        trueResult = crop(Bitmap.createScaledBitmap(trueResult, 128,128, true))
        var varTempResult = crop(Bitmap.createScaledBitmap(result!!, 128,128, true))
        varTempResult = Bitmap.createScaledBitmap(varTempResult, trueResult.width, trueResult.height, true)
        var count = 0
        for(x in 0 until trueResult.width){
            for(y in 0 until trueResult.height){
                if(varTempResult.getPixel(x, y) == trueResult.getPixel(x, y)){
                    count++
                }
            }
        }
        Log.i("result", "${
            count*100/(trueResult.width*trueResult.height)
        }")
    }
    override fun draw(canvas: Canvas) {
        if(!draw)
            return
        if(result == null)
            result = Bitmap.createBitmap(canvas.width, canvas.height, Bitmap.Config.ARGB_8888)
        val tempCanvas = Canvas(result!!)
        tempCanvas.drawColor(Color.WHITE)
        for(en in en.iterator()){
            en.drawEntity(tempCanvas)
        }
        canvas.drawBitmap(result!!, 0f,0f, Paint())
    }

    override fun sendTouch(event: MotionEvent) {
        val point = PointF(event.x,event.y)
        when(event.action){
            MotionEvent.ACTION_DOWN -> {
                Log.i("SV", "down")
                for(en in en.iterator()){
                    en.isSelected = en.checkCollusion(point)
                    if( en.checkCollusion(point))
                        break
                }
            }
            MotionEvent.ACTION_MOVE -> {
                for(en in en.iterator()){
                    if(en.isSelected) {
                        if (event.pointerCount > 1) {
                            val secondTouch = MotionEvent.PointerCoords()
                            event.getPointerCoords(1, secondTouch)
                            en.reSize(
                                point,
                                PointF(
                                    secondTouch.x,
                                    secondTouch.y
                                )
                            )
                        } else {
                            en.setPosition(point)
                        }
                    }
                }
            }
            MotionEvent.ACTION_UP ->{
                Log.i("SV", "up")
                for(en in en.iterator()){
                    if(en.isSelected)
                        en.isSelected=false

                }
            }   
            MotionEvent.ACTION_POINTER_DOWN ->{
                Log.i("SV", "PD")
            }
            MotionEvent.ACTION_POINTER_UP ->{
                Log.i("SV", "PU")
//                for(en in en.iterator()){
//                    if(en.isSelected)
//                        en.setBitmap(crop(en.getBitmap()))
//                }
            }
            else -> {

            }
        }
    }

    fun clean() {
        en.clear()
    }


    class Entity(private val str: String){
        var isSelected:Boolean = false
        private val pointsAnchor = ArrayList<PointF>()
        private val points = ArrayList<Float>()
        private var mainBitmap:Bitmap

        private var position = PointF(0f, 0f)
        private var anchorRadius = 20f

        private var canvasHeight = 0f
        private var canvasWidth = 0f

        init{
            setBorder()
            mainBitmap = crop(drawTextBitmap(str,300,300))
        }
        private fun setBorder() {
            GlobalScope.async(Dispatchers.Default) {
                pointsAnchor.clear()
                pointsAnchor.add(position)
                pointsAnchor.add(PointF(position.x, position.y+mainBitmap.height.toFloat()))
                pointsAnchor.add(PointF(position.x+mainBitmap.width.toFloat(), position.y+ mainBitmap.height.toFloat()))
                pointsAnchor.add(PointF(position.x+mainBitmap.width.toFloat(), position.y))

                points.clear()
                points.add(position.x)
                points.add(position.y)
                points.add(position.x)
                points.add(position.y+mainBitmap.height.toFloat())

                points.add(position.x+mainBitmap.width.toFloat())
                points.add(position.y+mainBitmap.height.toFloat())
                points.add(position.x+mainBitmap.width.toFloat())
                points.add(position.y)

                points.add(position.x+mainBitmap.width.toFloat())
                points.add(position.y)
                points.add(position.x)
                points.add(position.y)

                points.add(position.x)
                points.add(position.y+mainBitmap.height.toFloat())
                points.add(position.x+mainBitmap.width.toFloat())
                points.add(position.y+mainBitmap.height.toFloat())
            }

        }
        @Throws(ArrayIndexOutOfBoundsException::class)
        private fun drawAnchorsAndBounds(canvas: Canvas){

            canvasHeight = canvas.height.toFloat()
            canvasWidth = canvas.width.toFloat()

            try{
                try{
                canvas.drawLines(points.toFloatArray(), Paint())
                val mutableIterator = pointsAnchor.iterator()
                for (e in mutableIterator) {
                    canvas.drawCircle(e.x , e.y, anchorRadius, Paint())
                 }
                }catch(e:ArrayIndexOutOfBoundsException ){
                    Log.i("Ex", "ArrayIndexOutOfBoundsException")
                }
            }catch (e:ConcurrentModificationException ){
                Log.i("Ex", "ConcurrentModificationException")
            }
        }
        fun getBitmap():Bitmap{
            return mainBitmap
        }
        fun setBitmap(b:Bitmap){
            mainBitmap = b
        }
        fun reSize(point1: PointF, point2: PointF){
            val newWidth = abs(point1.x - point2.x).toInt()
            val newHeight = abs(point1.y - point2.y).toInt()
            if( newWidth in 1..canvasWidth.toInt()
                &&
                newHeight in 1..canvasHeight.toInt())
                mainBitmap = Bitmap.createScaledBitmap(
                    crop(drawTextBitmap( str,
                        min(newWidth, newHeight)/2,
                        min(newWidth, newHeight)/2)),
                    newWidth,
                    newHeight,
                    true)
            this.position = PointF(min(point1.x, point2.x), min(point1.y, point2.y))
            setBorder()
        }
        fun setPosition(position:PointF){
            position.x = if(position.x - mainBitmap.width/2 < 0f){
                0f
            }else{
                if(position.x + mainBitmap.width/2 > canvasWidth){
                    canvasWidth - mainBitmap.width.toFloat()
                }else
                position.x - mainBitmap.width/2
            }
            position.y = if(position.y - mainBitmap.height/2 < 0f){
                0f
            }else{
                if(position.y + mainBitmap.height/2 > canvasHeight){
                    canvasHeight - mainBitmap.height.toFloat()
                }else
                    position.y - mainBitmap.height/2
            }
            this.position = position
            setBorder()
        }
        fun drawEntity(canvas: Canvas){
            canvas.drawBitmap(mainBitmap, position.x, position.y, Paint())
            if(isSelected){
                drawAnchorsAndBounds(canvas)
            }
        }
        fun checkCollusion(point: PointF):Boolean{
            return (point.x in position.x..position.x + mainBitmap.width
                    &&
                    point.y in position.y..position.y + mainBitmap.height)
        }

    }
}
fun crop(origin:Bitmap) :Bitmap{
    val BLACK = -16777216
    var minH = origin.height
    var maxH = 0
    var maxW = 0
    var minW = origin.width
    for(x in 0 until origin.width){
        for(y in 0 until origin.height){
            if(origin.getPixel(x, y) == BLACK){
                if(y < minH)
                    minH = y
                if(y > maxH)
                    maxH = y
                if(x < minW)
                    minW = x
                if(x > maxW)
                    maxW = x
            }
        }
    }
    return Bitmap.createBitmap(origin, minW, minH, maxW - minW, maxH - minH)
}
fun drawTextBitmap(str: String, width: Int, height: Int):Bitmap{
    val tempBitmap = Bitmap.createBitmap(width, height,Bitmap.Config.ARGB_8888)
    val canvas = Canvas(tempBitmap)
    val paint = Paint()
    paint.color = Color.BLACK
    paint.textSize = min(tempBitmap.height,tempBitmap.width).toFloat()
    canvas.drawText(str, 0f, paint.textSize*0.9f, paint)
    return tempBitmap
}
///Мои пытки в деревья, на будущее



class Tree<T>(rootData: T):OnDrawListener{
    private var root = Node<T>()
    private var maxDepth:Int = 1
    private val points:ArrayList<PointF>
    init{
        root.data = rootData
        root.children = ArrayList()
        points = ArrayList()
    }
    private fun getChildNum():Int{
        return root.children!!.size
    }
    fun addChild(data: T){
        val child = Node<T>()
        child.data = data
        if(root.children!!.isEmpty()){
            maxDepth++
        }
        child.parent = root
        root.children!!.add(child)
    }
    fun goDown(childNumber: Int){
        if(root.children != null){
            root = root.children!![childNumber - 1]
        }
    }
    fun goUp(){
        if (root.parent != null) {
            root = root.parent!!
        }
    }
    private fun getMaxRootLevel():Int{
        maxDepth = 0
        points.clear()
        if(root.children!!.isNotEmpty()){
            val list = ArrayList<Int>()
            root.depth = maxDepth
            root.children!!.forEach {
                setPath(it)
                list.add(getNextRootLevel(maxDepth + 1, it))
            }
            maxDepth = list.max()!!
        }
        return maxDepth
    }
    private fun getNextRootLevel(level: Int, children: Node<T>):Int{
        if(children.children != null && children.children!!.isNotEmpty()){
            val list = ArrayList<Int>()
            children.depth = level
            children.children!!.forEach {
                list.add(getNextRootLevel(level + 1, it))
            }
        }
        return level
    }
    private fun setPath(it: Node<T>){
        points.add(PointF(root.floatX + root.floatX / 2, root.floatY + root.floatX / 2))
        points.add(
            PointF(
                root.floatX + root.floatX / 2,
                (root.floatY + it.floatY) / 2 + root.floatX / 2
            )
        )
        points.add(
            PointF(
                it.floatX + root.floatX / 2,
                (root.floatY + it.floatY) / 2 + root.floatX / 2
            )
        )
        points.add(PointF(it.floatX + root.floatX / 2, it.floatY))
        points.add(
            PointF(
                it.floatX + root.floatX / 2,
                (root.floatY + it.floatY) / 2 + root.floatX / 2
            )
        )
        points.add(
            PointF(
                root.floatX + root.floatX / 2,
                (root.floatY + it.floatY) / 2 + root.floatX / 2
            )
        )
    }
    private fun setCords(weight: Float, height: Float){
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
            canvas.drawLine(points[i].x, points[i].y, points[i + 1].x, points[i + 1].y, paintLine)
        canvas.drawKanji(root.data.toString(), root.floatX, root.floatY, width, height)
        root.children!!.forEach {
            canvas.drawKanji(it.data.toString(), it.floatX, it.floatY, width, height)
        }
    }

    override fun sendTouch(event: MotionEvent) {

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