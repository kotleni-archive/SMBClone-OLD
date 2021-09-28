package engine.block

import engine.World
import engine.type.Pos
import engine.type.Size
import java.awt.Color
import java.awt.Graphics

class Ground(override var world: World): Block(world) {
    override fun draw(g: Graphics) {
        g.color = Color.DARK_GRAY
        g.fillRect(position.x, position.y, size.w, size.h)
    }
}