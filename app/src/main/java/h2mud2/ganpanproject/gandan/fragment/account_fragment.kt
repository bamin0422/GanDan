package h2mud2.ganpanproject.gandan.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import h2mud2.ganpanproject.gandan.R
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager
import com.facebook.login.widget.LoginButton
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import kotlinx.android.synthetic.main.account_fragment.*
import com.google.firebase.auth.FirebaseAuth
import h2mud2.ganpanproject.gandan.activity.LoginActivity
import kotlinx.android.synthetic.main.account_fragment.view.*

class account_fragment: Fragment(){
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