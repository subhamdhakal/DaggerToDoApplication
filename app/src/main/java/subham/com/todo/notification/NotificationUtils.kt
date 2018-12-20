package subham.com.todo.notification

import android.app.Activity
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import java.util.*

/**
 * Created by devdeeds.com on 5/12/17.
 */
class NotificationUtils {
    fun setNotification(todoTitle:String,requestCode:Int,timeInMilliSeconds: Long, activity: Activity) {
        //------------  alarm settings start  -----------------//
        if (timeInMilliSeconds > 0) {
            val alarmManager = activity.getSystemService(Activity.ALARM_SERVICE) as AlarmManager
            val alarmIntent =
                Intent(activity.applicationContext, AlarmReceiver::class.java) // AlarmReceiver1 = broadcast receiver
            alarmIntent.putExtra("todoTitle",todoTitle)
            alarmIntent.putExtra("reason", "notification")
            alarmIntent.putExtra("timestamp", timeInMilliSeconds)
            val pendingIntent =
                PendingIntent.getBroadcast(activity, requestCode, alarmIntent, PendingIntent.FLAG_CANCEL_CURRENT)
//            if (BooleanConstants.isRepeatatingNotificationSet){
            alarmManager.set(AlarmManager.RTC_WAKEUP, timeInMilliSeconds, pendingIntent)
//            }else if (!BooleanConstants.isRepeatatingNotificationSet)
//            {
//                BooleanConstants.isRepeatatingNotificationSet=true
//            alarmManager.setRepeating(
//                AlarmManager.RTC_WAKEUP,
//                timeInMilliSeconds,
//                AlarmManager.INTERVAL_DAY,
//                pendingIntent
//            )
        }
        //------------ end of alarm settings  -----------------//
    }
}