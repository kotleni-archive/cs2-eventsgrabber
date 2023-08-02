package kotleni.cs2eventsgrabber.extensions

fun String.withoutTime(): String {
    val timeExample = "08/02 13:49:47 "
    return removeRange(0 until timeExample.length)
}

fun String.withoutDeadMark(): String {
    return removePrefix("*МЕРТВ*")
}

fun String.withoutTimeAndDeadMark(): String {
    return withoutTime()
        .withoutDeadMark()
        .trim()
}

fun String.isDeadMessage(): Boolean {
    return contains("*МЕРТВ*")
}

fun String.removePrivateMarks(): String {
    if(this.split(") ").size < 2)
        return this.trim()

    val a = this.split(") ")[1]
    if(a.contains(" @ ")) { // Alive message
        return a.split(" @ ")[0].trim()
    } else { // Dead message
        return a.trim()

    }
}

fun String.isMessage(from: String, text: String): Boolean {
    val wordsBlackList = listOf("CFuncTrackTrain::Find()")
    return contains(" : ") && !from.contains(' ') && wordsBlackList.none { word -> contains(word) }
}