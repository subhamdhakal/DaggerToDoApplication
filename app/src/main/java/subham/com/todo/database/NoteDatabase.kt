package subham.com.todo.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context

@Database(entities = [Note::class], version = 1,exportSchema = false)
abstract class NoteDatabase : RoomDatabase() {

    abstract val noteDao: NoteDao


}

