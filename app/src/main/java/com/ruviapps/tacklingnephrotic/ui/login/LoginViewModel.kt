package com.ruviapps.tacklingnephrotic.ui.login

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.ErrorCodes
import com.firebase.ui.auth.IdpResponse
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.ruviapps.tacklingnephrotic.R
import com.ruviapps.tacklingnephrotic.di.module.AppModule
import com.ruviapps.tacklingnephrotic.utility.Event
import com.ruviapps.tacklingnephrotic.utility.NavigationCommand
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlin.math.log

@HiltViewModel
class LoginViewModel @Inject constructor(    private val resourcesProvider: AppModule.ResourcesProvider
) : ViewModel() {


    /*private var _loginStatus = MutableLiveData<UserLoginStatus>()
    val loginStatus : LiveData<UserLoginStatus>
    get() = _loginStatus
*/
    private var _navigation = MutableLiveData< Event<NavigationCommand>>()
    val navigation : LiveData<Event<NavigationCommand>>
    get() = _navigation



    enum class Provider{
        EMAIL_PROVIDER,
        PHONE_PROVIDER,
        GOOGLE_PROVIDER
    }
    enum class UserLoginStatus{
        LOGIN_SUCCESS,
        LOGIN_FAILED
    }

    fun getSignInIntent(provider : Provider) : Intent {
        val providerToUse = when(provider){
            Provider.PHONE_PROVIDER -> arrayListOf(AuthUI.IdpConfig.PhoneBuilder().build())
            Provider.GOOGLE_PROVIDER -> arrayListOf(AuthUI.IdpConfig.GoogleBuilder().build())
            else ->
                arrayListOf(AuthUI.IdpConfig.EmailBuilder().build())
        }
        return AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setIsSmartLockEnabled(true)       //for testing purpose
            .setLogo(R.mipmap.ic_launcher)
            .setTheme(R.style.Theme_MyLoginTheme)
            .setAvailableProviders(providerToUse)
            .build()
    }


    fun handleResult(result : FirebaseAuthUIAuthenticationResult){
    val  loginResponse= result.idpResponse

        if(result.resultCode == Activity.RESULT_OK){
  //          _loginStatus.value = UserLoginStatus.LOGIN_SUCCESS

            if(loginResponse?.isNewUser == true){
                _navigation.value =   Event(NavigationCommand.ToDirection(LoginFragmentDirections.actionNavLoginFragmentToNavUserRole()))
            }else
                _navigation.value = Event(NavigationCommand.ToDirection(LoginFragmentDirections.actionNavLoginFragmentToNavResult()))

        }else
        {
    //        _loginStatus.value = UserLoginStatus.LOGIN_FAILED
            if(loginResponse== null)
                _navigation.value = Event((NavigationCommand.ShowError(resourcesProvider.getString(R.string.login_failed_msg))))

            if(loginResponse?.error?.errorCode == ErrorCodes.NO_NETWORK)
              _navigation.value = Event(  NavigationCommand.ShowError(resourcesProvider.getString(R.string.no_internet_exclaim)))

            _navigation.value = Event(NavigationCommand.ShowError(resourcesProvider.getString(R.string.login_failed_msg2)))
        }
    }

}