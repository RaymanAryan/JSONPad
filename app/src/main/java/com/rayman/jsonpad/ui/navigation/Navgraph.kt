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
import com.rayman.jsonpad.ui.screens.BackUpScreen
import com.rayman.jsonpad.ui.screens.EditNoteScreen
import com.rayman.jsonpad.ui.screens.HelpScreen
import com.rayman.jsonpad.ui.screens.HomeScreen
import com.rayman.jsonpad.ui.viewmodel.JsonViewModel
import com.rayman.jsonpad.ui.viewmodel.NoteViewModel
import com.rayman.jsonpad.ui.viewmodel.SelectNoteViewModel
import com.rayman.jsonpad.ui.viewmodel.UIViewModel

@Composable
fun NoteNavGraph(
    navController: NavHostController,
    noteViewModel: NoteViewModel = hiltViewModel(),
    selectNoteViewModel: SelectNoteViewModel = hiltViewModel(), // Replace 'Any' with the correct ViewModel type
    jsonViewModel: JsonViewModel = hiltViewModel(), // Replace 'Any' with the correct ViewModel type
    uiViewModel: UIViewModel = hiltViewModel() // Replace 'Any' with the correct ViewModel type
) {
    NavHost(navController, startDestination = "noteList") {
        composable("noteList") {
            HomeScreen(
                navController =  navController,
                onAddNote = { navController.navigate("editNote/new") },
                onNoteClick = { note -> navController.navigate("editNote/${note.id}") },
                noteViewModel = noteViewModel,
                selectNoteViewModel = selectNoteViewModel,
                uiViewModel = uiViewModel
            )
        }

        composable(route = "Backup", content =  { BackUpScreen(
            title = "Settings",
            navController,
            noteViewModel = noteViewModel,
            jsonViewModel = jsonViewModel,
        ) })

        composable(route = "Help" ,content = {HelpScreen(title = "Help",navController)})

        composable(route = "About") { AboutScreen(title = "About",navController)}

        composable(
            route = "editNote/{noteId}",
            arguments = listOf(navArgument("noteId") { type = NavType.StringType })
        ) { backStackEntry ->

            val noteId = backStackEntry.arguments?.getString("noteId")
            // completed Toast.makeText(context, "Error: Note ID is missing!$noteId" , Toast.LENGTH_SHORT).show()
            val note by  noteViewModel.getNoteById(noteId ?: "").collectAsState(initial = null)
            var newNote by remember { mutableStateOf<Note?>(null) }


            LaunchedEffect(noteId, note) {
                if (note == null) {
                    newNote = noteViewModel.addNote(Note(title = "Untitled", content =  "", category = "Default")) // Create a new empty note
                }
            }

            (note ?: newNote)?.let {
                EditNoteScreen(
                    note = it, onNoteUpdated = { navController.popBackStack() },
                    noteViewModel = hiltViewModel(),
                    onBack = {navController.popBackStack()}
                )
            }
        }
    }
}


