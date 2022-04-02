package com.rex50.zocketassignment.data.datasource.remote

import com.rex50.zocketassignment.data.datasource.remote.services.MetaPageService
import com.rex50.zocketassignment.data.models.LongLivedAccessTokenData
import com.rex50.zocketassignment.data.models.LongLivedTokenRequest
import com.rex50.zocketassignment.utils.Result
import com.rex50.zocketassignment.utils.extensions.toResult
import javax.inject.Inject

class AuthRemoteDataSourceImpl
@Inject
constructor(
    private val metaPageService: MetaPageService
) {

    suspend fun getLongLivedToken(request: LongLivedTokenRequest): Result<LongLivedAccessTokenData> {
        val response = metaPageService.getLongLivedToken(
            clientId = request.clientId,
            clientSecret = request.clientSecret,
            shortLivedToken = request.shortLivedToken,
            grantType = request.grantType
        )
        return response.toResult()
    }

}