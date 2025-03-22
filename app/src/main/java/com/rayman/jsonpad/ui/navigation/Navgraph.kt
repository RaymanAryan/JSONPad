package com.rayman.jsonpad.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.rayman.jsonpad.data.local.Note
import com.rayman.jsonpad.ui.screens.AboutScreen
import com.rayman.jsonpad.ui.screens.HomeScreen
import com.rayman.jsonpad.ui.screens.EditNoteScreen
import com.rayman.jsonpad.ui.screens.HelpScreen
import com.rayman.jsonpad.ui.screens.SettingsScreen
import com.rayman.jsonpad.ui.viewmodel.NoteViewModel

@Composable
fun NoteNavGraph(navController: NavHostController, viewModel: NoteViewModel = hiltViewModel()) {
    NavHost(navController, startDestination = "noteList") {
        composable("noteList") {
            HomeScreen(
                viewModel = viewModel,
                onAddNote = { navController.navigate("editNote/new") },
                onNoteClick = { note -> navController.navigate("editNote/${note.id}") },
                navController =  navController
            )
        }

        composable(route = "settings", content = {SettingsScreen()})

        composable(route = "Help", content = {HelpScreen()})

//        composable(
//            route = "Categories/{category}",
//            arguments = listOf(navArgument("category") { type = NavType.StringType })
//        ) { backStackEntry ->
//            val category = backStackEntry.arguments?.getString("category") ?: ""
//            HomeScreen(
//                viewModel = viewModel,
//                onAddNote = { navController.navigate("editNote/new") },
//                onNoteClick = { note -> navController.navigate("editNote/${note.id}") },
//                navController =  navController)
//        }

        composable(route = "About", content = {AboutScreen()})

        composable(
            route = "editNote/{noteId}",
            arguments = listOf(navArgument("noteId") { type = NavType.StringType })
        ) { backStackEntry ->

            val noteId = backStackEntry.arguments?.getString("noteId")
            // completed Toast.makeText(context, "Error: Note ID is missing!$noteId" , Toast.LENGTH_SHORT).show()
            val note by  viewModel.getNoteById(noteId ?: "").collectAsState(initial = null)
            var newNote by remember { mutableStateOf<Note?>(null) }


            LaunchedEffect(noteId, note) {
                if (note == null) {
                    newNote = viewModel.addNote("", "", "Default") // Create a new empty note
                }
            }

            (note ?: newNote)?.let {
                EditNoteScreen(
                    note = it, onNoteUpdated = { navController.popBackStack() },
                    viewModel = hiltViewModel(),
                    onBack = {navController.popBackStack()}
                )
            }
        }
    }
}


