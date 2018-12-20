package subham.com.todo.util

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import java.text.SimpleDateFormat
import java.util.*

fun Long.getFormattedStringFromTimeStamp(): String {
    val simpleDateFormat = SimpleDateFormat("d MMMM yyyy")
    val calendar = Calendar.getInstance()
    calendar.time = Date(this)
    val strDate = simpleDateFormat.format(calendar.time)
    var daySuffix = " "
    val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
    if (dayOfMonth > 3 && dayOfMonth < 21)
        daySuffix = "th "
    else {
        when (dayOfMonth % 10) {
            1 -> daySuffix = "st "
            2 -> daySuffix = "nd "
            3 -> daySuffix = "rd "
            else -> daySuffix = "th "
        }
    }
    return strDate.replaceFirst(" ".toRegex(), daySuffix)
}

fun getTodayStartTimeStamp(): Long {
    val calendar = Calendar.getInstance()
    calendar.set(Calendar.HOUR_OF_DAY, 0)
    calendar.set(Calendar.MINUTE, 0)
    calendar.set(Calendar.SECOND, 0)
    calendar.set(Calendar.MILLISECOND, 0)
    return calendar.timeInMillis
}

fun getTodayEndTimeStamp(): Long{
    val calendar = Calendar.getInstance()
    calendar.set(Calendar.HOUR_OF_DAY, 23)
    calendar.set(Calendar.MINUTE, 59)
    calendar.set(Calendar.SECOND, 59)
    calendar.set(Calendar.MILLISECOND, 999)
    return calendar.timeInMillis
}

fun Long.deadlineExpired(): Boolean {
    val calendar = Calendar.getInstance()
    val currentTime = calendar.timeInMillis

    calendar.timeInMillis = this;
    calendar.set(Calendar.HOUR_OF_DAY, 23)
    calendar.set(Calendar.MINUTE, 59)
    calendar.set(Calendar.SECOND, 59)
    calendar.set(Calendar.MILLISECOND, 999)

    return currentTime > calendar.timeInMillis
}

fun Long.getDate(): String {
    val simpleDateFormat = SimpleDateFormat("MMMM")
    return simpleDateFormat.format(Date(this))
}

fun Long.getDateWithPattern(pattern: String): String {
    val simpleDateFormat = SimpleDateFormat(pattern)
    return simpleDateFormat.format(Date(this))
}

fun Calendar.getDayStartTimeStamp(): Long {
    this.set(Calendar.HOUR_OF_DAY, 0)
    this.set(Calendar.MINUTE, 0)
    this.set(Calendar.SECOND, 0)
    this.set(Calendar.MILLISECOND, 0)
    return this.timeInMillis
}

fun Calendar.getDayEndTimeStamp(): Long {
    this.set(Calendar.HOUR_OF_DAY, 23)
    this.set(Calendar.MINUTE, 59)
    this.set(Calendar.SECOND, 59)
    this.set(Calendar.MILLISECOND, 999)
    return this.timeInMillis
}

fun getTodayCalender(): Calendar {
    var calendar = Calendar.getInstance()
    calendar.set(Calendar.HOUR_OF_DAY, 0)
    calendar.set(Calendar.MINUTE, 0)
    calendar.set(Calendar.SECOND, 0)
    calendar.set(Calendar.MILLISECOND, 0)
    return calendar
}

fun getAddedYear(year: Int): Long {
    val cal = Calendar.getInstance()
    cal.add(Calendar.YEAR, year);
    return cal.timeInMillis
}

fun getThreeMonthEarlierDate(): Long {
    val c = Calendar.getInstance()
    c.add(Calendar.MONTH, -3)
    return c.timeInMillis
}

fun convertSecondsToHMmSs(seconds: Long): String {
    val s = seconds % 60
    val m = seconds / 60 % 60
    val h = seconds / (60 * 60) % 24
    return String.format("%d:%02d", h, m)
}
fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}
