package engine.block

import engine.type.World
import engine.type.Pos
import engine.type.Size
import java.awt.Graphics

open class Block(open var world: World) {
    open var position = Pos(0, 0)
    open var size = Size(0, 0)

    open fun draw(g: Graphics) { }
}