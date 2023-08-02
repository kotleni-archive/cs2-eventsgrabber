package kotleni.cs2eventsgrabber

interface CS2EventsListener {
    /**
    * Called when search started
    **/
    fun onSearchStarted()

    /**
    * Called when search canceled
    **/
    fun onSearchCanceled()

    /**
    * Called when search found
    **/
    fun onSearchFound()

    /**
    * Called when search confirmed
    **/
    fun onSearchConfirmed()

    /**
    * Called when search confirm failed
    **/
    fun onSearchConfirmFailed()

    /**
    * Called when game ended
    **/
    fun onGameEnded()

    /**
    * Called when round started
    * @param round Round number
    **/
    fun onRoundStarted(round: Int)

    /**
    * Called when warmup ended
    **/
    fun onWarmupEnded()

    /**
    * Called when wrong command
    * @param command Command name
    **/
    fun onWrongCommand(command: String)

    /**
    * Called when message received
    * @param from Message sender name
    * @param text Message text
    * @param isDead Is sender dead
    **/
    fun onMessage(from: String, text: String, isDead: Boolean)

    /**
    * Called when UI state changed
    * @param state New UI state
    * @see CS2UIState
    **/
    fun onUIStateChanged(state: CS2UIState)
}