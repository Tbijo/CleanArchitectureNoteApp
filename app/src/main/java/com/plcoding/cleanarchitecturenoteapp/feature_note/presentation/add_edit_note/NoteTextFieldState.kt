package com.plcoding.cleanarchitecturenoteapp.feature_note.presentation.add_edit_note

// state ktory sa bude starat o state Textfieldov
// prepisovat text pri novom znaku
// zobrazovat hint ked treba

data class NoteTextFieldState(
    val text: String = "",
    val hint: String = "",
    val isHintVisible: Boolean = true
)
