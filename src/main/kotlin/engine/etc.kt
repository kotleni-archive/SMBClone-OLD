package engine

import java.awt.Graphics
import java.awt.Image
import java.util.*

fun LOG(msg: Any) {
    if(Globals.DEBUG_MODE)
        Date().also {
            println("[${it.hours}:${it.minutes}] $msg")
        }
}

fun Graphics.drawImageTiled(image: Image, x: Int, y: Int, w: Int, h: Int) {
    var xx = 0
    while(xx < w) {
        var yy = 0
        while(yy < h) {
            this.drawImage(image, x + xx, y + yy, image.getWidth(null), image.getHeight(null), null)
            yy += image.getHeight(null)
        }

        xx += image.getWidth(null)
    }
}