package engine

import java.util.*

fun LOG(msg: Any) {
    if(Globals.DEBUG_MODE)
        Date().also {
            println("[${it.hours}:${it.minutes}] $msg")
        }
}