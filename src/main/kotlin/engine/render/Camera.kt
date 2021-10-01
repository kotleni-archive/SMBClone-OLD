package engine.render

import engine.Globals
import engine.block.Block
import engine.entity.Entity
import engine.type.World
import java.awt.Dimension
import java.awt.Graphics
import java.awt.Rectangle
import kotlin.concurrent.thread

class Camera(var world: World) {
    fun getScrollX(): Int {
        return world.getPlayer()!!.position.x - (Globals.WINDOW_WIDTH / 2)
    }

    fun getScrollY(): Int {
        return world.getPlayer()!!.position.y - (Globals.WINDOW_HEIGHT / 2)
    }

    fun isBlockInCamera(block: Block): Boolean {
        val cameraRect = Rectangle().also {
            it.x = getScrollX()
            it.y = getScrollY()
            it.width = Globals.WINDOW_WIDTH
            it.height = Globals.WINDOW_HEIGHT
        }

        val blockRect = Rectangle().also {
            it.x = block.position.x
            it.y = block.position.y
            it.width = block.size.w
            it.height = block.size.h
        }

        return cameraRect.intersects(blockRect)
    }

    fun isEntityInCamera(entity: Entity): Boolean {
        val cameraRect = Rectangle().also {
            it.x = getScrollX()
            it.y = getScrollY()
            it.width = Globals.WINDOW_WIDTH
            it.height = Globals.WINDOW_HEIGHT
        }

        val blockRect = Rectangle().also {
            it.x = entity.position.x
            it.y = entity.position.y
            it.width = entity.size.w
            it.height = entity.size.h
        }

        return cameraRect.intersects(blockRect)
    }

    fun drawAll(g: Graphics) {
        // прокручиваем отрисовку
        g.translate(-getScrollX(), -getScrollY())

        // рисуем мир
        world.drawAll(g, this)

        // восстаналиваем прокрутку
        g.translate(getScrollX(), getScrollY())
    }
}