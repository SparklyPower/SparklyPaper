package net.sparklypower.sparklypaper

import com.mojang.logging.LogUtils
import java.time.LocalDateTime
import java.time.Month
import java.time.ZoneOffset
import java.util.concurrent.*

class HalloweenManager {
    companion object {
        private val LOGGER = LogUtils.getLogger()
    }
    private var spookySeasonStartEpoch = 0L
    private var spookySeasonEndEpoch = 0L
    private var halloweenStartEpoch = 0L
    private var halloweenEndEpoch = 0L
    private var executor = Executors.newSingleThreadScheduledExecutor(object: ThreadFactory {
        override fun newThread(p0: Runnable): Thread {
            val thread = Thread(p0)
            thread.name = "halloween-timer-updater"
            thread.priority = 1 // Minimum priority
            return thread
        }
    })
    private var latch = CountDownLatch(1)

    fun startHalloweenEpochTask() {
        var isFirst = true
        executor.scheduleAtFixedRate({
            updateEpoch()
            if (isFirst)
                latch.countDown()
            isFirst = false
        }, 0L, 90L, TimeUnit.DAYS) // Every 90 days
    }

    fun waitUntilEpochHasBeenUpdated() {
        latch.await()
    }

    fun updateEpoch() {
        LOGGER.info("Updating Spooky Season and Halloween Time")
        this.spookySeasonStartEpoch = getEpochMillisAtDate(20, Month.OCTOBER, false)
        this.spookySeasonEndEpoch = getEpochMillisAtDate(3, Month.NOVEMBER, true)
        this.halloweenStartEpoch = getEpochMillisAtDate(31, Month.OCTOBER, false)
        this.halloweenEndEpoch = getEpochMillisAtDate(31, Month.OCTOBER, true)
        LOGGER.info("Updated Spooky Season and Halloween Time!")
    }

    fun isSpookySeason() = System.currentTimeMillis() in spookySeasonStartEpoch until spookySeasonEndEpoch
    fun isHalloween() = System.currentTimeMillis() in halloweenStartEpoch until halloweenEndEpoch

    private fun getEpochMillisAtDate(dayOfMonth: Int, month: Month, isEnd: Boolean): Long {
        // Get the current year
        val currentYear = LocalDateTime.now().year

        // Define the target date (20/10/CurrentYear at midnight)
        val targetDate = LocalDateTime.of(
            currentYear,
            month,
            dayOfMonth,
            if (isEnd)
                23
            else
                0,
            if (isEnd)
                59
            else
                0,
            if (isEnd)
                59
            else
                0,
            if (isEnd)
                999_999_999
            else
                0,
        )

        // Check if the target date is in the past
        val now = LocalDateTime.now()
        val adjustedDate = if (now.isAfter(targetDate)) {
            // If in the past, adjust to the same date in the next year
            targetDate.plusYears(1)
        } else {
            // If in the future or today, use the original target date
            targetDate
        }

        // Convert the adjusted date to epoch time in milliseconds
        return adjustedDate.atZone(ZoneOffset.systemDefault()).toInstant().toEpochMilli()
    }
}