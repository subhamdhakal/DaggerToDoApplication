package subham.com.todo.repository

import android.arch.lifecycle.LiveData
import android.util.Log
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import subham.com.todo.database.ToDo
import subham.com.todo.database.ToDoDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DayRepository
@Inject
constructor(var dayDao: ToDoDao) {
    val TAG = "NoteRepository"
    fun insert(toDo: ToDo) {
        Completable.fromAction {
            dayDao.insertDay(toDo)
        }.observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                Log.d(TAG, "Sucessfully inserted")
            }, {
                it.printStackTrace()
            })
    }
//    fun update(day: Day) {
//        Completable.fromAction {
//            //            noteDao.update(note)
//        }.observeOn(AndroidSchedulers.mainThread())
//            .subscribeOn(Schedulers.io())
//            .subscribe({
//                Log.d(TAG, "Sucessfully updated")
//            }, {
//            })
//    }
    fun deleteToDo(toDo: ToDo) {
        Completable.fromAction {
            dayDao.deleteToDo(toDo)
        }.observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                Log.d(TAG, "Sucessfully deleted")
            }, {
            })
    }

//    fun deleteAllToDo() {
//        Completable.fromAction {
//            //            noteDao.deleteAllNotes()
//        }.observeOn(AndroidSchedulers.mainThread())
//            .subscribeOn(Schedulers.io())
//            .subscribe({
//                Log.d(TAG, "Sucessfully deleted all notes")
//            }, {
//            })
//    }
    fun getAllToDo(todayStart:Long,todayEnd:Long): LiveData<List<ToDo>> {
        return dayDao.getAllToDo(todayStart,todayEnd)
    }

    fun updateToDoStatus(toDo: ToDo) {
        Completable.fromAction {
            dayDao.updateToDoStatus(toDo)
        }.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    Log.d(TAG, "Sucessfully inserted")
                }, {
                    it.printStackTrace()
                })

    }

}
//    fun getAllToDo(): LiveData<List<ContactsContract.CommonDataKinds.Note>> {
////        return noteDao.getAllNotes()
//    }
//}