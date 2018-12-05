package subham.com.todo.database

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "note_table")
data class Note(
        @PrimaryKey(autoGenerate = true)
        var id: Int?,
        @ColumnInfo(name = "note_title")
        var title: String,
        @ColumnInfo(name = "note_description")
        var description: String,
        @ColumnInfo(name = "note_priority")
        var priority: Int = Int.MIN_VALUE
)
