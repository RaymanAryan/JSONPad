package com.rayman.jsonpad.ui.navigation

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.rayman.jsonpad.data.local.Note
import com.rayman.jsonpad.ui.screens.EditNoteScreen
import com.rayman.jsonpad.ui.screens.NoteListScreen
import com.rayman.jsonpad.ui.viewmodel.NoteViewModel

@Composable
fun NoteNavGraph(navController: NavHostController, viewModel: NoteViewModel = hiltViewModel()) {
    val context = LocalContext.current
    NavHost(navController, startDestination = "noteList") {
        composable("noteList") {
            NoteListScreen(
                viewModel = viewModel,
                onAddNote = { navController.navigate("editNote/new") },
                onNoteClick = { note -> navController.navigate("editNote/${note.id}") }
            )
        }
        composable(
            route = "editNote/{noteId}",
            arguments = listOf(navArgument("noteId") { type = NavType.StringType })
        ) { backStackEntry ->

            val noteId = backStackEntry.arguments?.getString("noteId")
            // completed Toast.makeText(context, "Error: Note ID is missing!$noteId" , Toast.LENGTH_SHORT).show()
            val note by  viewModel.getNoteById(noteId ?: "").collectAsState(initial = null)
            var newNote by rememberSaveable { mutableStateOf<Note?>(null) }


            LaunchedEffect(noteId, note) {
                if (note == null) {
                    newNote = viewModel.addNote("", "", "") // Create a new empty note
                    Toast.makeText(context, "Error: Note ID is missing!${newNote!!.title}" , Toast.LENGTH_SHORT).show()
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
