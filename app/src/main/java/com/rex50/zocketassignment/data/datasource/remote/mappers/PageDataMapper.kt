package com.rex50.zocketassignment.data.datasource.remote.mappers

import com.rex50.zocketassignment.data.models.Page
import com.rex50.zocketassignment.data.models.PageData

object PageDataMapper {

    fun pageDataToPage(page: PageData): Page {
        return Page(
            about = page.about,
            access_token = page.access_token,
            birthday = "",
            business = page.business.name,
            business_id = page.business.id,
            category = page.category,
            company_overview = "",
            contact_address = "",
            cover = "",
            current_location = "",
            description = "",
            emails = page.emails.toString(),
            engagement = page.engagement.count.toString(),
            fan_count = page.fan_count.toString(),
            followers_count = page.followers_count.toString(),
            founded = "",
            has_whatsapp_business_number = page.has_whatsapp_business_number.toString(),
            has_whatsapp_number = page.has_whatsapp_number.toString(),
            id = page.id,
            link = page.link,
            location = "",
            log = "",
            name = page.name,
            page_id = page.id,
            phone = page.phone,
            picture = page.picture.data.url,
            rating_count = page.rating_count.toString(),
            username = "",
            website = page.website
        )
    }

}