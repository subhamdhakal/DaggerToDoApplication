package subham.com.todo.database

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "todo_table")
data class ToDo(
    @PrimaryKey(autoGenerate = true)
    var toDoId: Int?,
    var title: String,
    var doneStatus: Boolean,
    var priority: Int,
    var remainder: String?,
    var timeToDo: Long,
    var hasTime:Boolean
)



