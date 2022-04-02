package com.rex50.zocketassignment.data.datasource.local.sharedPrefs

import com.facebook.AccessToken
import com.google.gson.Gson
import com.pixplicity.easyprefs.library.Prefs
import com.rex50.zocketassignment.data.datasource.interfaces.UserDetailsProvider
import com.rex50.zocketassignment.data.models.LongLivedAccessTokenData
import java.lang.Exception

class UserDetailsProviderImpl: UserDetailsProvider {
    override fun getUserAccessToken(): String? {
        val longLivedToken = getLongLivedToken()
        if(longLivedToken != null)
            return longLivedToken.access_token

        return AccessToken.getCurrentAccessToken()?.token
    }

    override fun getUserId(): String? {
        return AccessToken.getCurrentAccessToken()?.userId
    }

    override fun getSelectedPageId(): String {
        return Prefs.getString(SharedPrefsKeys.SELECTED_PAGE_ID, "")
    }

    override fun updateSelectedPageId(pageId: String) {
        Prefs.putString(SharedPrefsKeys.SELECTED_PAGE_ID, pageId)
    }

    override fun updateLongLivedAccessToken(data: LongLivedAccessTokenData) {
        val dataAsString = Gson().toJson(data)
        Prefs.putString(SharedPrefsKeys.LONG_LIVED_TOKEN, dataAsString)
    }

    private fun getLongLivedToken(): LongLivedAccessTokenData? {
        val data = Prefs.getString(SharedPrefsKeys.LONG_LIVED_TOKEN)
        return try {
            Gson().fromJson(data, LongLivedAccessTokenData::class.java)
        } catch (exception: Exception) {
            null
        }
    }
}