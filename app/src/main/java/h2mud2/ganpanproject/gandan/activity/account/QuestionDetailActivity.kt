package h2mud2.ganpanproject.gandan.activity.account

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import h2mud2.ganpanproject.gandan.R

class QuestionDetailActivity : AppCompatActivity() {

    /* 2020.11.13 / 김학균
    질문만 나와있는 Activity에서 클릭하게 된다면 대답을 추가적으로 나타내는 Activity로 전환
     */


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