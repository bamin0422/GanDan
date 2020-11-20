package h2mud2.ganpanproject.gandan.model

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import h2mud2.ganpanproject.gandan.R

class TextControllWidget : ConstraintLayout {
    
    /* 2020.11.04 / 민대인
    DesignTool의 Text 커스텀 위젯으로 만드려 했으나 
     */

    lateinit var textView: TextView
    lateinit var deleteBtn: Button

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    init {
        textView = TextView(context)
        deleteBtn = Button(context)

        var inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.text_layout, this, true)
        textView = findViewById(R.id.textItem)
        deleteBtn = findViewById(R.id.deleteItem)

        deleteBtn.setOnClickListener(this)
    }

    private fun Button.setOnClickListener(textControllWidget: TextControllWidget) {
        textControllWidget.visibility = View.GONE
    }
}

