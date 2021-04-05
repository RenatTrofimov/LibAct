package com.example.libact.surface

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView

class MyHolst(context: Context, attributeSet: AttributeSet): SurfaceView(context, attributeSet), SurfaceHolder.Callback{
    var path = Path()
    private var thread:DrawThread? = null
    private val surfaceHolder: SurfaceHolder = holder
    init{
        this.surfaceHolder.addCallback(this)
    }

    override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {

    }

    override fun surfaceDestroyed(holder: SurfaceHolder?) {
        thread?.setRunning(false)
        var lock:Boolean = true
        while (lock){
            try{
                thread?.join();
                lock = false;
            }catch (e: InterruptedException){

            }
        }
        Log.i("SV", "surfaceDestroyed")
    }

    override fun surfaceCreated(holder: SurfaceHolder?) {
        thread = DrawThread(surfaceHolder)
        thread?.setRunning(true)
        thread?.start()
        Log.i("SV", "surfaceCreated")
    }
}
class DrawThread(private val surfaceHolder: SurfaceHolder):Thread(){
    private var runFlag:Boolean = false
    private var prevTime: Long = 0

    init{
        prevTime = System.currentTimeMillis()
    }
    override fun run() {
        super.run()
        var canvas: Canvas?
        var paint = Paint(Paint.ANTI_ALIAS_FLAG)
        var intCount:Int=1
        paint.color = Color.BLACK
        while (runFlag){
            val now = System.currentTimeMillis()
            val elapsedTime = now - prevTime

            if(elapsedTime>1){
                intCount++
                prevTime = now
            }
            canvas = null
            try{
                canvas = surfaceHolder.lockCanvas(null)
            }finally {
                if(canvas!=null){
                    paint.setTextSize(intCount.toFloat())
                    canvas.drawColor(Color.WHITE)
                    canvas.drawText("会", 0f, canvas.height.toFloat(), paint)
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