package com.rex50.zocketassignment.data.datasource.remote.services

import com.rex50.zocketassignment.data.models.LongLivedAccessTokenData
import com.rex50.zocketassignment.data.models.PageData
import com.rex50.zocketassignment.data.models.PagesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private const val DEFAULT_PAGE_FIELDS = "name,access_token,category,about,birthday,business,company_overview,contact_address,cover,current_location," +
    "description,emails,engagement,fan_count,followers_count,founded,has_whatsapp_business_number,has_whatsapp_number,link,location,phone,rating_count," +
    "username,website,picture"

interface MetaPageService {

    @GET("oauth/access_token")
    suspend fun getLongLivedToken(
        @Query("grant_type") grantType: String,
        @Query("client_id") clientId: String,
        @Query("client_secret") clientSecret: String,
        @Query("fb_exchange_token") shortLivedToken: String,
    ): Response<LongLivedAccessTokenData>

    @GET("me/accounts")
    suspend fun getPages(
        @Query("fields") fields: String = DEFAULT_PAGE_FIELDS,
        @Query("access_token") accessToken: String
    ): Response<PagesResponse>

    @GET("{pageId}")
    suspend fun getPage(
        @Path("pageId") pageId: String,
        @Query("fields") fields: String = DEFAULT_PAGE_FIELDS,
        @Query("access_token") accessToken: String
    ): Response<PageData>

}