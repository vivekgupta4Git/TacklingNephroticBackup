package com.ruviapps.tacklingnephrotic

import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.ruviapps.tacklingnephrotic.ui.BottomSheet


class LoginActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
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
            val bottomFragment = BottomSheet()
            bottomFragment.show(supportFragmentManager,bottomFragment.tag)
        }


    }


}