package h2mud2.ganpanproject.gandan.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import h2mud2.ganpanproject.gandan.R

class SplashActivity: AppCompatActivity() {
    
    /* 2020.09.27 / 민대인
    간단 인트로
     */
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.intro)

        Handler().postDelayed({
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }, 2000)
    }
}
