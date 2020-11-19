package h2mud2.ganpanproject.gandan.model

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.os.Build
import android.util.AttributeSet
import android.view.FrameMetrics
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.RequiresApi

public class CanvasView : View {

    var paint = Paint()
    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )
    var startX = -1
        var startY = -1
        var stopX = -1
        var stopY = -1

        @SuppressLint("WrongCall")
        override fun onTouchEvent(event: MotionEvent?): Boolean {
            when (event?.action) {
                MotionEvent.ACTION_DOWN -> { //touch 시작, 화면에 손가락 올림.
                    startX = event.x.toInt()
                    startY = event.y.toInt()
                }
                MotionEvent.ACTION_MOVE -> {
                    // 화면에서 이동할 때, 화면에서 손가락을 띄였을 때.
                    stopX = event.x.toInt()
                    stopY = event.y.toInt()
                    this.invalidate() // 명령 완료, 그리기 호출.
                }

                // move랑 up을 나눠서 처리하는 이유는, 도형의 잔상이 남지 않도록 하기 위해서.

                MotionEvent.ACTION_UP -> {
                    this.invalidate()
                }

            }

            return true
        }
    fun setPaint(color: Int) {
        paint.setColor(color)
    }

    fun setThickness(thickness: Int) {
        paint.strokeWidth = thickness.toFloat()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
    }
}