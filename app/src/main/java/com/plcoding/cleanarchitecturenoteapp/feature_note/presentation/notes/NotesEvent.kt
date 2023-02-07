package com.plcoding.cleanarchitecturenoteapp.feature_note.presentation.notes

import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.model.Note
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.util.NoteOrder

// trieda kde bude eventy ktore vyvola pouzivatel

sealed class NotesEvent {
    // klient zavola zoradenie
    // po kliknuti na radio button s typom zoradenie odosleme info z UI do Viewmodelu
    data class Order(val noteOrder: NoteOrder): NotesEvent()

    // klient vymaze note
    data class DeleteNote(val note: Note): NotesEvent()

    // klient klikne na undo v SnackBar aby navratil vymazany Note
    object RestoreNote: NotesEvent()

    // na rozbalenie sekcie kde sa nastavuje zoradovanie
    object ToggleOrderSection: NotesEvent()
}
