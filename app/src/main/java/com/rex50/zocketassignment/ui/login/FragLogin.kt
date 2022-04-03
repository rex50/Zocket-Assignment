package com.rex50.zocketassignment.ui.login

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.facebook.*
import com.facebook.login.LoginResult
import com.rex50.zocketassignment.R
import com.rex50.zocketassignment.data.models.LongLivedTokenRequest
import com.rex50.zocketassignment.databinding.FragLoginBinding
import com.rex50.zocketassignment.ui.base.BaseFragmentWithListener
import com.rex50.zocketassignment.utils.extensions.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class FragLogin : BaseFragmentWithListener<FragLoginBinding, FragLogin.OnFragFBLoginInteractionListener>() {

    companion object {
        private const val TAG = "FragFBLogin"

        private const val EMAIL = "email"
        private const val PUBLIC_PROFILE = "public_profile"
        private const val BUSINESS_MANAGEMENT = "business_management"

        fun newInstance() = FragLogin()
    }

    private val viewModel: FragLoginViewModel by viewModels()

    private lateinit var fbCallbackManager: CallbackManager

    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragLoginBinding {
        return FragLoginBinding.inflate(inflater, container, false)
    }

    override fun initView() {
        initLoginButton()
    }

    override fun load() {
        checkIfLoggedIn()
    }

    private fun checkIfLoggedIn() {
        viewModel.isLoggedIn { isLoggedIn ->
            if(isLoggedIn) {
                listener?.onLoggedIn()
            }
        }
    }

    private fun initLoginButton() {
        fbCallbackManager = CallbackManager.Factory.create()
        binding?.loginButton?.apply {
            setPermissions(listOf(EMAIL, PUBLIC_PROFILE, BUSINESS_MANAGEMENT))
            fragment = this@FragLogin
            registerCallback(fbCallbackManager, object : FacebookCallback<LoginResult?> {
                override fun onSuccess(result: LoginResult?) {
                    result?.accessToken?.let { token ->
                        // onLoggedInSuccess(token)
                        listener?.onLoggedIn()
                    }
                }

                override fun onCancel() {
                    showToast("Please login to continue linking your page")
                }

                override fun onError(exception: FacebookException) {
                    Log.e(TAG, "onError: ", exception)
                    showToast("Problem while logging in")
                }
            })
        }
    }

    private fun onLoggedInSuccess(token: AccessToken) = fragScope?.launch {
        // Get long lived token (This should be fetched by server)
        val isSuccess = viewModel.fetchLongLivedToken(
            LongLivedTokenRequest(
                getString(R.string.facebook_app_id),
                getString(R.string.facebook_app_secret),
                token.token
            )
        )

        if(isSuccess) {
            Log.i(TAG, "Login success: ${token.userId} - ${token.token}")
            listener?.onLoggedIn()
        } else {
            Log.i(TAG, "Failed while getting long lived token")
            showToast("Problem with login. Try logout and login again")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        fbCallbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }

    interface OnFragFBLoginInteractionListener {
        fun onLoggedIn()
    }

}