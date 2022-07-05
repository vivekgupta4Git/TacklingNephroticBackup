package com.ruviapps.tacklingnephrotic.ui.welcome

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.ruviapps.tacklingnephrotic.R
import com.ruviapps.tacklingnephrotic.databinding.FragmentWelcomeBinding

class WelcomeFragment : Fragment() {

    companion object {
        const val TAG = "WelcomeFragment"
        const val SIGN_IN_REQUEST_CODE = 10
    }
    private lateinit var binding : FragmentWelcomeBinding
    private lateinit var viewModel: WelcomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentWelcomeBinding.inflate(inflater)
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.signInButton.setOnClickListener {
            startActivityForResult(
                AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setAvailableProviders(viewModel.getProviders())
                    .build(),
                    SIGN_IN_REQUEST_CODE
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
     if(requestCode == SIGN_IN_REQUEST_CODE){
         val response = IdpResponse.fromResultIntent(data)
         if(resultCode== Activity.RESULT_OK){
             Log.i(TAG,"Successfully signed in ${FirebaseAuth.getInstance()}")
         }else
         {
             Log.i(TAG,"Sign in Unsuccessful ${response?.error?.errorCode}")
         }
     }
    }

    override fun onStart() {
        super.onStart()
      if(viewModel.getAuth().currentUser != null){
            //user already sign in..
      }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(WelcomeViewModel::class.java)
        // TODO: Use the ViewModel
    }

}