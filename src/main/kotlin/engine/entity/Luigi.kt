package engine.entity

import engine.Globals
import engine.ai.EntityAi
import engine.render.SpriteMapAnimator
import engine.type.Direction
import engine.type.Size
import engine.type.World
import java.awt.Graphics

class Luigi(override var world: World): Entity(world) {
    override val size: Size = Size(32, 32)
    override val animator = SpriteMapAnimator(Globals.spriteLoader!!.getSpriteMap("luigi"), listOf(0, 1, 2), 8)

    private val entityAi = EntityAi(this)

    override fun onDraw(g: Graphics) {
        animator.map.apply {
            isFlip = (direction != Direction.LEFT)
            draw(g, position,  size, animator.getCurrentFrame())
        }
    }
}