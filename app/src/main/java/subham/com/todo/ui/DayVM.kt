package subham.com.todo.ui

import android.databinding.BaseObservable
import android.view.View
import subham.com.todo.R
import subham.com.todo.database.ToDo
import subham.com.todo.util.Priority
import subham.com.todo.util.getFormattedStringFromTimeStamp
import java.util.*

class DayVM : BaseObservable() {
    lateinit var todo: ToDo
    fun setToDo(todo: ToDo) {
        this.todo = todo
        notifyChange()
    }

    fun getTitle(): String {
        return todo.title
    }

    fun getPriority(): Int {
        when (todo.priority) {
            Priority.PRIORITY_LOW -> return R.drawable.ic_prioroty_indicator_green
            Priority.PRIORITY_NORMAL -> return R.drawable.ic_priority_indicator_yellow
            Priority.PRIORITY_HIGH -> return R.drawable.ic_priority_indicator_red
            else -> return R.drawable.ic_prioroty_indicator_green
        }
    }

    fun getStatus(): Int {
        if (!todo.doneStatus) return R.drawable.ic_tick_inside_a_circle
        else return R.drawable.ic_done_yellow_24dp
    }

    fun getNotificationIcon(): Int {
        if (todo.remainder != null && todo.remainder != Long.MIN_VALUE) {
            return R.drawable.ic_notifications_gray_24dp
        } else if (todo.remainder == null) {
            return 0
        } else return 0
    }

    fun getToDoTimeVisibility(): Int {
        if (todo.hasTime)
            return View.VISIBLE
        return View.GONE
    }

    fun getToDoTime(): String {
        if (todo.hasTime) {
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = todo.timeToDo!!
            val amorpm: String?
            amorpm = if (calendar.get(Calendar.AM_PM) == 0) "AM" else "PM"

            return "${calendar.get(Calendar.HOUR)}:${calendar.get(Calendar.MINUTE)} $amorpm"
        } else
            return ""
    }
}