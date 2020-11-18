package h2mud2.ganpanproject.gandan.activity.account

import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import h2mud2.ganpanproject.gandan.R
import kotlinx.android.synthetic.main.activity_quiksilver.*


class QuiksilverActivity : AppCompatActivity() {

    val weburl = "http://bannermall.co.kr/board/free/list.html?board_no=1"
    val TAG = "QuikSilver Activity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiksilver)

        quiksilver_webView.settings.javaScriptEnabled = true // 자바 스크립트 허용
        /* 웹뷰에서 새 창이 뜨지 않도록 방지하는 구문 */
        quiksilver_webView.webViewClient = WebViewClient()
        quiksilver_webView.webChromeClient = WebChromeClient()

        quiksilver_webView.loadUrl("http://bannermall.co.kr/") // 링크 주소를 load 함

    }

    override fun onBackPressed() {
        if(quiksilver_webView.canGoBack()){
            quiksilver_webView.goBack()
        }else{
            super.onBackPressed() // 본래의 백버튼 수행
        }
    }
}