package com.example.android.exercisetracker.utils

import androidx.room.TypeConverter
import org.threeten.bp.LocalDate

class Converters {


    @TypeConverter
    fun fromLocalDateToLong(localDate: LocalDate): Long {
        return localDate.toEpochDay()
    }

    @TypeConverter
    fun fromLongToLocalDate(dateLong: Long): LocalDate {
        return LocalDate.ofEpochDay(dateLong)
    }
}