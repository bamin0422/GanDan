package h2mud2.ganpanproject.gandan.activity.tool

import android.content.ClipData
import android.graphics.drawable.Drawable
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.*
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
import h2mud2.ganpanproject.gandan.model.CanvasView
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
    lateinit var ganpanFrame : CanvasView

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
    lateinit var closeThicknessPen : Button
    lateinit var penLayout : ConstraintLayout
    lateinit var colorPadLayoutPen : ConstraintLayout
    lateinit var colorPadPen : ColorPickerView
    lateinit var closeColorPickerPen : Button
    lateinit var penColorBtn : ImageButton
    lateinit var penThicknessBtn : ImageButton
    lateinit var ereaserBtn : ImageButton
    lateinit var colorPreviewPen : FrameLayout
    lateinit var penThicknessLayout : ConstraintLayout
    lateinit var penThicknessTV : TextView
    lateinit var penThicknessSeekBar : SeekBar
    lateinit var penEreaserLayout : ConstraintLayout
    lateinit var penEreaserSeekBar : SeekBar
    lateinit var penEreaserTV : TextView
    lateinit var closeEreaserPen : Button

    var menuCheck = 0
    var detailMenuCheck = -1

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
        closeThicknessPen = findViewById(R.id.close_thickness_pen)
        penColorBtn = findViewById(R.id.penColor)
        penThicknessBtn = findViewById(R.id.penThickness)
        ereaserBtn = findViewById(R.id.ereaser)
        colorPreviewPen = findViewById(R.id.colorpreviewpen)
        penThicknessLayout = findViewById(R.id.penThicknessLayout)
        penThicknessTV =findViewById(R.id.penThicknessTV)
        penThicknessSeekBar = findViewById(R.id.penThicknessSeekBar)
        penEreaserLayout = findViewById(R.id.penEreaserLayout)
        penEreaserSeekBar = findViewById(R.id.penEreaserSeekBar)
        penEreaserTV = findViewById(R.id.penEreaserTV)
        closeEreaserPen = findViewById(R.id.close_ereaser_pen)


        var params : ViewGroup.LayoutParams = ganpanFrame.layoutParams
        var color = 0
        var penColor = 0
        var penThickness = 1
        var ereaserThickness = 1
        penThicknessSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                penThickness = progress + 1
                ganpanFrame.setThickness(penThickness)
                penThicknessTV.text = "선굵기 : "+(progress+1).toString()
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}

        })

        penEreaserSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                ereaserThickness = progress + 1

                penEreaserTV.text = "지우개 굵기 : "+(progress+1).toString()
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}

        })



        cancelBtn.setOnClickListener {
            if(checkMenu(menuCheck)){
                when(menuCheck){
                    2 -> penLayout.visibility = View.GONE
                    4 -> sizeLayout.visibility = View.GONE
                    5 -> backgroundLayout.visibility = View.GONE
                }
                menuCheck = 0
            }
            else finish()
        }
        saveBtn.setOnClickListener {
            when(menuCheck){
                2 -> {
                    colorPadLayoutPen.visibility = View.GONE
                    penLayout.visibility = View.GONE
                    penThicknessLayout.visibility = View.GONE
                    penEreaserLayout.visibility = View.GONE
                }
                4 -> {
                    params.width = widthEditor.text.toString().toInt()
                    params.height = heigtEditor.text.toString().toInt()
                    sizeLayout.visibility = View.GONE
                }
                5 -> {
                    colorPadLayoutPen.visibility = View.GONE
                    ganpanFrame.backgroundColor = color
                    backgroundLayout.visibility = View.GONE
                }
            }
            menuCheck = 0
        }
        photoBtn.setOnClickListener {  }
        textBtn.setOnClickListener {  }
        sizeBtn.setOnClickListener {
            menuCheck = 4
            sizeLayout.visibility = View.VISIBLE
        }

        // background 설정
        colorPadBackground.setOnColorChangedListener {
            color = colorPadBackground.color
            colorPreview.setBackgroundColor(color)

        }
        backgroundBtn.setOnClickListener {
            colorPadLayoutBackground.visibility = View.GONE
            menuCheck = 5
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
            menuCheck = 2
            penLayout.visibility = View.VISIBLE
        }
        closeColorPickerPen.setOnClickListener {
            colorPadLayoutPen.visibility = View.GONE
        }
        closeEreaserPen.setOnClickListener {
            penEreaserLayout.visibility = View.GONE
        }
        ereaserBtn.setOnClickListener {
            penEreaserLayout.visibility = View.VISIBLE
        }
        penColorBtn.setOnClickListener {
            colorPadLayoutPen.visibility = View.VISIBLE
        }
        penThicknessBtn.setOnClickListener {
            penThicknessLayout.visibility = View.VISIBLE
        }
        colorPadPen.setOnColorChangedListener {
            penColor = colorPadPen.color
            ganpanFrame.setPaint(penColor)
            colorPreviewPen.setBackgroundColor(penColor)
        }
        closeThicknessPen.setOnClickListener {
            penThicknessLayout.visibility = View.GONE
        }

    }

    override fun onBackPressed() {
        when(menuCheck){
            0 -> finish()
            1-> {
                menuCheck = 0
            }
            2-> {
                menuCheck = 0
                penEreaserLayout.visibility = View.GONE
                colorPadLayoutPen.visibility = View.GONE
                penLayout.visibility = View.GONE
                penThicknessLayout.visibility =View.GONE
            }
            3-> {
                menuCheck = 0
            }
            4 -> {
                menuCheck = 0
                sizeLayout.visibility = View.GONE
            }
            5 -> {
                colorPadLayoutPen.visibility = View.GONE
                menuCheck = 0
                backgroundLayout.visibility = View.GONE
            }
        }
    }

    fun checkMenu(cnt : Int) : Boolean{
        if(cnt != 0) return true
        else return false
    }
}