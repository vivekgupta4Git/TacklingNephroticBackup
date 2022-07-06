package com.ruviapps.tacklingnephrotic.ui

import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.ruviapps.tacklingnephrotic.LoginActivity
import com.ruviapps.tacklingnephrotic.MainActivity
import com.ruviapps.tacklingnephrotic.R
import com.ruviapps.tacklingnephrotic.databinding.FragmentBottomSheetBinding

/**
 * A simple [Fragment] subclass.
 * Use the [BottomSheet.newInstance] factory method to
 * create an instance of this fragment.
 */
class BottomSheet : BottomSheetDialogFragment() {

    private lateinit var binding : FragmentBottomSheetBinding
    private val providers = arrayListOf(
        AuthUI.IdpConfig.EmailBuilder().build(),
        AuthUI.IdpConfig.PhoneBuilder().build(),
        AuthUI.IdpConfig.GoogleBuilder().build())

    private val signInLaunch = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ){
            result ->
        run {
            if(result!=null) {
                startActivity(Intent(requireActivity(), MainActivity::class.java))
                dismiss()
                requireActivity().finish()
            }else
            {
                Snackbar.make(binding.root,"Login Failed",Snackbar.LENGTH_LONG).show()
                dismiss()
            }

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
       binding.googleButton.setOnClickListener {
           val sigInIntent = AuthUI.getInstance().createSignInIntentBuilder()
               .setAvailableProviders(providers)
               .setLogo(R.mipmap.ic_launcher)
               .setTheme(R.style.Theme_App_MyTheme)
               .build()
           signInLaunch.launch(sigInIntent)

       }

       }


}