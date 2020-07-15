package com.example.android.exercisetracker.utils

import androidx.room.TypeConverter
import org.threeten.bp.LocalDate

class Converters {
//   @TypeConverter
//   fun fromTimestamp(value: Long?): Date? {
//       return value?.let { Date(it) }
//   }

//   fun fromLocalDate(localDate: LocalDate): Long? {
//       return localDate?


//       @TypeConverter
//       fun dateToTimestamp(date: Date?): Long? {
//           return date?.time?.toLong()
//       }

    @TypeConverter
    fun fromLocalDateToLong(localDate: LocalDate): Long {
        return localDate.toEpochDay()
    }

    @TypeConverter
    fun fromLongToLocalDate(dateLong: Long): LocalDate {
        return LocalDate.ofEpochDay(dateLong)
    }
}