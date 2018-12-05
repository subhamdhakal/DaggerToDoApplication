package subham.com.todo.repository

import android.arch.lifecycle.LiveData
import android.util.Log
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import subham.com.todo.database.Note
import subham.com.todo.database.NoteDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NoteRepository
@Inject
constructor(var noteDao: NoteDao) {
    val TAG = "NoteRepository"
    fun insert(note: Note) {
        Completable.fromAction {
            noteDao.insert(note)
        }.observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                Log.d(TAG, "Sucessfully inserted")
            }, {
                it.printStackTrace()
            })
    }

    fun update(note: Note) {
        Completable.fromAction {
            noteDao.update(note)
        }.observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                Log.d(TAG, "Sucessfully updated")
            }, {
            })
    }

    fun delete(note: Note) {
        Completable.fromAction {
            noteDao.delete(note)
        }.observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                Log.d(TAG, "Sucessfully deleted")
            }, {
            })
    }

    fun deleteAllNotes() {
        Completable.fromAction {
            noteDao.deleteAllNotes()
        }.observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                Log.d(TAG, "Sucessfully deleted all notes")
            }, {
            })
    }

    fun getAllNotes(): LiveData<List<Note>> {
        return noteDao.getAllNotes()
    }
}