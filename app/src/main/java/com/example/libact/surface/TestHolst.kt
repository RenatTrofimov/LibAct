package com.example.libact.surface

import android.graphics.*
import android.util.Log
import android.view.MotionEvent
import java.util.ConcurrentModificationException
import kotlin.math.abs
import kotlin.math.min

class TestHolst:OnDrawListener {
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
    }

    override fun sendTouch(event: MotionEvent) {
        val point = PointF(event.x,event.y)
        val pointerIndex= event.actionIndex
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
//                Log.i("i", "first ${event.x} and ${event.y}")
                for(en in en.iterator()){
                    if(en.isSelected) {
                        if (event.pointerCount > 1) {
                            val secondTouch = MotionEvent.PointerCoords()
                            event.getPointerCoords(1, secondTouch)
//                            Log.i("i", "second ${secondTouch.x} and ${secondTouch.y}")
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
        private var mainBitmap: Bitmap = Bitmap.createBitmap(300, 300, Bitmap.Config.ARGB_8888)
        private var border: Bitmap = Bitmap.createBitmap(300, 300, Bitmap.Config.ARGB_8888)
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
                try{
                    canvas.drawLines(points.toFloatArray(), Paint())
                    val mutableIterator = pointsAnchor.iterator()
                    for (e in mutableIterator) {
                        canvas.drawCircle(e.x , e.y, anchorRadius, Paint())
                    }
                }catch(e:ArrayIndexOutOfBoundsException ){
                    Log.i("Ex", "ArrayIndexOutOfBoundsException")
                }
            }catch (e: ConcurrentModificationException){
                Log.i("Ex", "ConcurrentModificationException")
            }

        }
        fun reSize(point1: PointF, point2: PointF){
            if(abs(point1.x - point2.x) in 1f..canvasWidth && abs(point1.y - point2.y) in 1f..canvasHeight)
                drawMainBitmap( abs((point1.x - point2.x).toInt()),
                    abs((point1.y - point2.y).toInt())
                )

            this.position = PointF(min(point1.x, point2.x), min(point1.y, point2.y))
            setBorder()
        }
        private fun drawMainBitmap(width: Int, height: Int){
            val tempBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(tempBitmap)
            val paint = Paint()
            paint.color = Color.BLACK
            paint.textSize = tempBitmap.height.toFloat()
            canvas.drawText(str, 0f, tempBitmap.height*0.9f, paint)
            mainBitmap = tempBitmap
        }
        fun setPosition(position: PointF){
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