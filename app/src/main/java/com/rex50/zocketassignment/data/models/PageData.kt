package com.rex50.zocketassignment.data.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PageData(
    val about: String,
    val access_token: String,
    @Embedded(prefix = "business") val business: Business,
    val category: String,
    val emails: List<String>,
    @Embedded(prefix = "engagement_") val engagement: Engagement,
    val fan_count: Int,
    val followers_count: Int,
    val has_whatsapp_business_number: Boolean,
    val has_whatsapp_number: Boolean,
    @PrimaryKey val id: String,
    val link: String,
    val name: String,
    val phone: String,
    @Embedded(prefix = "picture_") val picture: Picture,
    val rating_count: Int,
    val website: String
)

data class Business(
    val id: String,
    val name: String
)

data class Engagement(
    val count: Int,
    val social_sentence: String
)

data class Picture(
    @Embedded(prefix = "image_data_") val data: ImageData
)

data class ImageData(
    val height: Int,
    val is_silhouette: Boolean,
    val url: String,
    val width: Int
)


/*
{
    "name": "@pavitra_raut",
    "access_token": "EAAJennU4iw0BAD8kE94QBv9y0yW5ZAAgXegYl7gsIQx66IOwyMj2GPFOl8T2sFZCCBs9dZCp5ZBGxbqIwA3QEqidZADukuXm1zXAC4ouFAwAgW9uHKdR0onGP0CI6FLMJghtfIIB7P8wGDk7elNklwEkOFvEopsR6xoFY1OfC5ZCqe5eTGtBac",
    "id": "1995324997441201",
    "category": "Artist",
    "about": "Just a simple page",
    "business": {
        "id": "136284384109210",
        "name": "@pavitra_raut"
    },
    "emails": [
        "pareshraut50@gmail.com"
    ],
    "engagement": {
        "count": 0,
        "social_sentence": "Be the first of your friends to like this."
    },
    "fan_count": 0,
    "followers_count": 0,
    "has_whatsapp_business_number": false,
    "has_whatsapp_number": false,
    "link": "https://www.facebook.com/1995324997441201",
    "phone": "+917016258605",
    "rating_count": 0,
    "website": "https://rex50.github.com",
    "picture": {
        "data": {
            "height": 50,
            "is_silhouette": true,
            "url": "https://scontent.fblr8-1.fna.fbcdn.net/v/t1.30497-1/84702798_579370612644419_4516628711310622720_n.png?stp=c15.0.50.50a_cp0_dst-png_p50x50&_nc_cat=1&ccb=1-5&_nc_sid=dbb9e7&_nc_ohc=gUoFRg-xDdAAX9OnfCt&_nc_ht=scontent.fblr8-1.fna&edm=AJdBtusEAAAA&oh=00_AT9DIrzQBcGJWjw2K2n9CrsmzvR-Q8vgEIB37JVllMr1gQ&oe=626FFC89",
            "width": 50
        }
    }
}
 */