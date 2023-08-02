import kotleni.cs2eventsgrabber.CS2EventsGrabber
import kotleni.cs2eventsgrabber.CS2EventsListener
import kotleni.cs2eventsgrabber.CS2UIState
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test

class MainTest {
    // TODO: Detect game folder automatically
    private val gameFolderPath = "D:\\Steam\\steamapps\\common\\Counter-Strike Global Offensive"
    private val grabber = CS2EventsGrabber(gameFolderPath)

    @Test
    fun `Start grabber and listen all events`() {
        val listener = object: CS2EventsListener {
            override fun onSearchStarted() {
                println("onSearchStarted()")
            }

            override fun onSearchCanceled() {
                println("onSearchCanceled()")
            }

            override fun onSearchFound() {
                println("onSearchFound()")
            }

            override fun onSearchConfirmed() {
                println("onSearchConfirmed()")
            }

            override fun onSearchConfirmFailed() {
                println("onSearchConfirmFailed()")
            }

            override fun onGameEnded() {
                println("onGameEnded()")
            }

            override fun onRoundStarted(round: Int) {
                println("onRoundStarted($round)")
            }

            override fun onWarmupEnded() {
                println("onWarmupEnded()")
            }

            override fun onWrongCommand(command: String) {
                println("onWrongCommand($command)")
            }

            override fun onMessage(from: String, text: String, isDead: Boolean) {
                val logLine = "${if(isDead) "(DEAD) " else ""}$from : $text".trim()

                println("onMessage($logLine)")
            }

            override fun onUIStateChanged(state: CS2UIState) {
                println("onUIStateChanged(${state})")
            }
        }

        // Configure
        grabber.setEventsListener(listener)
        grabber.setUpdateDelay(700)

        // Start listener polling
        runBlocking {
            grabber.startPolling()
        }
    }
}