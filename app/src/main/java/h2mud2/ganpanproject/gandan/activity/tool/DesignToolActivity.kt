package h2mud2.ganpanproject.gandan.activity.tool

import android.content.ClipData
import android.graphics.drawable.Drawable
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import h2mud2.ganpanproject.gandan.R
import h2mud2.ganpanproject.gandan.adapter.GridAdapter
import h2mud2.ganpanproject.gandan.adapter.HorizonAdapter
import h2mud2.ganpanproject.gandan.crawler.WebCrawler
import h2mud2.ganpanproject.gandan.decoration.RecyclerViewDecoration
import h2mud2.ganpanproject.gandan.fragment.FireStoreManager
import h2mud2.ganpanproject.gandan.model.Item
import kotlinx.android.synthetic.main.activity_design.*
import kotlinx.coroutines.*
import net.margaritov.preference.colorpicker.ColorPickerPanelView
import net.margaritov.preference.colorpicker.ColorPickerView
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.db.INTEGER
import org.jetbrains.anko.find
import kotlin.properties.Delegates

class DesignToolActivity : AppCompatActivity(){

    lateinit var cancelBtn : Button
    lateinit var saveBtn : Button
    lateinit var penBtn : ImageButton
    lateinit var photoBtn : ImageButton
    lateinit var textBtn : ImageButton
    lateinit var sizeBtn : ImageButton
    lateinit var backgroundBtn : ImageButton

    lateinit var sizeLayout : ConstraintLayout
    lateinit var ganpanFrame : FrameLayout

    lateinit var heigtEditor : EditText
    lateinit var widthEditor : EditText

    // background
    lateinit var backgroundLayout : ConstraintLayout
    lateinit var colorPadLayoutBackground : ConstraintLayout
    lateinit var colorPadBackground : ColorPickerView
    lateinit var closeColorPickerBackground : Button
    lateinit var backgroundColorBtn : ImageButton
    lateinit var backgroundImgBtn : ImageButton
    lateinit var colorPreview : FrameLayout

    // pen
    lateinit var penLayout : ConstraintLayout
    lateinit var colorPadLayoutPen : ConstraintLayout
    lateinit var colorPadPen : ColorPickerView
    lateinit var closeColorPickerPen : Button
    lateinit var penColorBtn : ImageButton
    lateinit var penThicknessBtn : ImageButton
    lateinit var ereaserBtn : ImageButton
    lateinit var colorPreviewPen : FrameLayout

    var cnt = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_design)

        ganpanFrame = findViewById(R.id.ganpan_frame)

        sizeLayout = findViewById(R.id.sizeController)
        heigtEditor = findViewById(R.id.height)
        widthEditor = findViewById(R.id.length)

        cancelBtn = findViewById(R.id.cancel_btn)
        saveBtn = findViewById(R.id.save_btn)
        penBtn = findViewById(R.id.penAdder)
        photoBtn = findViewById(R.id.photoAdder)
        textBtn = findViewById(R.id.textAdder)
        sizeBtn = findViewById(R.id.sizeEditor)
        backgroundBtn = findViewById(R.id.backgroundEditor)

        // background 설정 구성
        backgroundLayout = findViewById(R.id.backgroundController)
        colorPadLayoutBackground = findViewById(R.id.colorPadLayoutBackground)
        colorPadBackground = findViewById(R.id.backgroundColorPad)
        closeColorPickerBackground = findViewById(R.id.close_colorpad_background)
        backgroundColorBtn = findViewById(R.id.backgroundColorController)
        backgroundImgBtn = findViewById(R.id.backgroundImgController)
        colorPreview = findViewById(R.id.colorpreview)

        // pen 설정 구성
        penLayout = findViewById(R.id.penController)
        colorPadLayoutPen = findViewById(R.id.colorPadLayoutpen)
        colorPadPen = findViewById(R.id.penColorPad)
        closeColorPickerPen = findViewById(R.id.close_colorpad_pen)
        penColorBtn = findViewById(R.id.penColor)
        penThicknessBtn = findViewById(R.id.penThickness)
        ereaserBtn = findViewById(R.id.ereaser)
        colorPreviewPen = findViewById(R.id.colorpreviewpen)


        var params : ViewGroup.LayoutParams = ganpanFrame.layoutParams
        var color = 0
        var penColor = 0



        cancelBtn.setOnClickListener {
            if(checkMenu(cnt)){
                when(cnt){
                    2 -> penLayout.visibility = View.GONE
                    4 -> sizeLayout.visibility = View.GONE
                    5 -> backgroundLayout.visibility = View.GONE
                }
                cnt = 0
            }
            else finish()
        }
        saveBtn.setOnClickListener {
            when(cnt){
                2 -> {
                    penLayout.visibility = View.GONE
                }
                4 -> {
                    params.width = widthEditor.text.toString().toInt()
                    params.height = heigtEditor.text.toString().toInt()
                    sizeLayout.visibility = View.GONE
                }
                5 -> {
                    ganpanFrame.backgroundColor = color
                    backgroundLayout.visibility = View.GONE
                }
            }
            cnt = 0
        }
        photoBtn.setOnClickListener {  }
        textBtn.setOnClickListener {  }
        sizeBtn.setOnClickListener {
            cnt = 4
            sizeLayout.visibility = View.VISIBLE
        }

        // background 설정
        colorPadBackground.setOnColorChangedListener {
            color = colorPadBackground.color
            colorPreview.setBackgroundColor(color)

        }
        backgroundBtn.setOnClickListener {
            colorPadLayoutBackground.visibility = View.GONE
            cnt = 5
            backgroundLayout.visibility = View.VISIBLE
        }

        backgroundColorBtn.setOnClickListener {
            colorPadLayoutBackground.visibility = View.VISIBLE
        }

        backgroundImgBtn.setOnClickListener {  }

        closeColorPickerBackground.setOnClickListener {
            colorPadLayoutBackground.visibility = View.GONE
        }

        // pen 설정
        penBtn.setOnClickListener {
            cnt = 2
            penLayout.visibility = View.VISIBLE
        }
        closeColorPickerPen.setOnClickListener {
            colorPadLayoutPen.visibility = View.GONE
        }
        ereaserBtn.setOnClickListener {  }
        penColorBtn.setOnClickListener {
            colorPadLayoutPen.visibility = View.VISIBLE
        }
        penThicknessBtn.setOnClickListener {  }
        colorPadPen.setOnColorChangedListener {
            penColor = colorPadPen.color
            colorPreviewPen.setBackgroundColor(penColor)
        }

    }

    override fun onBackPressed() {
        when(cnt){
            0 -> finish()
            1-> {
                cnt = 0
            }
            2-> {
                cnt = 0
                penLayout.visibility = View.GONE
            }
            3-> {
                cnt = 0
            }
            4 -> {
                cnt = 0
                sizeLayout.visibility = View.GONE
            }
            5 -> {
                cnt = 0
                backgroundLayout.visibility = View.GONE
            }
        }
    }

    fun checkMenu(cnt : Int) : Boolean{
        if(cnt != 0) return true
        else return false
    }
}