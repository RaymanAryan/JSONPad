package com.rayman.jsonpad.data.repository

import android.content.Context
import android.net.Uri
import com.rayman.jsonpad.data.local.Note
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.IOException
import javax.inject.Inject

class JsonFileRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    moshi: Moshi
) {

    private val adapter = moshi.adapter<List<Note>>(
        Types.newParameterizedType(List::class.java, Note::class.java)
    )

    fun writeToFile(uri: Uri, notes: List<Note>) {
        try {
            val jsonString = adapter.toJson(notes)
            context.contentResolver.openOutputStream(uri)?.use { outputStream ->
                outputStream.write(jsonString.toByteArray())
            }
        } catch (e: IOException) {
            e.printStackTrace()
            // Handle error (e.g., log it or notify the user)
        }
    }

    fun loadJson(uri: Uri): List<Note>? {
        return try {
            context.contentResolver.openInputStream(uri)?.bufferedReader()?.use { reader ->
                val jsonString = reader.readText()
                adapter.fromJson(jsonString) // ✅ Correctly converting to List<Note>
            }
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }
}