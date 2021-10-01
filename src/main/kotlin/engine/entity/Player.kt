package engine.entity

import engine.Globals
import engine.render.CameraFocus
import engine.render.SpriteMapAnimator
import engine.type.Direction
import engine.type.Pos
import engine.type.World
import engine.type.Size
import engine.ui.etc.UIKey
import java.awt.Graphics
import java.awt.event.KeyEvent
import kotlin.concurrent.thread

class Player(override var world: World) : Entity(world), CameraFocus {
    override val size = Size(32, 32)
    override val animator = SpriteMapAnimator(Globals.spriteLoader!!.getSpriteMap("player"), listOf(2, 3, 4), 8)

    fun dash() {
        when(direction) {
            Direction.LEFT -> { thread { for(i in 0..2) { if(!isCanMove(direction)) { break }; position.x -= 1; Thread.sleep(2) } } }
            Direction.RIGHT -> { thread { for(i in 0..2) { if(!isCanMove(direction)) { break }; position.x += 1; Thread.sleep(2) } } }
        }
    }

    override fun onDraw(g: Graphics) {
        animator.map.apply {
            isFlip = (direction != Direction.LEFT)
            draw(g, position,  size, if(isGrounded()) animator.getCurrentFrame() else 5)
        }
        //g.drawImage(Globals.spriteLoader!!.getSpriteMap("player")., position.x, position.y, size.w, size.h, null)
    }

    fun updateInput(keys: List<UIKey>) {
        keys.forEach {
            when(it.keyCode) {
                KeyEvent.VK_A, KeyEvent.VK_LEFT -> { // left
                    animator.doTick()
                    direction = Direction.LEFT
                    if(isInWorld() && isCanMove(Direction.LEFT))
                        position.x -= 2
                }

                KeyEvent.VK_D, KeyEvent.VK_RIGHT -> { // right
                    animator.doTick()
                    direction = Direction.RIGHT
                    if(isInWorld() && isCanMove(Direction.RIGHT))
                        position.x += 2
                }

                KeyEvent.VK_W, KeyEvent.VK_UP -> { // jump
                    if(isGrounded())
                        jump()
                }

                KeyEvent.VK_SHIFT -> { // dash
                    animator.doTick()
                    if(isCanMove(direction))
                        dash()
                }
            }
        }
    }
}