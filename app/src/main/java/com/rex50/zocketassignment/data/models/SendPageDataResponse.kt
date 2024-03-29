package com.rex50.zocketassignment.data.models

data class SendPageDataResponse(
    val result: List<Page>,
    val status: String
)

data class Page(
    val about: String,
    val access_token: String,
    val birthday: String,
    val business: String,
    val business_id: String,
    val category: String,
    val company_overview: String,
    val contact_address: String,
    val cover: String,
    val current_location: String,
    val description: String,
    val emails: String,
    val engagement: String,
    val fan_count: String,
    val followers_count: String,
    val founded: String,
    val has_whatsapp_business_number: String,
    val has_whatsapp_number: String,
    val id: String,
    val link: String,
    val location: String,
    val log: String,
    val name: String,
    val page_id: String,
    val phone: String,
    val picture: String,
    val rating_count: String,
    val username: String,
    val website: String
)