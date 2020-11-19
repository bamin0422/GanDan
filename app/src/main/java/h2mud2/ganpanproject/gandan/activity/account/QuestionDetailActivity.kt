package h2mud2.ganpanproject.gandan.activity.account

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import h2mud2.ganpanproject.gandan.R

class QuestionDetailActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question_detail)

        var titleDetail = findViewById<TextView>(R.id.titleDetail)
        var contextDetail = findViewById<TextView>(R.id.ContentDetail)

        if(intent.hasExtra("title")){
            titleDetail.setText(intent.getStringExtra("title").toString())
        }
        if(intent.hasExtra("context")){
            contextDetail.setText(intent.getStringExtra("context").toString())
        }
    }
}