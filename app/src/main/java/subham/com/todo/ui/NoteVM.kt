package subham.com.todo.ui

import android.databinding.BaseObservable
import subham.com.todo.database.Note

class NoteVM(var note: Note): BaseObservable() {
    fun getPriority(): String {
        return note.priority.toString()
    }

    fun getTitle(): String {
        return note.title.toString()
    }

    fun getDescription(): String {
        return note.description
    }
}