package com.ruviapps.tacklingnephrotic

import android.content.Intent
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.ruviapps.tacklingnephrotic.ui.login.BottomSheet


class LoginActivity : AppCompatActivity() {

    private val firebaseUser = FirebaseAuth.getInstance().currentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
    //Use Case 1 -> if user have already logged in using email or google sign in method
       //don't show login activity, just start the app..
        if(firebaseUser != null){
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }
       //User Case 2 -> showing login activity for first time.
       setContentView(R.layout.activity_login)

        val signIn = findViewById<TextView>(R.id.signIn)

        val wordTwo: Spannable = SpannableString(getString(R.string.sign_in))

        wordTwo.setSpan(ForegroundColorSpan(R.color.primaryTextColor),
            0,
            wordTwo.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        wordTwo.setSpan(
            UnderlineSpan(),
            0,wordTwo.length,
            Spannable.SPAN_INCLUSIVE_INCLUSIVE)
        signIn.append(wordTwo)
        signIn.isClickable = true

        val getStartedButton = findViewById<Button>(R.id.getStartedButton)
        getStartedButton.setOnClickListener {
           //let user use our app without login but will create a user
        }

        signIn.setOnClickListener {
            //use case 3 -> if user has previously installed this app and logged in using provided methods
            //then show user an option to login
            val bottomFragment = BottomSheet()
            bottomFragment.show(supportFragmentManager,bottomFragment.tag)
        }


    }




}