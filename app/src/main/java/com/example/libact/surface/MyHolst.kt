package com.example.libact.surface

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import java.util.*
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.collections.ArrayList
import kotlin.math.abs
import kotlin.math.min


interface OnDrawListener{

    fun draw(canvas: Canvas)
    fun sendTouch(event: MotionEvent)
    fun getListener():OnDrawListener{
        return this
    }
}

class MyHolst(context: Context, attributeSet: AttributeSet): SurfaceView(context, attributeSet), SurfaceHolder.Callback{
    private var thread:DrawThread? = null
    val tree = Tree<String>("0")
    val example = Example()
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

        tree.addChild("A")
        tree.addChild("W")
        tree.addChild("P")


        val sendThing = example

        thread = DrawThread(surfaceHolder)
        send(sendThing)

        setOnClickListener{
            thread?.send(sendThing)
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        example.sendTouch(event!!)
        return super.onTouchEvent(event)
    }
    private fun <T : OnDrawListener>send(thing: T){
        thread?.send(thing)
        thread?.setRunning(true)
        thread?.start()

    }
    fun getCanvas():Canvas?{
        return surfaceHolder.lockCanvas(null)
    }
    fun setCanvas(canvas: Canvas){
        surfaceHolder.unlockCanvasAndPost(canvas)
    }

}

class DrawThread(private val surfaceHolder: SurfaceHolder):Thread() {
    private var runFlag:Boolean = false
    private var beChange = AtomicBoolean(true)
    private var canvas: Canvas? = null
    private var onDrawListener:OnDrawListener? = null
    private var paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    override fun run() {
        super.run()
        while (runFlag){
            if(beChange.get()){
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

    fun startDraw(){
        beChange.set(true)
    }
    fun stopDraw(){
        beChange.set(false)
    }
    fun isDrawing():Boolean{
        return beChange.get()
    }
    fun setRunning(run: Boolean) {
        runFlag = run
    }
    fun <T : OnDrawListener>send(thing: T){
        beChange.set(true)
        onDrawListener = thing.getListener()
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

class Example:OnDrawListener {
    private var bitmap:Bitmap? = null
    private var index = 0
    private val en = ArrayList<Entity>()
    init {
        en.add(Entity("1"))
        en.add(Entity("2"))
        en.add(Entity("3"))
    }
    override fun draw(canvas: Canvas) {
        canvas.drawColor(Color.WHITE)
        for(en in en.iterator()){
            en.drawEntity(canvas)
        }
        //canvas.drawBitmap(en.bitmap, null, Rect(0,0,canvas.width, canvas.height), Paint())
    }

    override fun sendTouch(event: MotionEvent) {

        val point = PointF(event.x,event.y)
        val pointerIndex= event.action and MotionEvent.ACTION_POINTER_INDEX_MASK shr MotionEvent.ACTION_POINTER_INDEX_SHIFT
        val pointerId = event.getPointerId(pointerIndex);
        when(event.actionMasked){
            MotionEvent.ACTION_DOWN -> {
                Log.i("SV", "down")

                for(en in en.iterator()){
                    en.isSelected = en.checkCollusion(point)
                    if( en.checkCollusion(point))
                        break
                }
            }
            MotionEvent.ACTION_MOVE -> {
                Log.i("SV", "$pointerIndex")
                for(en in en.iterator()){
                    if(en.isSelected)
                        when(pointerIndex){
                            0 ->{
                                en.reSize(point, PointF(0f,0f))
                            }
                            1 ->{
                                en.reSize(  point,
                                    PointF( event.getX(0),
                                        event.getY(0))
                                )
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

            }
            MotionEvent.ACTION_POINTER_UP ->{

            }

            else -> {
//                Log.i("SV", "${}")
            }
        }


    }


    class Entity(str:String){
        var isSelected:Boolean = false
        private val pointsAnchor = ArrayList<PointF>()
        private val points = ArrayList<Float>()
        private val str:String = str
        private var mainBitmap:Bitmap = Bitmap.createBitmap(300, 300, Bitmap.Config.ARGB_8888)
        private var border:Bitmap = Bitmap.createBitmap(300, 300, Bitmap.Config.ARGB_8888)
        private var position = PointF(0f, 0f)
        private var anchorRadius = 20f
        private var canvasHeight = 0f
        private var canvasWidth = 0f

        init{
            setBorder()
            drawMainBitmap(300,300)
        }
        private fun setBorder() {

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
        @Throws(ArrayIndexOutOfBoundsException::class)
        private fun drawAnchorsAndBounds(canvas: Canvas){

            canvasHeight = canvas.height.toFloat()
            canvasWidth = canvas.width.toFloat()

            try{
                canvas.drawLines(points.toFloatArray(), Paint())
                val mutableIterator = pointsAnchor.iterator()
                for (e in mutableIterator) {
                    canvas.drawCircle(e.x , e.y, anchorRadius, Paint())
                }
            }catch (e:ConcurrentModificationException ){
                Log.i("Ex", "ConcurrentModificationException")
            }

        }
        fun reSize(point1: PointF, point2: PointF){
            drawMainBitmap( abs((point1.x - point2.x).toInt()),
                            abs((point1.y - point2.y).toInt())
            )
            setPosition(PointF(min(point1.x, point2.x), min(point1.y, point2.y)))
        }
        private fun drawMainBitmap(width: Int, height: Int){
            val tempBitmap = Bitmap.createBitmap(width, height,Bitmap.Config.ARGB_8888)
            val canvas = Canvas(tempBitmap)
            val paint = Paint()
            paint.color = Color.BLACK
            paint.textSize = tempBitmap.height.toFloat()
            canvas.drawText(str, 0f, tempBitmap.height*0.9f, paint)
            mainBitmap = tempBitmap
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
///Мои пытки в деревья, на будущее



class Tree<T>(rootData: T):OnDrawListener{
    private var root = Node<T>()
    private var maxDepth:Int = 1
    private val points:ArrayList<PointF>
    init{
        root.data = rootData
        root.children = ArrayList<Node<T>>()
        points = ArrayList<PointF>()
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