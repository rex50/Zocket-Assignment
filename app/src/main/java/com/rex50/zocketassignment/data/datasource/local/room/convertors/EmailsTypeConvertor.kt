package com.rex50.zocketassignment.data.datasource.local.room.convertors

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class EmailsTypeConvertor {

    private val gson by lazy { Gson() }

    @TypeConverter
    fun fromEmailsList(emails: List<String>): String {
        return gson.toJson(emails)
    }

    @TypeConverter
    fun toEmailsList(emails: String): List<String> {
        val type = object : TypeToken<List<String>>(){}.type
        return gson.fromJson(emails, type)
    }
}