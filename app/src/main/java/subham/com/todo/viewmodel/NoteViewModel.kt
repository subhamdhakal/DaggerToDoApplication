package subham.com.todo.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import subham.com.todo.database.Note
import subham.com.todo.repository.NoteRepository
import subham.com.todo.util.singleArgViewModelFactory

class NoteViewModel(private var repository: NoteRepository) : ViewModel() {
    companion object {
        /**
         * Factory for creating [MainViewModel]
         *
         * @param arg the repository to pass to [MainViewModel]
         */
        val FACTORY = singleArgViewModelFactory(::NoteViewModel)
    }

    fun insertNote(note: Note) {
        return repository.insert(note)
    }

    fun updateNote(note: Note) {
        return repository.update(note)
    }

    fun deleteNote(note: Note) {
        return repository.delete(note)
    }

    fun deleteAllNotes() {
        return repository.deleteAllNotes()
    }

    fun getAllNotes(): LiveData<List<Note>> {
        return repository.getAllNotes()
    }
}