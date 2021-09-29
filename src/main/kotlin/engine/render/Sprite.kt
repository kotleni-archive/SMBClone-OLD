package engine.render

import engine.type.Pos
import engine.type.Size
import java.awt.Graphics
import java.awt.Image

open class Sprite(var image: Image? = null) {
    var isFlip = false

    open fun draw(g: Graphics, pos: Pos, size: Size, frame: Int) {
        if(image == null) return

        if(isFlip)
            g.drawImage(image, pos.x, pos.y, size.w, size.h, null)
        else
            g.drawImage(image, pos.x + size.w, pos.y, -size.w, size.h, null)
    }
}