package com.rex50.zocketassignment.ui.login

import android.util.Log
import androidx.lifecycle.ViewModel
import com.facebook.AccessToken
import com.rex50.zocketassignment.data.models.LongLivedTokenRequest
import com.rex50.zocketassignment.data.repos.meta.auth.AuthRepo
import com.rex50.zocketassignment.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FragLoginViewModel
@Inject
constructor (
    private val authRepo: AuthRepo
): ViewModel() {

    companion object {
        private const val TAG = "FragFBLoginViewModel"
    }

    fun isLoggedIn(status: (Boolean) -> Unit) {
        val accessToken = AccessToken.getCurrentAccessToken()
        accessToken?.let { token ->
            val isLoggedIn = !accessToken.isExpired
            if(isLoggedIn) {
                Log.i(TAG, "Still logged in: ${token.userId} - ${token.token}")
            } else
                Log.i(TAG, "FB token expired")
            status(isLoggedIn)
            return
        }
        status(false)
    }

    suspend fun fetchLongLivedToken(request: LongLivedTokenRequest): Boolean {
        return when(val result = authRepo.getLongLivedToken(request)) {
            is Result.Success -> {
                authRepo.cacheLongLivedToken(result.data)
                true
            }

            is Result.Failure -> {
                false
            }
        }
    }
}