package com.example.notes.ui.util

sealed class Screen(val route:String){
    object NotesScreen : Screen("notes_screen")
    object AddEditNoteScreen : Screen("add_edit_screen")
}