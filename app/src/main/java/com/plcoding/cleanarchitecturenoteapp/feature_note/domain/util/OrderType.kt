package com.plcoding.cleanarchitecturenoteapp.feature_note.domain.util

// the user can choose in which order to display the data
// but we need something to based the order on like (Title, Color) and then choose Asc or Desc
// NoteOrder

sealed class OrderType{
    object Ascending: OrderType()
    object Descending: OrderType()
}
