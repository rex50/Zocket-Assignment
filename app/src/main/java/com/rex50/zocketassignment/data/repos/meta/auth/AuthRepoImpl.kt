package com.rex50.zocketassignment.data.repos.meta.auth

import com.rex50.zocketassignment.data.datasource.interfaces.UserDetailsProvider
import com.rex50.zocketassignment.data.datasource.remote.AuthRemoteDataSourceImpl
import com.rex50.zocketassignment.data.models.LongLivedAccessTokenData
import com.rex50.zocketassignment.data.models.LongLivedTokenRequest
import com.rex50.zocketassignment.utils.Result
import javax.inject.Inject

class AuthRepoImpl
@Inject
constructor(
    private val remoteDataSourceImpl: AuthRemoteDataSourceImpl,
    private val userDetailsProviderImpl: UserDetailsProvider
): AuthRepo {

    override suspend fun getLongLivedToken(request: LongLivedTokenRequest): Result<LongLivedAccessTokenData> {
        return remoteDataSourceImpl.getLongLivedToken(request)
    }

    override suspend fun cacheLongLivedToken(longLivedAccessTokenData: LongLivedAccessTokenData) {
        userDetailsProviderImpl.updateLongLivedAccessToken(longLivedAccessTokenData)
    }
}