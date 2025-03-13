package com.teamwizdum.wizdum.feature.common.extensions

import android.net.Uri
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


inline fun <reified T> Json.encodeToUri(value: T): String {
    return Uri.encode(encodeToString(value))
}

inline fun <reified T> Json.decodeFromUri(value: String): T {
    return decodeFromString(Uri.decode(value))
}

fun formatBasicDateTime(input: String): String {
    return try {
        val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val outputFormatter = DateTimeFormatter.ofPattern("yyyy년 M월 d일")

        val parsedDate = LocalDateTime.parse(input, inputFormatter)
        return parsedDate.format(outputFormatter)
    } catch (e: Exception) {
        ""
    }
}


fun formatIsoDateTime(input: String): String {
    return try {
        val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSS")
        val outputFormatter = DateTimeFormatter.ofPattern("yyyy년 M월 d일")

        val parsedDate = LocalDateTime.parse(input, inputFormatter)
        return parsedDate.format(outputFormatter)
    } catch (e: Exception) {
        ""
    }
}