package com.ruviapps.tacklingnephrotic.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.facebook.*
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.firebase.ui.auth.ErrorCodes
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.ruviapps.tacklingnephrotic.LoginActivity
import com.ruviapps.tacklingnephrotic.MainActivity
import com.ruviapps.tacklingnephrotic.R
import com.ruviapps.tacklingnephrotic.databinding.FragmentBottomSheetBinding
import com.ruviapps.tacklingnephrotic.ui.login.LoginViewModel
import com.ruviapps.tacklingnephrotic.ui.login.LoginViewModel.*
import com.ruviapps.tacklingnephrotic.utility.NavigationCommand

/**
 * A simple [Fragment] subclass.
 * Use the [BottomSheet.newInstance] factory method to
 * create an instance of this fragment.
 */

class BottomSheet : BottomSheetDialogFragment() {

  //  private lateinit var auth: FirebaseAuth
    /*private val callbackManager = CallbackManager.Factory.create()   */

    private lateinit var binding : FragmentBottomSheetBinding
    private val sharedViewModel : LoginViewModel by activityViewModels()

    private val signInLaunch = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ){
            result ->
sharedViewModel.handleResult(result)
     observeNavigation()
    }

    private fun observeNavigation(){
        sharedViewModel.navigation.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { command ->
                when (command) {
                    is NavigationCommand.ToDirection ->
                        findNavController().navigate(command.directions)
                    is NavigationCommand.ShowError -> {
                        Toast.makeText(requireContext(), command.errMsg, Toast.LENGTH_SHORT).show()
                    }
                    else -> {

                    }
                }
            }
        }

    }


    private fun handleResult(result: FirebaseAuthUIAuthenticationResult) {
        val response = result.idpResponse
        if(result.resultCode == AppCompatActivity.RESULT_OK){
            val intent = Intent(requireActivity(),MainActivity::class.java).apply {
                putExtra(LoginActivity.INTENT_EXTRA_USERNAME,"${response?.user}")
                putExtra(LoginActivity.INTENT_EXTRA_EMAIL,"${response?.email}")
                putExtra(LoginActivity.INTENT_EXTRA_PHONE,"${response?.phoneNumber}")
                putExtra(LoginActivity.INTENT_EXTRA_IS_NEW_USER,"${response?.isNewUser}")
            }
            startActivity(intent)
            requireActivity().finish()
        }else{
            if(response == null) {
                Toast.makeText(requireContext(), getString(R.string.login_failed_msg), Toast.LENGTH_SHORT).show()
            }else{
                when(response.error?.errorCode){
                    ErrorCodes.NO_NETWORK -> Toast.makeText(requireContext(),getString(R.string.no_internet_exclaim),Toast.LENGTH_LONG).show()
                    ErrorCodes.UNKNOWN_ERROR -> Toast.makeText(requireContext(),getString(R.string.unknown_error_with_error_code)
                            +  "${response.error?.errorCode}",Toast.LENGTH_SHORT).show()
                    else-> Toast.makeText(requireContext(),getString(R.string.login_failed_msg2),Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentBottomSheetBinding.inflate(layoutInflater)
/*
        auth = Firebase.auth
        FacebookSdk.sdkInitialize(requireContext())
*/
        // Inflate the layout for this fragment
        return binding.root
    }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       binding.googleButton.setOnClickListener {
           signInLaunch.launch(sharedViewModel.getSignInIntent(Provider.GOOGLE_PROVIDER))
       }

       val phoneButton = binding.phoneBtn
       phoneButton.setOnClickListener {
           signInLaunch.launch(sharedViewModel.getSignInIntent(Provider.PHONE_PROVIDER))

       }

/*
       val faceBookLoginButton = binding.facebookBtn
       faceBookLoginButton.setReadPermissions("email", "public_profile")

       faceBookLoginButton.registerCallback(callbackManager,object :
                FacebookCallback<LoginResult> {
                override fun onSuccess(loginResult: LoginResult) {
                    Log.d("facebook", "facebook:onSuccess:$loginResult")
                    handleFacebookAccessToken(loginResult.accessToken)
                }

                override fun onCancel() {
                    Log.d("facebook", "facebook:onCancel")
                }

                override fun onError(error: FacebookException) {
                    Log.d("facebook", "facebook:onError", error)
                }
            })

*/

       }


    /*override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // Pass the activity result back to the Facebook SDK
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }
    private fun handleFacebookAccessToken(token: AccessToken) {
        Log.d("facebook", "handleFacebookAccessToken:$token")

        val credential = FacebookAuthProvider.getCredential(token.token)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("facebook", "signInWithCredential:success")
                    val user = auth.currentUser
                    Log.d("facebook","User = $user")
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("facebook", "signInWithCredential:failure", task.exception)
                    Toast.makeText(requireContext(), "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    Log.d("facebook","Error login using facebook")
                }
            }
    }*/
}