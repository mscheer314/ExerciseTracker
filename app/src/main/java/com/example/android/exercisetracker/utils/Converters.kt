package com.example.android.exercisetracker.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time?.toLong()
    }

    @TypeConverter
    fun stringToItem(value: String): List<Long> {
        val listItems = object : TypeToken<Long>() {}.type
        return Gson().fromJson(value, listItems)
    }

    @TypeConverter
    fun itemToString(list: List<Long>): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}