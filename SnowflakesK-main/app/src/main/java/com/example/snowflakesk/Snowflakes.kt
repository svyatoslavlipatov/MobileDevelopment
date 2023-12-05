package com.example.snowflakesk

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.AsyncTask
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import kotlin.random.Random

data class Snowflake(var x: Float, var y: Float, var velocity: Float, val radius: Float, val color: Int)

class Snowflakes @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    View(context, attrs, defStyleAttr) {

    lateinit var moveTask: MoveTask
    lateinit var snow: Array<Snowflake>
    val paint = Paint()
    var h = 1000
    var w = 1000

    init {
        setWillNotDraw(false)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawColor(Color.WHITE)

        for (s in snow) {
            paint.color = s.color
            canvas.drawCircle(s.x, s.y, s.radius, paint)
        }
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        h = bottom - top
        w = right - left
        val r = Random(0)
        r.nextFloat()

        snow = Array(10) {
            Snowflake(
                r.nextFloat() * w,
                r.nextFloat() * h,
                15 + 10 * r.nextFloat(),
                30 + 20 * r.nextFloat(),
                Color.rgb(r.nextInt(256), r.nextInt(256), r.nextInt(256))
            )
        }

        moveTask = MoveTask(this)
        moveTask.execute(100)
    }

    fun moveSnowflakes() {
        for (s in snow) {
            s.y += s.velocity
            if (s.y > h) {
                s.y -= h
            }
        }
        postInvalidate()
    }

    inner class MoveTask(val s: Snowflakes) : AsyncTask<Int, Int, Unit>() {
        override fun doInBackground(vararg params: Int?) {
            val delay = params[0] ?: 200
            while (!isCancelled) {
                Thread.sleep(delay.toLong())
                publishProgress()
            }
        }

        override fun onProgressUpdate(vararg values: Int?) {
            s.moveSnowflakes()
        }
    }
}