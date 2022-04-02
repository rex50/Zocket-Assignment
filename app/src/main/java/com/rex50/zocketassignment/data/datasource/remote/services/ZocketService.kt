package com.rex50.zocketassignment.data.datasource.remote.services

import com.rex50.zocketassignment.data.models.Page
import com.rex50.zocketassignment.data.models.SendPageDataResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ZocketService {

    @POST("fb_page_details")
    suspend fun sendPage(
        @Body pageDetails: Page
    ): Response<SendPageDataResponse>

}