package com.rayman.jsonpad.ui.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.rayman.jsonpad.data.local.Note
import com.rayman.jsonpad.data.repository.JsonFileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class JsonViewModel @Inject constructor(
    private val jsonFileRepository: JsonFileRepository
) : ViewModel() {

    fun saveJson(uri: Uri, data: List<Note>) {
        jsonFileRepository.writeToFile(uri, data)  // Directly call the function
    }

    fun loadJson(uri: Uri): List<Note>? {
        return jsonFileRepository.loadJson(uri)
    }
}
