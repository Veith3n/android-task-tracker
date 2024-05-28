package uni.aeh.tasktracker.core.data.time

import io.mockk.every
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import java.util.Calendar
import java.util.TimeZone
import kotlin.math.abs

class TimeUtilsTest {
    // Diff between UTC and Bangkok is always 7 hours
    private val sevenHourInMillis = 7 * 60 * 60 * 1000L
    private val mockedTimeZoneDiffInMillis = sevenHourInMillis

    @BeforeEach
    fun setup(): Unit {
        mockkStatic(TimeZone::class)
        every { TimeZone.getDefault() } returns TimeZone.getTimeZone("Asia/Bangkok")
    }

    @AfterEach
    fun teardown() {
        unmockkStatic(TimeZone::class)
    }

    @Test
    fun `test date before current day`() {
        val beginningOfYesterday = Calendar.getInstance().apply {
            timeZone = TimeZone.getTimeZone("UTC")
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
            add(Calendar.DAY_OF_YEAR, -1)
        }.timeInMillis

        val mappedMillis = mapDateToBeRepresentedCorrectlyInDeviceTimezone(beginningOfYesterday)

        assertEquals(
            beginningOfYesterday - mappedMillis,
            mockedTimeZoneDiffInMillis
        )
    }

    @Test
    fun `returns correctly adjusted time after current day`() {
        val beginningOfTomorrow = Calendar.getInstance().apply {
            timeZone = TimeZone.getTimeZone("UTC")
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
            add(Calendar.DAY_OF_YEAR, 1)
        }.timeInMillis

        val mappedMillis = mapDateToBeRepresentedCorrectlyInDeviceTimezone(beginningOfTomorrow)

        assertEquals(
            beginningOfTomorrow - mappedMillis,
            mockedTimeZoneDiffInMillis
        )
    }

    @Test
    fun `returns correctly adjusted time on the same day as current one`() {
        val beggingOfCurrentDayInUtc = Calendar.getInstance().apply {
            timeZone = TimeZone.getTimeZone("UTC")
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis

        val mappedMillis = mapDateToBeRepresentedCorrectlyInDeviceTimezone(beggingOfCurrentDayInUtc)

        assertEquals(abs(beggingOfCurrentDayInUtc - mappedMillis), mockedTimeZoneDiffInMillis)
    }

}