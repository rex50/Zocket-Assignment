package com.rex50.zocketassignment.data.repos.meta.auth

import com.rex50.zocketassignment.data.models.LongLivedAccessTokenData
import com.rex50.zocketassignment.data.models.LongLivedTokenRequest
import com.rex50.zocketassignment.utils.Result

interface AuthRepo {
    suspend fun getLongLivedToken(request: LongLivedTokenRequest): Result<LongLivedAccessTokenData>
    suspend fun cacheLongLivedToken(longLivedAccessTokenData: LongLivedAccessTokenData)
}