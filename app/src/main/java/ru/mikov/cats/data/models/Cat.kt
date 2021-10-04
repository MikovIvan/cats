package ru.mikov.cats.data.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Cat(
    @Json(name = "id") val id: String,
    @Json(name = "url") val url: String
)
