package com.rex50.zocketassignment.data.models

data class LongLivedTokenRequest(
    val clientId: String,
    val clientSecret: String,
    val shortLivedToken: String,
    val grantType: String = "fb_exchange_token"
)