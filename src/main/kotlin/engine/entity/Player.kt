package engine.entity

import engine.type.Direction
import engine.World
import engine.type.Size
import java.awt.Color
import java.awt.Graphics
import java.awt.event.KeyEvent
import kotlin.concurrent.thread

class Player(override var world: World) : Entity(world) {
    override val size = Size(32, 32)

    override fun draw(g: Graphics) {
        g.color = Color.BLACK
        g.fillRect(position.x, position.y, size.w, size.h)
    }

    fun updateInput(keys: ArrayList<Int>) {
        keys.forEach {
            when(it) {
                KeyEvent.VK_A, KeyEvent.VK_LEFT -> { // left
                    if(isCanMove(Direction.LEFT))
                        position.x -= 2
                }

                KeyEvent.VK_D, KeyEvent.VK_RIGHT -> { // right
                    if(isCanMove(Direction.RIGHT))
                        position.x += 2
                }

                KeyEvent.VK_W, KeyEvent.VK_UP -> { // jump
                    if(isGrounded()) {
                        thread { for(i in 0..60) { position.y -= 2; Thread.sleep(1) } }
                    }
                }
            }
        }
    }
}