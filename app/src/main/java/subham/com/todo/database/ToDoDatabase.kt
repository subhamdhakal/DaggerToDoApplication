package subham.com.todo.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters

@Database(entities = [ToDo::class], version = 1, exportSchema = false)
abstract class DayDatabase : RoomDatabase() {
    abstract val toDoDao:ToDoDao

}

