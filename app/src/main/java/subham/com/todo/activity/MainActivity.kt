package subham.com.todo.activity

import android.app.NotificationManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.BottomSheetBehavior
import android.support.v4.app.NotificationCompat
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import com.github.badoualy.datepicker.DatePickerTimeline
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.bottom_sheet_add_todo.*
import subham.com.todo.R
import subham.com.todo.adapter.DayAdapter
import subham.com.todo.adapter.SwipeToDeleteCallback
import subham.com.todo.base.BaseActivity
import subham.com.todo.database.ToDo
import subham.com.todo.notification.NotificationUtils
import subham.com.todo.util.Priority
import subham.com.todo.util.hideKeyboard
import subham.com.todo.viewmodel.DayViewModel
import java.util.*
import javax.inject.Inject

class MainActivity : BaseActivity(), View.OnClickListener {
    lateinit var notificationManager: NotificationManager
    lateinit var notificationBuilder: NotificationCompat.Builder
    val NOTIFICATION_REQUEST_CODE = 101
    override fun onClick(p0: View?) {
        button_15_mins_before.background = getDrawable(R.drawable.bg_rounded_rectangle_large_radius_gray)
        button_an_hour_before.background = getDrawable(R.drawable.bg_rounded_rectangle_large_radius_gray)
        button_in_24_hours.background = getDrawable(R.drawable.bg_rounded_rectangle_large_radius_gray)

        button_15_mins_before.setTextColor(ContextCompat.getColor(this, R.color.black))
        button_an_hour_before.setTextColor(ContextCompat.getColor(this, R.color.black))
        button_in_24_hours.setTextColor(ContextCompat.getColor(this, R.color.black))


        when (p0?.id) {
            button_15_mins_before.id -> {
                setFocus(button_15_mins_before)
                remainderTime = 900000
            }
            button_an_hour_before.id -> {
                setFocus(button_an_hour_before)
                remainderTime = 3600000
            }
            button_in_24_hours.id -> {
                setFocus(button_in_24_hours)
                remainderTime = 86400000
            }
        }
    }

    private fun setFocus(button_focus: Button) {
        button_focus.background = getDrawable(R.drawable.bg_rounded_rectangle_large_radius_purple)
        button_focus.setTextColor(ContextCompat.getColor(this, R.color.white))
    }

    var remainderTime: Long = 900000
    var priority = Int.MIN_VALUE
    lateinit var adapter: DayAdapter
    var isToggleChecked = false
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>
    override fun getLayout(): Int {
        return R.layout.activity_main
    }

    var selectedCurrentDate: Long = Calendar.getInstance().timeInMillis
    val TAG = MainActivity::class.java.simpleName
    @Inject
    lateinit var dayViewModel: DayViewModel
    var listOfToDo: MutableList<ToDo> = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        button_15_mins_before.setOnClickListener(this)
        button_an_hour_before.setOnClickListener(this)
        button_in_24_hours.setOnClickListener(this)
        setInvisible()
//        createNotificationChannel()
        adapter = DayAdapter(this, object : DayAdapter.ClickListener {
            override fun onCheckClickListener(todo: ToDo) {
                Log.e(TAG, "called")
                if (!todo.doneStatus) {
                    todo.doneStatus = true
                    dayViewModel.updateToDoStatus(todo)
                    fetchToDosForThatDay(selectedCurrentDate)
                } else if (todo.doneStatus) {
                    todo.doneStatus = false
                    dayViewModel.updateToDoStatus(todo)
                    fetchToDosForThatDay(selectedCurrentDate)
                }
            }
        })
        reclycler_view.layoutManager = LinearLayoutManager(this)

        reclycler_view.adapter = adapter
//        val calendar = Calendar.getInstance()
//        calendar.set(Calendar.HOUR_OF_DAY, 14)
//        calendar.set(Calendar.MINUTE, 52)
//        NotificationUtils().setNotification(" ba js", 2, calendar.timeInMillis, this@MainActivity)
//        calendar.set(Calendar.HOUR_OF_DAY,22)
//        calendar.set(Calendar.MINUTE,29)
//        NotificationUtils().cancelNotification(1,calendar.timeInMillis, this@MainActivity)
//        edit_text_in_dialog.setOnFocusChangeListener { view, b ->
//            if (bottomSheetBehavior.state != BottomSheetBehavior.STATE_EXPANDED && b)
//                bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
//        }
//        edit_text_in_dialog.setOnClickListener {
//            if (bottomSheetBehavior.state!=BottomSheetBehavior.STATE_EXPANDED){
//                bottomSheetBehavior.state=BottomSheetBehavior.STATE_EXPANDED
//                edit_text_in_dialog.requestFocus()
//            }
//        }
        button_next.setOnClickListener {
            edit_text_in_dialog.clearFocus()
            button_next.visibility=View.INVISIBLE
            this.hideKeyboard(edit_text_in_dialog)
            Handler().postDelayed({
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            }, 200)
        }
        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == R.id.radio_button_high)
                priority = Priority.PRIORITY_HIGH
            else if (checkedId == R.id.radio_button_normal)
                priority = Priority.PRIORITY_NORMAL
            else
                priority = Priority.PRIORITY_LOW
        }
        toggle_time_set.setOnCheckedChangeListener { view, isChecked ->
            if (isChecked) {
                isToggleChecked = isChecked
                setVisible()
            } else if (!isChecked) {
                isToggleChecked = false
                setInvisible()
            }
        }


        date_picker.onDateSelectedListener =
                DatePickerTimeline.OnDateSelectedListener { year, month, day, index ->
                    val calendar = Calendar.getInstance()
                    calendar.set(year, month, day)
//                    Toast.makeText(this, "Time in millis" + calendar.timeInMillis, Toast.LENGTH_SHORT).show()
                    Log.e(TAG, "time in milllis" + calendar.timeInMillis)
                    selectedCurrentDate = calendar.timeInMillis
                    fetchToDosForThatDay(selectedCurrentDate)
                }




        radio_button_low.isChecked = true

        bottomSheetBehavior = BottomSheetBehavior.from<LinearLayout>(bottom_sheet)

        image_view_arrow.setOnClickListener {
            if (bottomSheetBehavior.state == BottomSheetBehavior.STATE_COLLAPSED)
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            else
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }
        bottomSheetBehavior.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        button_next.visibility = View.VISIBLE
                        image_view_arrow.animate().rotation(0f).setDuration(500).start()
                    }
                    BottomSheetBehavior.STATE_HIDDEN -> {
                    }
                    BottomSheetBehavior.STATE_EXPANDED -> {
                        button_next.visibility = View.INVISIBLE
                        image_view_arrow.animate().rotation(180f).setDuration(500).start()
                    }
                    BottomSheetBehavior.STATE_DRAGGING -> {
                    }
                    BottomSheetBehavior.STATE_SETTLING -> {
                    }
                }
            }
        })
        fetchToDosForToday()

        button_save.setOnClickListener {
            saveToDo()
        }
        val swipeHandler = object : SwipeToDeleteCallback(this) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                listOfToDo[viewHolder.adapterPosition].notificationId?.let {
                    NotificationUtils().cancelNotification(
                        it,
                        this@MainActivity
                    )
                }
                dayViewModel.deleteToDo(listOfToDo[viewHolder.adapterPosition])
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(reclycler_view)
    }

    private fun fetchToDosForThatDay(selectedCurrentDate: Long) {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = selectedCurrentDate
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        val todayStart = calendar.timeInMillis
        calendar.set(Calendar.HOUR_OF_DAY, 23)
        calendar.set(Calendar.MINUTE, 59)
        calendar.set(Calendar.SECOND, 59)
        calendar.set(Calendar.MILLISECOND, 999)
        val todayEnd = calendar.timeInMillis

        dayViewModel.getTodosForToday(todayStart, todayEnd).observe(this, android.arch.lifecycle.Observer { it ->
            adapter.setData(it)
            listOfToDo = it as MutableList<ToDo>
        })
    }

    private fun fetchToDosForToday() {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        val todayStart = calendar.timeInMillis
        calendar.set(Calendar.HOUR_OF_DAY, 23)
        calendar.set(Calendar.MINUTE, 59)
        calendar.set(Calendar.SECOND, 59)
        calendar.set(Calendar.MILLISECOND, 999)
        val todayEnd = calendar.timeInMillis

        dayViewModel.getTodosForToday(todayStart, todayEnd).observe(this, android.arch.lifecycle.Observer { it ->
            adapter.setData(it)
            listOfToDo = it as MutableList<ToDo>
        })
    }

    private fun saveToDo() {
        val title = edit_text_in_dialog.text.toString()
        var status = false
        var hasCertainTime = false
        var todo1: ToDo?
        this.hideKeyboard(relative_layout_edit_text)

        if (isToggleChecked) {
            hasCertainTime = true
            //get that day and set its time and minutes to that of time picker, get that time in milliseconds and the pass it as a parameter
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = selectedCurrentDate
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                calendar.set(Calendar.HOUR_OF_DAY, time_picker.hour)
                calendar.set(Calendar.MINUTE, time_picker.minute)
            } else {
                calendar.set(Calendar.HOUR_OF_DAY, time_picker.currentHour)
                calendar.set(Calendar.MINUTE, time_picker.currentMinute)
            }
            selectedCurrentDate = calendar.timeInMillis
            todo1 = ToDo(
                0,
                title = title,
                doneStatus = status,
                priority = priority,
                remainder = selectedCurrentDate - remainderTime,
                timeToDo = selectedCurrentDate,
                hasTime = hasCertainTime,
                notificationId = (System.currentTimeMillis() / 1000 / 60).toInt()
            )
            dayViewModel.insertToDO(todo1)
            NotificationUtils().setNotification(
                todo1.title,
                todo1.notificationId!!,
                todo1.remainder!!,
                this@MainActivity
            )
        } else if (!isToggleChecked) {
            todo1 = ToDo(
                0,
                title = title,
                doneStatus = status,
                priority = priority,
                remainder = null,
                timeToDo = selectedCurrentDate,
                hasTime = false,
                notificationId = null
            )
            dayViewModel.insertToDO(todo1)
//            NotificationUtils().setNotification(
//                todo1.title,
//                todo1.notificationId!!,
//                todo1.timeToDo!!,
//                this@MainActivity
//            )
        }
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        isToggleChecked = false
        fetchToDosForThatDay(selectedCurrentDate)
        clearFields()
    }

    private fun clearFields() {
        edit_text_in_dialog.setText("")
        radioGroup.check(R.id.radio_button_low)
        toggle_time_set.isChecked = false
    }

    fun setVisible() {
        time_picker.visibility = View.VISIBLE
        button_in_24_hours.visibility = View.VISIBLE
        button_an_hour_before.visibility = View.VISIBLE
        button_15_mins_before.visibility = View.VISIBLE
        view.visibility = View.VISIBLE
        view2.visibility = View.VISIBLE
        text_view_remind_me.visibility = View.VISIBLE
        imageView2.visibility = View.VISIBLE
    }

    fun setInvisible() {
        time_picker.visibility = View.GONE
        button_in_24_hours.visibility = View.GONE
        button_an_hour_before.visibility = View.GONE
        button_15_mins_before.visibility = View.GONE
        view.visibility = View.GONE
        view2.visibility = View.GONE
        text_view_remind_me.visibility = View.GONE
        imageView2.visibility = View.GONE
    }
}

