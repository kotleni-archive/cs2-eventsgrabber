package kotleni.cs2eventsgrabber

import kotleni.cs2eventsgrabber.extensions.isDeadMessage
import kotleni.cs2eventsgrabber.extensions.isMessage
import kotleni.cs2eventsgrabber.extensions.removePrivateMarks
import kotleni.cs2eventsgrabber.extensions.withoutTimeAndDeadMark
import java.io.File

class CS2LogsEventsParser(val file: File) {
    private var eventsListener: CS2EventsListener? = null
    private val CS2Match = CS2Match()
    private var lastContent = ""

    fun process() {
        if(file.exists()) {
            val text = file.readText()

            if(lastContent.isEmpty())
                lastContent = text

            if(lastContent != text) {
                val newContent = text.substring(lastContent.length)
                lastContent = text
                parse(newContent)
            }
        }
    }

    fun setEventsListener(listener: CS2EventsListener?) {
        eventsListener = listener
    }

    private fun parse(content: String) {
        content.split('\n')
            .forEach {
                if(it.contains("matchmaking: 1")) { // OR 0 if stoped
                    eventsListener?.onSearchStarted()
                }
                if(it.contains("matchmaking: 4")) {
                    eventsListener?.onSearchConfirmed()

                    CS2Match.rounds = 0
                }
                if(it.contains("Unknown command: Test_message")) {
                    eventsListener?.onWrongCommand(it.replace("Unknown command: ", ""))
                }
                if(it.contains("error: \"Failed to ready up\"")) {
                    eventsListener?.onSearchConfirmFailed()
                }
                if(it.contains("_v2_MatchmakingGC2ClientReserve,")) {
                    eventsListener?.onSearchFound()
                }
                if(it.contains("_v2_MatchmakingGC2ClientHello,")) {
                    eventsListener?.onGameEnded()
                }
                if(it.contains("Panel id-sb-name__nameicons has fill-parent-flow for width") && CS2Match.isLoaded) {
                    if(CS2Match.rounds == 0)
                        eventsListener?.onRoundStarted(CS2Match.rounds)

                    eventsListener?.onRoundStarted(CS2Match.rounds)

                    CS2Match.rounds += 1
                }
                if(it.contains(" : ")) {
                    val from = it.substringBefore(" : ")
                        .withoutTimeAndDeadMark()
                        .removePrivateMarks()
                    val text = it.substringAfter(" : ")

                    if(it.isMessage(from, text))
                        eventsListener?.onMessage(from, text, it.isDeadMessage())
                }
                if(it.contains("-> CSGO_GAME_UI_STATE_LOADINGSCREEN")) { // BUG: can be invoked multiple times
                    if(CS2Match.state != CS2UIState.LOADING)
                        eventsListener?.onUIStateChanged(CS2UIState.LOADING)
                    CS2Match.isLoaded = true
                }
                if(it.contains("-> CSGO_GAME_UI_STATE_MAINMENU")) { // BUG: invoked multiple times
                    if(CS2Match.state != CS2UIState.MAIN_MENU)
                        eventsListener?.onUIStateChanged(CS2UIState.MAIN_MENU)
                    CS2Match.isLoaded = false
                }
                if(it.contains("-> CSGO_GAME_UI_STATE_INGAME")) { // BUG: invoked multiple times
                    if(CS2Match.state != CS2UIState.IN_GAME)
                        eventsListener?.onUIStateChanged(CS2UIState.IN_GAME)
                    CS2Match.isPaused = false
                }
                if(it.contains("-> CSGO_GAME_UI_STATE_PAUSEMENU")) { // BUG: invoked multiple times
                    if(CS2Match.state != CS2UIState.PAUSE_MENU)
                        eventsListener?.onUIStateChanged(CS2UIState.PAUSE_MENU)
                    CS2Match.isPaused = true
                }
            }
    }
}