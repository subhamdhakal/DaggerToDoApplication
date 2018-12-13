package subham.com.todo.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import subham.com.todo.database.ToDo
import subham.com.todo.repository.DayRepository

class DayViewModel(private var repository: DayRepository) : ViewModel() {
    fun insertToDO(toDo: ToDo) {
        return repository.insert(toDo)
    }

    //
//    fun updateNote(note: ContactsContract.CommonDataKinds.Note) {
//        return repository.update(note)
//    }
//
    fun deleteNote(toDo: ToDo) {
        return repository.deleteToDo(toDo)
    }
//
//    fun deleteAllNotes() {
//        return repository.deleteAllNotes()
//    }
//
//    fun getAllDay: LiveData<Day>> {
//        return repository.getAllDay(LiveData<Day>)
//    }
    fun getTodosForToday(todayStart:Long,todayEnd:Long): LiveData<List<ToDo>> {
        return repository.getAllToDo(todayStart,todayEnd)
    }
}