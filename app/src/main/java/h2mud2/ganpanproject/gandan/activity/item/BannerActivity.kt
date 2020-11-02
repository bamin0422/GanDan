package h2mud2.ganpanproject.gandan.activity.item

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import h2mud2.ganpanproject.gandan.R

class BannerActivity : AppCompatActivity(){

    lateinit var backBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_banner)

        backBtn = findViewById(R.id.back_btn)

        backBtn.setOnClickListener {
            finish()
        }

    }
}