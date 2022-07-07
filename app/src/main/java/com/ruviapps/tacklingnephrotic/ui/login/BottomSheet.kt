package com.ruviapps.tacklingnephrotic.ui.login

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.ErrorCodes
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ruviapps.tacklingnephrotic.MainActivity
import com.ruviapps.tacklingnephrotic.R
import com.ruviapps.tacklingnephrotic.databinding.FragmentBottomSheetBinding

/**
 * A simple [Fragment] subclass.
 * Use the [BottomSheet.newInstance] factory method to
 * create an instance of this fragment.
 */
class BottomSheet : BottomSheetDialogFragment() {
companion object{
    val providers = arrayListOf(
        AuthUI.IdpConfig.EmailBuilder().build(),
        AuthUI.IdpConfig.GoogleBuilder().build())
}

    private lateinit var binding : FragmentBottomSheetBinding
 val signInLaunch = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ) { result ->
         this.onSignInResult(result)
 }
    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        val response = result.idpResponse
        if (result.resultCode == RESULT_OK) {
            // Successfully signed in
          startActivity(Intent(requireActivity(),MainActivity::class.java))
           requireActivity().finish()
        // ...
        } else {
            // Sign in failed. If response is null the user canceled the
            // sign-in flow using the back button.
            if(response === null)
                Toast.makeText(requireContext(),"Login Cancelled",Toast.LENGTH_LONG).show()
            else when(response.error?.errorCode) {
                // otherwise check response.getError().getErrorCode() and handle the error.
             ErrorCodes.NO_NETWORK ->   Toast.makeText(requireContext(), "No Internet", Toast.LENGTH_LONG).show()
                ErrorCodes.UNKNOWN_ERROR -> Toast.makeText(requireContext(),"Unknown Error",Toast.LENGTH_LONG).show()
            }
            dismiss()

            // ...
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentBottomSheetBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        return binding.root
    }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       binding.loginButton .setOnClickListener {
           val sigInIntent = AuthUI.getInstance().createSignInIntentBuilder()
               .setAvailableProviders(providers)
               .setIsSmartLockEnabled(false)        //for testing purpose only
               .setLogo(R.mipmap.ic_launcher)
               .setTheme(R.style.Theme_MyLoginTheme)
               .build()
           signInLaunch.launch(sigInIntent)

       }

       }



}