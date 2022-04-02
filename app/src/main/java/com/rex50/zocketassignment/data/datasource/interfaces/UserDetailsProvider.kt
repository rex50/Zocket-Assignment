package com.rex50.zocketassignment.data.datasource.interfaces

import com.rex50.zocketassignment.data.models.LongLivedAccessTokenData

interface UserDetailsProvider {
    fun getUserAccessToken(): String?
    fun getUserId(): String?
    fun getSelectedPageId(): String
    fun updateSelectedPageId(pageId: String)
    fun updateLongLivedAccessToken(data: LongLivedAccessTokenData)
}