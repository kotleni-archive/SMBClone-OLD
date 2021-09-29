package engine.block

import engine.Globals
import engine.drawImageTiled
import engine.type.World
import java.awt.Graphics

class Trap(override var world: World): Block(world) {
    override fun draw(g: Graphics) {
        g.drawImageTiled(Globals.spriteLoader!!.getSprite("trap").image!!, position.x, position.y, size.w, size.h)
    }
}