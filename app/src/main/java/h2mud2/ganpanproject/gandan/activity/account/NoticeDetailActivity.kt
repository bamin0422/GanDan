package h2mud2.ganpanproject.gandan.activity.account

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import h2mud2.ganpanproject.gandan.R

class NoticeDetailActivity : AppCompatActivity() {

    /* 2020.11.12 / 김학균
    공지사항의 제목을 클릭하게 되면 자세한 내용을 담고 있는 Activity로 전환
     */

    val weburl = "http://bannermall.co.kr/board/free/read.html?"
    val TAG = "Notice Activity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notice_detail)

        var titleDetail = findViewById<TextView>(R.id.titleDetail)
        var dateDetail = findViewById<TextView>(R.id.DateDetail)
        var contextDetail = findViewById<TextView>(R.id.ContentDetail)

        if(intent.hasExtra("title")){
            titleDetail.setText(intent.getStringExtra("title").toString())
        }
        if(intent.hasExtra("date")){
            dateDetail.setText(intent.getStringExtra("date").toString())
        }
        if(intent.hasExtra("context")){
            contextDetail.setText(intent.getStringExtra("context").toString())
        }
    }
}