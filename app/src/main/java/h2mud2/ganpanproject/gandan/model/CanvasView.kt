package h2mud2.ganpanproject.gandan.model

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import java.util.ArrayList

public class CanvasView : ConstraintLayout {

    lateinit var pathInfo : PathInfo
    var data = ArrayList<PathInfo>()
    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )
    var check = 0
    fun setPathInfo(color: Int, r: Float){
        check = 2
        var paint = Paint()
        paint.setColor(color)
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = r
        pathInfo = PathInfo()
        pathInfo.setPaint(paint)
    }


    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if(check == 0){
            return true
        }
        else if(check == 1){
            return true
        }
        else {
            pathInfo.setPaintStyle(Paint.Style.STROKE)
            when(event?.action){
                MotionEvent.ACTION_DOWN -> pathInfo.moveTo(event.x, event.y)
                MotionEvent.ACTION_MOVE -> pathInfo.lineTo(event.x, event.y)
                MotionEvent.ACTION_UP -> {
                    data.add(pathInfo)
                    invalidate()
                    return true
                }
            }
            data.add(pathInfo)
            invalidate()
            return true
        }
    }

    override public fun onDraw(canvas: Canvas?) {
        for(p in data){
            canvas?.drawPath(p, p.getPaint())
        }
        super.onDraw(canvas)
    }
}

public class PathInfo : Path() {
    private lateinit var paint : Paint
    init {
        paint = Paint()
    }
    fun setPaint(paint : Paint){
        this.paint = paint
    }
    fun setPaintStyle(paint : Paint.Style ){
        this.paint.style = paint
    }
    fun getPaint() : Paint{
        return this.paint
    }
}