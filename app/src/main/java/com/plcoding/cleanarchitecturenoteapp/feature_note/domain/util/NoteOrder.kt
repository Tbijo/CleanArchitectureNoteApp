package com.plcoding.cleanarchitecturenoteapp.feature_note.domain.util

// vyberame vec podla ktorej sa bude organizovat zoznam zhora nadol alebo naopak
// preto kazda vec (class) dostane aj parameter ci sa jedna o zhora nadol alebo naopak

sealed class NoteOrder(val orderType: OrderType) {
    class Title(orderType: OrderType): NoteOrder(orderType)
    class Date(orderType: OrderType): NoteOrder(orderType)
    class Color(orderType: OrderType): NoteOrder(orderType)

    // zmeni sa note order ale ostane order type
    fun copy(orderType: OrderType): NoteOrder {
        return when(this){
            is Color -> Color(orderType)
            is Date -> Date(orderType)
            is Title -> Title(orderType)
        }
    }
}
