package h2mud2.ganpanproject.gandan.activity.item

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import h2mud2.ganpanproject.gandan.R
import h2mud2.ganpanproject.gandan.adapter.HorizonAdapter

class HangingActivity : AppCompatActivity(){

    lateinit var backBtn: Button
    lateinit var hangingItemRecyclerView : RecyclerView
    lateinit var itemAdapter : HorizonAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_hanging)

        backBtn = findViewById(R.id.back_btn)

        hangingItemRecyclerView = findViewById(R.id.designItemRecycler)
        hangingItemRecyclerView.adapter = itemAdapter
        hangingItemRecyclerView.layoutManager = GridLayoutManager(this, 2)

        backBtn.setOnClickListener {
            finish()
        }
    }
}