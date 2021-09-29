package engine.block

import engine.Globals
import engine.drawImageTiled
import engine.type.World
import java.awt.Color
import java.awt.Graphics
import java.awt.image.ImageObserver

class Grass(override var world: World): Block(world) {
    override fun draw(g: Graphics) {
        g.drawImageTiled(Globals.textureLoader!!.getTexture("ground"), position.x, position.y, size.w, size.h)
    }
}