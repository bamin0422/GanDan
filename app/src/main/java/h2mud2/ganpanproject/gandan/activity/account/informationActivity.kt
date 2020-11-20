package h2mud2.ganpanproject.gandan.activity.account

import android.content.pm.ActivityInfo
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import androidx.recyclerview.widget.DividerItemDecoration
import com.bumptech.glide.Glide
import h2mud2.ganpanproject.gandan.R
import h2mud2.ganpanproject.gandan.model.NoticeItem
import kotlinx.android.synthetic.main.activity_notice.*
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import java.net.URI
import java.net.URL

class informationActivity : AppCompatActivity() {

    /* 2020.11.13 / 김학균
     웹사이트에 있는 사진을 가져와서 이미지뷰에 적용시킴
     가로가 큰 이미지랑 강제적으로 휴대폰 화면이 가로로 되도록 함.
      */

    val weburl = "http://bannermall.co.kr/shopinfo/guide.html"
    val TAG = "Information Activity"

    override fun onCreate(savedInstanceState: Bundle?) {
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_information)
    }

}