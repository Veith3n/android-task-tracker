package uni.aeh.tasktracker.core.data.time

import java.util.Calendar
import java.util.TimeZone
import kotlin.math.abs

fun mapDateToBeRepresentedCorrectlyInDeviceTimezone(dateMillis: Long): Long {
    val dayInMillis = 60 * 60 * 24 * 1000

    val startOfCurrentDay: Long = Calendar.getInstance().apply {
        timeZone = TimeZone.getDefault()
        set(Calendar.HOUR_OF_DAY, 0);
        set(Calendar.MINUTE, 0);
        set(Calendar.SECOND, 0);
        set(Calendar.MILLISECOND, 0)
    }.timeInMillis

    return if (startOfCurrentDay > dateMillis) {
        dateMillis + abs((startOfCurrentDay - dateMillis) % dayInMillis) - dayInMillis
    } else {
        dateMillis - abs(((dateMillis - startOfCurrentDay)) % dayInMillis)
    }
}
