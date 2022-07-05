package com.ruviapps.tacklingnephrotic

import android.graphics.Color
import android.os.Bundle
import android.text.Html
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.ruviapps.tacklingnephrotic.databinding.ActivityLoginBinding


class LoginActivity : AppCompatActivity() {

    private lateinit var  binding : ActivityLoginBinding
    private val providers = arrayListOf(
        AuthUI.IdpConfig.EmailBuilder().build(),
        AuthUI.IdpConfig.PhoneBuilder().build(),
        AuthUI.IdpConfig.GoogleBuilder().build())
    private val signInLaunch = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ){
        result ->

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val signIn = binding.signIn

        val wordTwo: Spannable = SpannableString(getString(R.string.sign_in))

        wordTwo.setSpan(ForegroundColorSpan(Color.DKGRAY),
            0,
            wordTwo.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        wordTwo.setSpan(
            UnderlineSpan(),
            0,wordTwo.length,
            Spannable.SPAN_INCLUSIVE_INCLUSIVE)
        signIn.append(wordTwo)
        signIn.isClickable = true
        setContentView(binding.root)
        binding.getStartedButton.setOnClickListener {
           //let user use our app without login but will create a user
        }

        binding.signIn.setOnClickListener {
            //show bottom sheet dialog fragment having intent of firebase providers...

        }

    }
}