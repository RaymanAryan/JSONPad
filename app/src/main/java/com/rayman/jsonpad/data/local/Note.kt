package com.rayman.jsonpad.data.local

import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import androidx.room.Entity
import androidx.room.PrimaryKey


@JsonClass(generateAdapter = true) // Enables Moshi JSON conversion
@Entity(tableName = "notes")
@Keep data class Note(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @Json(name = "title") var title: String,
    @Json(name = "content") var content: String,
    @Json(name = "category") var category: String,
    @Json(name = "created_at") val createdAt: Long = System.currentTimeMillis(),
    @Json(name = "updated_at") var updatedAt: Long = System.currentTimeMillis()
)
