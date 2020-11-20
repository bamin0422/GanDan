package h2mud2.ganpanproject.gandan.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import h2mud2.ganpanproject.gandan.R
import com.facebook.login.LoginManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import h2mud2.ganpanproject.gandan.activity.*
import h2mud2.ganpanproject.gandan.activity.account.NoticeActivity
import h2mud2.ganpanproject.gandan.activity.account.QuestionActivity
import h2mud2.ganpanproject.gandan.activity.account.QuiksilverActivity
import h2mud2.ganpanproject.gandan.activity.account.informationActivity
import kotlinx.android.synthetic.main.account_fragment.view.*

class account_fragment: Fragment(){

    /* 2020.11.05 / 김학균
    사용자가 계정을 변경할 수 있도록 로그아웃 기능 추가 및
    공지사항, 배송안내, 이용안내, 자주하는 질문 클릭시 화면전환 추가
     */

    var googleSignInClient : GoogleSignInClient? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        val view = inflater.inflate(R.layout.account_fragment, container, false)
        view.btn_logout.setOnClickListener{
            logout()
        }

        var gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = context?.let { GoogleSignIn.getClient(it, gso) }

        view.text_Quiksilver.setOnClickListener {
            startActivity(Intent(activity, QuiksilverActivity::class.java))
        }

        view.text_Notice.setOnClickListener {
            startActivity(Intent(activity, NoticeActivity::class.java))
        }

        view.text_Information.setOnClickListener {
            startActivity(Intent(activity, informationActivity::class.java))
        }

        view.text_Question.setOnClickListener {
            startActivity(Intent(activity, QuestionActivity::class.java))
        }


        return view
    }

    fun logout(){

        FirebaseAuth.getInstance().signOut()

        //Google Session out
        googleSignInClient?.signOut()

        //Facebook Session out
        LoginManager.getInstance().logOut()

        startActivity(Intent(activity, LoginActivity::class.java))
    }
}