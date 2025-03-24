package com.rayman.jsonpad.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.rayman.jsonpad.ui.components.SimpleBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HelpScreen(title: String, navigate: NavHostController) {
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
            Help()
        }
    }
}

@Composable
fun Help() {
    Column(modifier = Modifier.fillMaxSize()) {

        Spacer(modifier = Modifier.fillMaxSize(.05f))

        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()){
            Text(
                text = "Help & Support",
                textDecoration = TextDecoration.Underline, // Corrected placement
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.fillMaxSize(.05f))
        Text("Here are some common questions and answers to help you.")
        Spacer(modifier = Modifier.fillMaxSize(.1f))
        HelpItem(
            question = "i) How to use this app?",
            answer = "=> Navigate using the menu and interact with different features."
        )
        Spacer(modifier = Modifier.fillMaxSize(.05f))
        HelpItem(
            question = "ii) Where can I report a bug?",
            answer = "=> You can report bugs via the support email in the settings."
        )
        Spacer(modifier = Modifier.fillMaxSize(.05f))
        HelpItem(
            question = "iii) Can you ask me questions?",
            answer = "=> Yes, in email, GitHub, and App Store, etc."
        )
        Spacer(modifier = Modifier.fillMaxSize(.05f))
    }
}


@Composable
fun HelpItem(question: String, answer: String) {
    Column {
        Text(question, fontWeight = FontWeight.Bold,style = MaterialTheme.typography.titleMedium)
        Text(answer, style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))
    }
}