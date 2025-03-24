package com.rayman.jsonpad.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.rayman.jsonpad.data.local.Note
import com.rayman.jsonpad.ui.components.NoteListScreen
import com.rayman.jsonpad.ui.viewmodel.NoteViewModel
import com.rayman.jsonpad.ui.viewmodel.SelectNoteViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: NoteViewModel = hiltViewModel(),
    viewModel2: SelectNoteViewModel = hiltViewModel(),
    onAddNote: () -> Unit = {},
    onNoteClick: (Note) -> Unit = {}
) {
    val categories = viewModel.allCategories.collectAsState().value
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    BackHandler {
        when {
            drawerState.isOpen -> scope.launch { drawerState.close() }
            !viewModel2.isEmpty() -> viewModel2.clearSelection()
            else -> navController.popBackStack()
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            Column(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.primaryContainer)) {
                Spacer(Modifier.fillMaxHeight(.04f))
                // Divider

                // Home
                DrawerItem("Home", Icons.Filled.Home) {
                    scope.launch { closeDrawer(drawerState)
                        viewModel.setCategory(null)}
                }
                // Divider
                HorizontalDivider()

                // Categories Section
                Text(
                    text = "Categories",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 16.dp, top = 8.dp)
                )
                categories.filter { it != "" }.distinct().forEach { category ->
                    DrawerItem(category, Icons.AutoMirrored.Filled.List) {
                        scope.launch { closeDrawer(drawerState)
                            viewModel.setCategory(category)}
                    }
                }

                // Divider
                HorizontalDivider()

                // Settings & Help
                DrawerItem("Settings", Icons.Filled.Settings) {
                    scope.launch { drawerState.close() }
                    navController.navigate("Settings")
                }
                DrawerItem("Help", Icons.Filled.Info) {
                    scope.launch { drawerState.close() }
                    navController.navigate("Help")
                }
                DrawerItem("About",Icons.Filled.Info) {
                    scope.launch { drawerState.close() }
                    navController.navigate("About")
                }
            }
        },
        content = {
            NoteListScreen(
                onAddNote = onAddNote,
                onNoteClick = onNoteClick,
                navDrawerState = drawerState,
                viewModel = viewModel,
                viewModel2 = viewModel2
            )
        }
    )
}


@Composable
fun DrawerItem(title: String, icon: ImageVector, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = icon, contentDescription = title, modifier = Modifier.size(24.dp))
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = title, fontSize = 16.sp, color = MaterialTheme.colorScheme.onPrimaryContainer)
    }
}

suspend fun closeDrawer(drawerState: DrawerState){
    drawerState.close()
}
