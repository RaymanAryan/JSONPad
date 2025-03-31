package com.rayman.jsonpad.ui.screens

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.navigation.NavHostController
import com.rayman.jsonpad.ui.components.SimpleBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(title: String, navigate: NavHostController) {
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
            About()
        }
    }
}


@Composable
fun About() {

    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "About JSONPad", textDecoration = TextDecoration.Underline,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "JSONPad is a lightweight, powerful note-taking app" +
                    " designed for developers and productivity enthusiasts. " +
                    "It provides an intuitive interface for creating, organizing," +
                    " and managing structured notes with JSON support.",
            fontSize = 16.sp,
            textAlign = TextAlign.Justify
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "About the Developer",textDecoration = TextDecoration.Underline,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Created by Rayman Aryan, an Android developer passionate about" +
                    " building efficient, user-friendly apps. JSONPad is an open-source" +
                    " project aimed at simplifying data-driven note-taking.",
            fontSize = 16.sp,
            textAlign = TextAlign.Justify
        )

        Spacer(modifier = Modifier.height(20.dp))

        // GitHub Link
        Text(
            text = "GitHub Repository",
            fontSize = 18.sp,
            color = Color.Blue,
            textDecoration = TextDecoration.Underline,
            modifier = Modifier.clickable {
                val intent = Intent(Intent.ACTION_VIEW,
                    "https://github.com/RaymanAryan/JSONPad".toUri())
                context.startActivity(intent) // Launch GitHub link
            }
        )
    }
}


