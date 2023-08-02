package kotleni.cs2eventsgrabber

import kotlinx.coroutines.delay
import java.io.File

class CS2EventsGrabber(gameFolderPath: String) {
    private val parser = CS2LogsEventsParser(File("$gameFolderPath\\game\\csgo\\console.log"))

    private var updateDelay = 1000L
    private var isPolling = false

    fun setUpdateDelay(delay: Long) {
        updateDelay = delay
    }

    fun setEventsListener(listener: CS2EventsListener?) {
        parser.setEventsListener(listener)
    }

    suspend fun startPolling() {
        isPolling = true
        while(isPolling) {
            parser.process()
            delay(updateDelay)
        }
    }

    fun stopPolling() {
        isPolling = false
    }
}