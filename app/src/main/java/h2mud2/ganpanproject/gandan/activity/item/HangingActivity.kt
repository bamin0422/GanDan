package h2mud2.ganpanproject.gandan.activity.item

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import h2mud2.ganpanproject.gandan.R
import h2mud2.ganpanproject.gandan.activity.tool.DesignToolActivity
import h2mud2.ganpanproject.gandan.adapter.HorizonAdapter

class HangingActivity : AppCompatActivity(){
    
    /* 2020.09.28 / 민대인
    현수막은 아이템이 따로 없어 사진과 
     */

    lateinit var backBtn: Button
    lateinit var hangingItemRecyclerView : RecyclerView
    lateinit var itemAdapter : HorizonAdapter
    lateinit var designBtn : Button
    lateinit var itemClicked : LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_hanging)

        designBtn = findViewById(R.id.design_btn)

        designBtn.setOnClickListener {
            val intent = Intent(applicationContext, DesignToolActivity::class.java)
            startActivity(intent)
        }

        backBtn = findViewById(R.id.back_btn)

        backBtn.setOnClickListener {
            finish()
        }
    }
}
