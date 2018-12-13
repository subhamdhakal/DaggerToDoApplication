package subham.com.todo.ui

import android.databinding.BaseObservable
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
    fun getPriority():Int{
        when(todo.priority){
            Priority.PRIORITY_LOW-> return R.drawable.ic_prioroty_indicator_green
            Priority.PRIORITY_NORMAL-> return R.drawable.ic_priority_indicator_yellow
            Priority.PRIORITY_HIGH-> return R.drawable.ic_priority_indicator_red
            else-> return R.drawable.ic_prioroty_indicator_green
        }
    }
    fun getStatus():Int{
        if(!todo.doneStatus) return R.drawable.ic_tick_inside_a_circle
        else return R.drawable.ic_done_yellow_24dp

    }
    fun getToDoTime():String{
        if(todo.hasTime) {
            var calendar = Calendar.getInstance()
            calendar.timeInMillis=todo.timeToDo
            return calendar.time.toString()
        }
        else return ""
    }
}