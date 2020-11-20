package h2mud2.ganpanproject.gandan.activity

import android.Manifest.permission.CAMERA
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.FacebookSdk
import com.facebook.login.LoginBehavior
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.common.collect.Lists.asList
import com.google.common.primitives.Bytes.asList
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import h2mud2.ganpanproject.gandan.R
import kotlinx.android.synthetic.main.account_login.*
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.Arrays


class LoginActivity : AppCompatActivity(){

    var auth: FirebaseAuth? = null

    var googleSignInClient : GoogleSignInClient? = null
    var RC_SIGN_IN = 1000

    var callbackManager = CallbackManager.Factory.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.account_login)
        auth = FirebaseAuth.getInstance()
        FacebookSdk.sdkInitialize(getApplicationContext());

        btn_signup.setOnClickListener{
            createEmailId()
        }

        var gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        btn_google.setOnClickListener{
            var signInIntent = googleSignInClient?.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }
        btn_facebook.setOnClickListener{
            facebookLogin()
        }
        btn_signin.setOnClickListener{
            loginEmail()
        }
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        var account = GoogleSignIn.getLastSignedInAccount(this)
        if(account != null){
            moveMainActivity(FirebaseAuth.getInstance().currentUser)
        }
    }

    override fun onResume(){
        super.onResume()
        moveMainActivity(FirebaseAuth.getInstance().currentUser)
    }

    fun createEmailId(){
        var email = email_edittext.text.toString()
        var password = password_edittext.text.toString()

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if(task.isSuccessful){
                moveMainActivity(task.result?.user)
            }else{
                moveMainActivity(null)
            }
        }
    }

    fun loginEmail(){
        var email = email_edittext.text.toString()
        var password = password_edittext.text.toString()

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if(task.isSuccessful){
                moveMainActivity(task.result?.user)
            }else{
                moveMainActivity(null)
            }
        }
    }

    fun facebookLogin(){
        LoginManager.getInstance().loginBehavior = LoginBehavior.WEB_VIEW_ONLY
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile","email"))
        LoginManager.getInstance().registerCallback(callbackManager, object : FacebookCallback<LoginResult>{
            override fun onSuccess(result: LoginResult?) {
                firebaseAuthWithFacebook(result)
            }

            override fun onCancel() {

            }

            override fun onError(error: FacebookException?) {

            }

        })
    }

    fun firebaseAuthWithFacebook(result: LoginResult?){
        var credential = FacebookAuthProvider.getCredential(result?.accessToken?.token!!)
        FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener{task->
            if(task.isSuccessful){
                moveMainActivity(task.result?.user)
            }else{
                moveMainActivity(null)
            }
        }
    }

    fun firebaseAuthWithGoogle(acct: GoogleSignInAccount?){
        var credential = GoogleAuthProvider.getCredential(acct?.idToken, null)
        FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener(this){ task ->
            if(task.isSuccessful){
                moveMainActivity(task.result?.user)
            }else{
                moveMainActivity(null)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager.onActivityResult(requestCode, resultCode, data)
        if(requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account!!)
            }catch (e: ApiException){
                // Google Sign In failed
            }
        }
    }

    private fun moveMainActivity(user: FirebaseUser?){
        if(user != null){
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

}