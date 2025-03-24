package com.rayman.jsonpad.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.rayman.jsonpad.ui.components.SimpleBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(title: String, navigate: NavHostController) {
    val coroutine = rememberCoroutineScope()

    Scaffold(
        topBar = {
            SimpleBar(title = title, navigate = navigate, coroutine = coroutine)
        }
    ) { paddingValues ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Settings()
        }
    }
}

@Composable
fun Settings() {
    Text("Settings", modifier = Modifier.size(79.dp))
}