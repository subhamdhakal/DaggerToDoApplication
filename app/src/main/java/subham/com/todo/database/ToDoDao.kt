package subham.com.todo.database

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*

@Dao
interface ToDoDao {
    @Insert
    fun insertDay(toDo: ToDo)

    @Query("SELECT * FROM todo_table WHERE timeToDo >=:todayStart AND timeToDo<=:todayEnd ORDER BY doneStatus ASC")
    fun getAllToDo(todayStart:Long,todayEnd:Long): LiveData<List<ToDo>>

    @Delete
    fun deleteToDo(toDo: ToDo)

    @Update
    fun updateToDoStatus(toDo: ToDo)


}

//    @Query("DELETE FROM note_table")
//    fun deleteAllNotes()
//    @Query("SELECT * FROM NOTE_TABLE ORDER BY note_priority ASC")
//    fun getAllNotes():LiveData<List<Note>>
//}