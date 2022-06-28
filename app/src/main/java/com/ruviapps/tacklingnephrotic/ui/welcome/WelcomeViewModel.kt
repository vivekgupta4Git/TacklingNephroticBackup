package com.ruviapps.tacklingnephrotic.ui.welcome

import android.content.Context
import androidx.lifecycle.ViewModel
import com.firebase.ui.auth.AuthUI
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ruviapps.tacklingnephrotic.R


class WelcomeViewModel : ViewModel() {
    private val auth: FirebaseAuth = Firebase.auth

    fun getAuth(): FirebaseAuth =  auth
    fun getProviders() = arrayListOf(
        AuthUI.IdpConfig.PhoneBuilder().build(),
        AuthUI.IdpConfig.GoogleBuilder().build()
    )




    fun requestLogin(context : Context){
        /* Configure sign-in to request the user's ID, email address, and basic profile.
         ID and basic profile are included in DEFAULT_SIGN_IN.
         */
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        // Build a GoogleSignInClient with the options specified by gso.
        val mGoogleSignInClient = GoogleSignIn.getClient(context, gso);

    }

    fun signInRequest(context: Context){
      val   signInRequest = BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    // server's client id.
                    .setServerClientId(context.getString(R.string.client_id))
                    // Only show accounts previously used to sign in.
                    .setFilterByAuthorizedAccounts(true)
                    .build())
            .build()
    }

    // Check for existing Google Sign In account, if the user is already signed in
    // the GoogleSignInAccount will be non-null.
    fun getCurrentUser(context: Context) : GoogleSignInAccount?{
        return GoogleSignIn.getLastSignedInAccount(context)
    }

}