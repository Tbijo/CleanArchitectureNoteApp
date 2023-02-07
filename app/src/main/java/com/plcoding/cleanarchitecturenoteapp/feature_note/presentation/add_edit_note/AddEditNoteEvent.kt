package com.plcoding.cleanarchitecturenoteapp.feature_note.presentation.add_edit_note

import androidx.compose.ui.focus.FocusState

sealed class AddEditNoteEvent {
    data class EnteredTitle(val value: String): AddEditNoteEvent() // kazdy novy znak (klik na klavesnicu event) value sa bude menit podla znakov
    data class ChangeTitleFocus(val focusState: FocusState): AddEditNoteEvent() // klik na textfield aby sme vedeli ze sa ma schovat Hint
    data class EnteredContent(val value: String): AddEditNoteEvent()
    data class ChangeContentFocus(val focusState: FocusState): AddEditNoteEvent()
    data class ChangeColor(val color: Int): AddEditNoteEvent()
    object SaveNote: AddEditNoteEvent()
}
