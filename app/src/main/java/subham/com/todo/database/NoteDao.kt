package subham.com.todo.database

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*

@Dao
interface NoteDao {
    @Insert
    fun insert(note: Note)
    @Update
    fun update(note: Note)
    @Delete
    fun delete(note: Note)
    @Query("DELETE FROM note_table")
    fun deleteAllNotes()
    @Query("SELECT * FROM NOTE_TABLE ORDER BY note_priority DESC")
    fun getAllNotes():LiveData<List<Note>>
}