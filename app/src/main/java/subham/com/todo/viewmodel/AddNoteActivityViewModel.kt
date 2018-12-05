package subham.com.todo.viewmodel

import android.arch.lifecycle.ViewModel
import subham.com.todo.database.Note
import subham.com.todo.repository.NoteRepository
import subham.com.todo.util.singleArgViewModelFactory

class AddNoteActivityViewModel(var repository: NoteRepository):ViewModel(){
    companion object {
        /**
         * Factory for creating [MainViewModel]
         *
         * @param arg the repository to pass to [MainViewModel]
         */
        val FACTORY = singleArgViewModelFactory(::AddNoteActivityViewModel)
    }

    fun insertNote(note: Note) {
        return repository.insert(note)
    }

}