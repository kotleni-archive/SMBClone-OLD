package engine.block

import engine.Globals
import engine.drawImageTiled
import engine.type.World
import java.awt.Graphics

class Stone2(override var world: World): Block(world) {
    override fun draw(g: Graphics) {
        g.drawImageTiled(Globals.spriteLoader!!.getSprite("stone2").image!!, position.x, position.y, size.w, size.h)
    }
}