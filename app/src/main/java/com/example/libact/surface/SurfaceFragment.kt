package com.example.libact.surface

import android.content.Context
import android.graphics.*
import android.os.Bundle
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import androidx.fragment.app.Fragment
import com.example.libact.R
import kotlinx.android.synthetic.main.test_fragment.*
import java.util.*


class SurfaceFragment: Fragment(R.layout.test_fragment) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var myHolst = MyHolst(requireContext(), surfaceView)
    }

    class MyHolst(context: Context,surfaceView: SurfaceView ):SurfaceView(context), SurfaceHolder.Callback{
        var thread:DrawThread? = null
        var surfaceHolder:SurfaceHolder?=null

        init{
            surfaceHolder = surfaceView.holder
            holder.addCallback(this)
        }

        override fun onTouchEvent(event: MotionEvent): Boolean {
            var path = Path()
            val paint = Paint(Paint.ANTI_ALIAS_FLAG)
            var random: Random

            if (event.action == MotionEvent.ACTION_DOWN) {
                path = Path()
                path.moveTo(event.x, event.y)
            } else if (event.action == MotionEvent.ACTION_MOVE) {
                path.lineTo(event.x, event.y)
            } else if (event.action == MotionEvent.ACTION_UP) {
                path.lineTo(event.x, event.y)
            }
            if (path != null) {
                val canvas: Canvas = surfaceHolder!!.lockCanvas()
                canvas.drawPath(path, paint)
                surfaceHolder!!.unlockCanvasAndPost(canvas)
            }
            return true
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
                }catch (e:InterruptedException){

                }
            }
        }

        override fun surfaceCreated(holder: SurfaceHolder?) {
            thread = surfaceHolder?.let { DrawThread(it) }
            thread?.setRunning(true)
            thread?.start()
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
            var canvas:Canvas?
            var paint = Paint(Paint.ANTI_ALIAS_FLAG)
            var intCount:Int=0
            paint.color = intCount


            while (runFlag){
                val now = System.currentTimeMillis()
                val elapsedTime = now - prevTime

                if(elapsedTime>1){
                    paint.color = intCount
                    intCount++
                    prevTime = now
                }
                canvas = null
                try{
                    canvas = surfaceHolder.lockCanvas(null)
                }finally {
                    if(canvas!=null){
                        canvas = draw(canvas, paint)
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                }
            }
        }

        fun setRunning(run: Boolean) {
            runFlag = run
        }
        fun draw(canvas:Canvas, paint:Paint):Canvas{
            canvas.drawRect(0f,0f,canvas.width.toFloat(), canvas.height.toFloat(), paint)
            return canvas
        }
    }
}