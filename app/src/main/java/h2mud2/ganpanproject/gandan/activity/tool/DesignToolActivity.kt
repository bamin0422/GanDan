package h2mud2.ganpanproject.gandan.activity.tool

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.provider.MediaStore
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.common.primitives.UnsignedBytes.toInt
import h2mud2.ganpanproject.gandan.R
import h2mud2.ganpanproject.gandan.adapter.FontAdapter
import h2mud2.ganpanproject.gandan.adapter.FontSt
import h2mud2.ganpanproject.gandan.model.CanvasView
import h2mud2.ganpanproject.gandan.model.PathInfo
import kotlinx.android.synthetic.main.activity_design.*
import net.margaritov.preference.colorpicker.ColorPickerView
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.textColor

class DesignToolActivity : AppCompatActivity(), View.OnTouchListener {
    
    /* 2020.09.28 / 민대인
    디자인 툴
     */

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

    // text
    lateinit var textController : ConstraintLayout
    lateinit var colorPadLayoutText : ConstraintLayout
    lateinit var textColorPad : ColorPickerView
    lateinit var closeColorPickerText : Button
    lateinit var colorPreviewText : FrameLayout
    lateinit var textStyleLayout : ConstraintLayout
    lateinit var textInputET : EditText
    lateinit var textSizeET : EditText
    lateinit var closeTextStyleBtn : Button
    lateinit var fontLayout : ConstraintLayout
    lateinit var fontRecycler : RecyclerView
    lateinit var textEditer : ImageButton
    lateinit var textFont : ImageButton
    lateinit var textColorPick : ImageButton
    lateinit var tFont : Typeface
    lateinit var addText : ImageButton
    lateinit var addTextView : TextView
    lateinit var addImageView : ImageView


    var menuCheck = 0
    var detailMenuCheck = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_design)

        ganpanFrame = findViewById(R.id.ganpan_frame)
        addImageView = findViewById(R.id.addImage)

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

        // text 설정 구성
        textController = findViewById(R.id.textController)
        colorPadLayoutText = findViewById(R.id.colorPadLayouttext)
        textColorPad = findViewById(R.id.textColorPad)
        closeColorPickerText = findViewById(R.id.close_colorpad_text)
        colorPreviewText = findViewById(R.id.colorpreviewtext)
        textStyleLayout = findViewById(R.id.textStyleLayout)
        textInputET = findViewById(R.id.textInputET)
        textSizeET = findViewById(R.id.textSizeET)
        closeTextStyleBtn = findViewById(R.id.close_textEditer)
        fontLayout = findViewById(R.id.fontLayout)
        fontRecycler = findViewById(R.id.fontRecycler)
        textEditer = findViewById(R.id.textEditer)
        textFont = findViewById(R.id.textFont)
        textColorPick = findViewById(R.id.textColor)
        addText = findViewById(R.id.addText)
        addTextView = findViewById(R.id.addTextView)

        var params : ViewGroup.LayoutParams = ganpanFrame.layoutParams
        var imgParams : ViewGroup.LayoutParams = addImageView.layoutParams
        var color = 0
        var tColor = Color.BLACK
        var penColor = Color.BLACK
        var penThickness = 1
        var ereaserThickness = 1
        var fontList : ArrayList<FontSt> = arrayListOf()
        fontList.add(FontSt("BC카드", "bccard"))
        fontList.add(FontSt("빙그레", "binggrae1"))
        fontList.add(FontSt("빙그레", "binggrae2"))
        fontList.add(FontSt("빙그레메로나", "binggraemelona1"))
        fontList.add(FontSt("빙그레메로나", "binggraemelona2"))
        fontList.add(FontSt("빙그레싸만코", "binggraesamanco1"))
        fontList.add(FontSt("빙그레싸만코", "binggraesamanco2"))
        fontList.add(FontSt("빙그레따옴", "binggraetaom1"))
        fontList.add(FontSt("빙그레따옴", "binggraetaom2"))
        fontList.add(FontSt("빙그레2-1", "binggraev21"))
        fontList.add(FontSt("빙그레2-2", "binggraev22"))
        fontList.add(FontSt("배민을지로", "bmeuljiro"))
        fontList.add(FontSt("gong1", "gong1"))
        fontList.add(FontSt("gong2", "gong2"))
        fontList.add(FontSt("gong3", "gong3"))
        fontList.add(FontSt("잘풀리는집", "jalpullineun"))
        fontList.add(FontSt("코트라", "kotra"))
        fontList.add(FontSt("마루부리", "maruburi"))
        fontList.add(FontSt("닉스곤체 l", "nixgonl"))
        fontList.add(FontSt("닉스곤체 m", "nixgonm"))
        fontList.add(FontSt("페이북1", "paybooc1"))
        fontList.add(FontSt("페이북2", "paybooc2"))
        fontList.add(FontSt("스포카 B", "spoqab"))
        fontList.add(FontSt("스포카 L", "spoqal"))
        fontList.add(FontSt("스포카 R", "spoqar"))
        fontList.add(FontSt("스포카 thin", "spoqathin"))
        fontList.add(FontSt("위메프 B", "wemakepriceb"))
        fontList.add(FontSt("위메프 R", "wemakepricer"))
        fontList.add(FontSt("위메프 SB", "wemakepricesb"))

        var fontItemAdapter = FontAdapter(applicationContext, fontList){
            tFont = Typeface.createFromAsset(applicationContext.assets, "font/"+it.font+".ttf")
            addTextView.typeface = tFont
        }
        fontRecycler.adapter = fontItemAdapter

        penThicknessSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                penThickness = progress + 1
                ganpanFrame.setPathInfo(penColor, penThickness.toFloat())
                penThicknessTV.text = "선굵기 : "+(progress+1).toString()
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}

        })

        cancelBtn.setOnClickListener {
            if(checkMenu(menuCheck)){
                when(menuCheck){
                    1 -> {
                        textController.visibility = View.GONE
                        addTextView.visibility = View.INVISIBLE
                    }
                    2 -> {
                        penLayout.visibility = View.GONE
                        ganpanFrame.data = ArrayList<PathInfo>()
                    }
                    3 -> {
                        addImageView.visibility =View.GONE
                    }
                    4 -> sizeLayout.visibility = View.GONE
                    5 -> {
                        backgroundLayout.visibility = View.GONE
                    }
                }
                menuCheck = 0
                saveBtn.text = "저장"
            }
            else finish()
        }

        saveBtn.setOnClickListener {
            when(menuCheck){
                1 -> {
                    addTextView.textSize = textSizeET.text.toString().toFloat()
                    addTextView.text = textInputET.text
                    colorPadLayoutText.visibility = View.GONE
                    textStyleLayout.visibility =View.GONE
                    fontLayout.visibility =View.GONE
                    textController.visibility = View.GONE
                }
                2 -> {
                    colorPadLayoutPen.visibility = View.GONE
                    penLayout.visibility = View.GONE
                    penThicknessLayout.visibility = View.GONE
                }
                3 -> {
                    params.width = widthEditor.text.toString().toInt()
                    params.height = heigtEditor.text.toString().toInt()
                    sizeLayout.visibility = View.GONE
                }
                4 -> {
                    imgParams.width = widthEditor.text.toString().toInt()
                    imgParams.height = heigtEditor.text.toString().toInt()
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
        photoBtn.setOnClickListener {
            sizeController.visibility = View.VISIBLE
            var writePermission = ContextCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE)
            var readPermission = ContextCompat.checkSelfPermission(this, READ_EXTERNAL_STORAGE)
            if (writePermission == PackageManager.PERMISSION_DENIED || readPermission == PackageManager.PERMISSION_DENIED)
            {
                ActivityCompat.requestPermissions(this, arrayOf(WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE), 200)
            } else {
                var intent = Intent()
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*")
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 300)
            }
        }
        textBtn.setOnClickListener {
            saveBtn.text = "적용"
            menuCheck = 1
            textController.visibility = View.VISIBLE
        }
        sizeBtn.setOnClickListener {
            saveBtn.text = "적용"
            menuCheck = 4
            sizeLayout.visibility = View.VISIBLE
        }

        // background 설정
        colorPadBackground.setOnColorChangedListener {
            color = colorPadBackground.color
            colorPreview.setBackgroundColor(color)

        }
        backgroundBtn.setOnClickListener {
            saveBtn.text = "적용"
            colorPadLayoutBackground.visibility = View.GONE
            menuCheck = 5
            backgroundLayout.visibility = View.VISIBLE
        }

        backgroundColorBtn.setOnClickListener {
            colorPadLayoutBackground.visibility = View.VISIBLE
        }

        backgroundImgBtn.setOnClickListener {
            var writePermission = ContextCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE)
            var readPermission = ContextCompat.checkSelfPermission(this, READ_EXTERNAL_STORAGE)
            if (writePermission == PackageManager.PERMISSION_DENIED || readPermission == PackageManager.PERMISSION_DENIED)
            {
                ActivityCompat.requestPermissions(this, arrayOf(WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE), 200)
            } else {
                var intent = Intent()
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*")
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 200)
            }

        }

        closeColorPickerBackground.setOnClickListener {
            colorPadLayoutBackground.visibility = View.GONE
        }

        // pen 설정
        penBtn.setOnClickListener {
            saveBtn.text = "적용"
            menuCheck = 2
            penLayout.visibility = View.VISIBLE
        }
        closeColorPickerPen.setOnClickListener {
            colorPadLayoutPen.visibility = View.GONE
        }

        ereaserBtn.setOnClickListener {
            ganpanFrame.data.removeAt(ganpanFrame.data.size - 1)
            colorPadLayoutPen.visibility = View.GONE
            penThicknessLayout.visibility = View.GONE
        }
        penColorBtn.setOnClickListener {
            colorPadLayoutPen.visibility = View.VISIBLE
            penThicknessLayout.visibility = View.GONE
        }
        penThicknessBtn.setOnClickListener {
            colorPadLayoutPen.visibility = View.GONE
            penThicknessLayout.visibility = View.VISIBLE
        }
        colorPadPen.setOnColorChangedListener {
            penColor = colorPadPen.color
            ganpanFrame.setPathInfo(penColor, penThickness.toFloat())
            colorPreviewPen.setBackgroundColor(penColor)
        }
        closeThicknessPen.setOnClickListener {
            penThicknessLayout.visibility = View.GONE
        }

        // text 설정
        addTextView.setOnTouchListener(this)
        addImageView.setOnTouchListener(this)
        textColorPad.setOnColorChangedListener {
            tColor = textColorPad.color
            colorPreviewText.setBackgroundColor(tColor)
            addTextView.textColor = tColor
        }
        textEditer.setOnClickListener {
            textStyleLayout.visibility = View.VISIBLE
            fontLayout.visibility = View.GONE
            colorPadLayoutText.visibility = View.GONE
        }
        textFont.setOnClickListener {
            var fontlm = LinearLayoutManager(applicationContext)
            fontlm.orientation = LinearLayoutManager.HORIZONTAL
            fontRecycler.layoutManager = fontlm
            fontLayout.visibility = View.VISIBLE
            textStyleLayout.visibility = View.GONE
            colorPadLayoutText.visibility = View.GONE
        }
        textColorPick.setOnClickListener {
            colorPadLayoutText.visibility = View.VISIBLE
            fontLayout.visibility = View.GONE
            textStyleLayout.visibility = View.GONE
        }
        closeTextStyleBtn.setOnClickListener {
            textStyleLayout.visibility =View.GONE
        }
        closeColorPickerText.setOnClickListener {
            colorPadLayoutText.visibility = View.GONE
        }
        addText.setOnClickListener {
            addTextView.visibility = View.VISIBLE
        }
    }

    override fun onBackPressed() {
        when(menuCheck){
            0 -> finish()
            1-> {
                menuCheck = 0
                colorPadLayoutText.visibility = View.GONE
                textStyleLayout.visibility =View.GONE
                fontLayout.visibility =View.GONE
                textController.visibility = View.GONE
            }
            2-> {
                menuCheck = 0
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
        saveBtn.text = "저장"
    }

    @SuppressLint("MissingSuperCall")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode==200 && resultCode == RESULT_OK && data!=null && data.data != null){
            var selectedImageUri = data.data
            var bitmap = MediaStore.Images.Media.getBitmap(applicationContext.contentResolver, selectedImageUri)
            ganpanFrame.setBackgroundDrawable(BitmapDrawable(bitmap))
        }else if(requestCode==300 && resultCode == RESULT_OK && data!=null && data.data != null){
            var selectedImageUri = data?.data
            addImageView.setImageURI(selectedImageUri)
        }
    }

    var oldXvalue : Float = 0.0f
    var oldYvalue : Float = 0.0f

    fun checkMenu(cnt : Int) : Boolean{
        if(cnt != 0) return true
        else return false
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        var width = ganpanFrame.width
        var height = ganpanFrame.height
        if(event?.action == MotionEvent.ACTION_DOWN){
            oldXvalue = event.x
            oldYvalue = event.y
        }else if(event?.action == MotionEvent.ACTION_MOVE){
            v?.x = event.rawX - oldXvalue
            v?.y = event.rawY - oldYvalue
        }else if(event?.action == MotionEvent.ACTION_UP){
            if(v?.x!! > width &&v.y > height){
                v.x = width.toFloat()
                v.y = height.toFloat()
            }else if (v.x < 0 && v.y > height) {
                v.x = 0.0F
                v.y = height.toFloat()
            } else if (v.x > width && v.y < 0) {
                v.x = width.toFloat()
                v.y = 0.0F
            } else if (v.x < 0 && v.y < 0) {
                v.x = 0.0F
                v.y = 0.0F
            } else if (v.x < 0 || v.x > width) {
                if (v.x < 0) {
                    v.x= 0.0F
                    v.setY(event.rawY - oldYvalue - v.height);
                } else {
                    v.x = width.toFloat()
                    v.y = event.rawY - oldYvalue - v.height
                }
            } else if (v.y < 0 || v.y > height) {
                if (v.y < 0) {
                    v.x = event.rawX - oldXvalue
                    v.y = 0.0F
                } else {
                    v.x = event.rawX - oldXvalue
                    v.y = height.toFloat()
                }
            }
        }
        return true;
    }
}
