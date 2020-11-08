package h2mud2.ganpanproject.gandan.activity.tool

import android.content.ClipData
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import h2mud2.ganpanproject.gandan.R
import h2mud2.ganpanproject.gandan.adapter.GridAdapter
import h2mud2.ganpanproject.gandan.adapter.HorizonAdapter
import h2mud2.ganpanproject.gandan.crawler.WebCrawler
import h2mud2.ganpanproject.gandan.decoration.RecyclerViewDecoration
import h2mud2.ganpanproject.gandan.fragment.FireStoreManager
import h2mud2.ganpanproject.gandan.model.Item
import kotlinx.coroutines.*
import org.jetbrains.anko.find

class DesignToolActivity : AppCompatActivity(){

    lateinit var cancelBtn : Button
    lateinit var saveBtn : Button
    lateinit var penBtn : ImageButton
    lateinit var photoBtn : ImageButton
    lateinit var textBtn : ImageButton
    lateinit var sizeBtn : ImageButton
    lateinit var backgroundBtn : ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_design)

        cancelBtn = findViewById(R.id.cancel_btn)
        saveBtn = findViewById(R.id.save_btn)
        penBtn = findViewById(R.id.penAdder)
        photoBtn = findViewById(R.id.photoAdder)
        textBtn = findViewById(R.id.textAdder)
        sizeBtn = findViewById(R.id.sizeEditor)
        backgroundBtn = findViewById(R.id.backgroundEditor)

        cancelBtn.setOnClickListener {finish()}
        saveBtn.setOnClickListener {  }
        penBtn.setOnClickListener {  }
        photoBtn.setOnClickListener {  }
        textBtn.setOnClickListener {  }
        sizeBtn.setOnClickListener {  }
        backgroundBtn.setOnClickListener {  }

    }
}