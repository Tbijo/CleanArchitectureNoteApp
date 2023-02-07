package com.plcoding.cleanarchitecturenoteapp.feature_note.domain.use_case

import com.google.common.truth.Truth.assertThat
import com.plcoding.cleanarchitecturenoteapp.feature_note.data.repository.FakeNoteRepository
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.model.Note
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.util.NoteOrder
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.util.OrderType
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

// this test case does not require Android Components so it is local test in JVM
// therefore put it in test directory
class GetNotesTest {
    // First thing which dependencies do we need here
    // We need the repository and the UseCase itself

    private lateinit var getNotes: GetNotes
    private lateinit var fakeNoteRepository: FakeNoteRepository

    // @Before tells junit this is a before function
    // It will run the before-function before every single test case
    // function will be used to initialize our objects
    @Before
    fun setUp() {
        // Get notes needs a Repo we will not use our implementation because that requires Room Database
        // which needs Android Components
        // And it would have take a lot of time, unit tests should be quick and not depend on DBs or API
        // We will use a FakeRepository
        fakeNoteRepository = FakeNoteRepository()
        getNotes = GetNotes(fakeNoteRepository)

        // provide fake data into Repo
        val notesToInsert = mutableListOf<Note>()
        ('a'..'z').forEachIndexed { index, c ->
            notesToInsert.add(
                Note(
                    title = c.toString(),
                    content = c.toString(),
                    timeStamp = index.toLong(),
                    color = index
                )
            )
        }
        // this data will be ordered we are testing whether the use case orders them
        // therefore the data should be shuffled
        notesToInsert.shuffle()
        // now put the data in the Repo
        // it is a suspend fun so use runBlocking
        runBlocking { notesToInsert.forEach { fakeNoteRepository.insertNote(it) } }
    }

    // finished making set up for Test Case now to make the Test Case itself
    // as a function with @Test

    // In test cases we can use backticks ` to write not the function name but what it tests and we
    // dont call them ourselves

    // Name convention for test cases:
        // 1. What do we do with this test case?
        // 2. ","
        // 3. What should it return?
    // It will display it so we easily recognize the error

    @Test
    fun `Order notes by title ascending, correct order`() =  runBlocking {
        // use case returns flow so runBlocking used
        // first() we listen only for the first emit
        // get data from repository
        val notes = getNotes(NoteOrder.Title(OrderType.Ascending)).first()

        // now to write the logic to check whether the received data is really ordered by title and ascending
        for (i in 0..notes.size - 2) {
            // comparing two notes next to each other therefore - 2 loops
            // function makes sure something is correct
            // if it fails our test case fails and vice versa
            assertThat(notes[i].title).isLessThan(notes[i+1].title)
        }
    }

    @Test
    fun `Order notes by title descending, correct order`() = runBlocking {
        val notes = getNotes(NoteOrder.Title(OrderType.Descending)).first()

        for(i in 0..notes.size - 2) {
            assertThat(notes[i].title).isGreaterThan(notes[i+1].title)
        }
    }

    @Test
    fun `Order notes by date ascending, correct order`() = runBlocking {
        val notes = getNotes(NoteOrder.Date(OrderType.Ascending)).first()

        for(i in 0..notes.size - 2) {
            assertThat(notes[i].timeStamp).isLessThan(notes[i+1].timeStamp)
        }
    }

    @Test
    fun `Order notes by date descending, correct order`() = runBlocking {
        val notes = getNotes(NoteOrder.Date(OrderType.Descending)).first()

        for(i in 0..notes.size - 2) {
            assertThat(notes[i].timeStamp).isGreaterThan(notes[i+1].timeStamp)
        }
    }

    @Test
    fun `Order notes by color ascending, correct order`() = runBlocking {
        val notes = getNotes(NoteOrder.Color(OrderType.Ascending)).first()

        for(i in 0..notes.size - 2) {
            assertThat(notes[i].color).isLessThan(notes[i+1].color)
        }
    }

    @Test
    fun `Order notes by color descending, correct order`() = runBlocking {
        val notes = getNotes(NoteOrder.Color(OrderType.Descending)).first()

        for(i in 0..notes.size - 2) {
            assertThat(notes[i].color).isGreaterThan(notes[i+1].color)
        }
    }
}