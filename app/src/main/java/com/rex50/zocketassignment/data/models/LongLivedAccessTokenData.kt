package com.rex50.zocketassignment.data.models

import java.io.Serializable

data class LongLivedAccessTokenData(
    val access_token: String,
    val expires_in: String,
    val token_type: String
): Serializable