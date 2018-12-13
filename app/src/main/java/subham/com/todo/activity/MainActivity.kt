package subham.com.todo.activity

import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import com.github.badoualy.datepicker.DatePickerTimeline
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.bottom_sheet_add_todo.*
import subham.com.todo.R
import subham.com.todo.R.id.*
import subham.com.todo.adapter.DayAdapter
import subham.com.todo.adapter.SwipeToDeleteCallback
import subham.com.todo.base.BaseActivity
import subham.com.todo.database.ToDo
import subham.com.todo.util.Priority
import subham.com.todo.viewmodel.DayViewModel
import java.util.*
import javax.inject.Inject

class MainActivity : BaseActivity() {
    var priority = Int.MIN_VALUE
    val adapter = DayAdapter(this)
    var isToggleChecked=false
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>
    override fun getLayout(): Int {
        return R.layout.activity_main
    }
    var selectedCurrentDate:Long= Calendar.getInstance().timeInMillis

    val TAG = MainActivity::class.java.simpleName
    @Inject
    lateinit var dayViewModel: DayViewModel
    var listOfToDo: MutableList<ToDo> = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        reclycler_view.layoutManager = LinearLayoutManager(this)
        reclycler_view.adapter = adapter

        toggle_time_set.setOnCheckedChangeListener { view, isChecked ->
            if (isChecked)
                isToggleChecked=isChecked
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
            if(isChecked)
                time_picker.visibility=View.VISIBLE
            else
                time_picker.visibility=View.GONE
        }
        date_picker.onDateSelectedListener =
                DatePickerTimeline.OnDateSelectedListener { year, month, day, index ->
                    val calendar = Calendar.getInstance()
                    calendar.set(year, month, day)
                    Toast.makeText(this, "Time in millis" + calendar.timeInMillis, Toast.LENGTH_SHORT).show()
                    Log.e(TAG,"time in milllis"+calendar.timeInMillis)
                    selectedCurrentDate=calendar.timeInMillis
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
                        image_view_arrow.animate().rotation(0f).setDuration(500).start()
                    }
                    BottomSheetBehavior.STATE_HIDDEN -> {
                    }
                    BottomSheetBehavior.STATE_EXPANDED -> {
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
                val adapter = reclycler_view.adapter as DayAdapter
                dayViewModel.deleteNote(listOfToDo[viewHolder.adapterPosition])
//                adapter.removeAt(viewHolder.adapterPosition)
//                dayViewModel.deleteNote(listOfToDo[viewHolder.position])
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(reclycler_view)
    }

    private fun fetchToDosForThatDay(selectedCurrentDate: Long) {
        val calendar=Calendar.getInstance()
        calendar.timeInMillis=selectedCurrentDate
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

        dayViewModel.getTodosForToday(todayStart,todayEnd).observe(this, android.arch.lifecycle.Observer { it ->
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

        dayViewModel.getTodosForToday(todayStart,todayEnd).observe(this, android.arch.lifecycle.Observer { it ->
            adapter.setData(it)
            listOfToDo = it as MutableList<ToDo>

        })
    }

    private fun saveToDo() {
        val title = edit_text_in_dialog.text.toString()
        var status = false
        var hasCertainTime=false

        if (isToggleChecked){
            hasCertainTime=true
            //get that day and set its time and minutes to that of time picker, get that time in milliseconds and the pass it as a parameter
            val calendar=Calendar.getInstance()
            calendar.timeInMillis=selectedCurrentDate
            calendar.set(Calendar.HOUR_OF_DAY,time_picker.hour)
            calendar.set(Calendar.MINUTE,time_picker.minute)
            selectedCurrentDate=calendar.timeInMillis
        }
              val todo = ToDo( null, title, status, priority, "15:00",selectedCurrentDate,hasCertainTime)
        dayViewModel.insertToDO(todo)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        isToggleChecked=false
        clearFields()
    }

    private fun clearFields() {
        edit_text_in_dialog.setText("")
        radioGroup.check(R.id.radio_button_low)
        fetchToDosForThatDay(selectedCurrentDate)
    }
}

