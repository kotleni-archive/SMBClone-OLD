package engine.entity

import engine.type.Direction
import engine.type.World
import engine.block.Block
import engine.helper.CollisionHelper
import engine.type.Pos
import engine.type.Size
import java.awt.Graphics

open class Entity(open var world: World) {
    open val position = Pos(0, 0)
    open val size = Size(0, 0)

    // рисовать энтити
    open fun draw(g: Graphics) { }

    // если стоит на земле
    open fun isGrounded(): Boolean {
        world.blocksManager.getBlocksList().forEach {
            if(world.gameView.camera.isBlockInCamera(it)) {
                if (!this.isCanMoveDown()) // deleted: isCanMoveUp() &&
                    return true
            }
        }

        return false
    }

    // если нахоидтся в пределах мира
    open fun isInWorld(): Boolean {
        return CollisionHelper.isCollideEntityWithWorld(this, world)
    }

    // если касается другого энтити
    open fun isCollide(entity: Entity): Boolean {
        return CollisionHelper.isCollideEntityWithEntity(this, entity)
    }

    // если касается блока
    open fun isCollide(block: Block): Boolean {
        if(!world.gameView.camera.isBlockInCamera(block)) return false

        return CollisionHelper.isCollideEntityWithBlock(this, block)
    }

    // если может двигаться в верх
    open fun isCanMoveUp(): Boolean {
        world.blocksManager.getBlocksList().forEach {
            if(world.gameView.camera.isBlockInCamera(it))
            if(CollisionHelper.isCollideBlockVerticalTop(this, it)) { // если не может
                return false
            }
        }

        return true
    }

    // если может двигаться вниз
    open fun isCanMoveDown(): Boolean {
        world.blocksManager.getBlocksList().forEach {
            if(world.gameView.camera.isBlockInCamera(it))
            if(CollisionHelper.isCollideBlockVerticalBottom(this, it)) { // если не может
                return false
            }
        }

        return true
    }

    // если энтити может подвинуться
    open fun isCanMove(direction: Direction): Boolean {
        world.blocksManager.getBlocksList().forEach {
            if(world.gameView.camera.isBlockInCamera(it))
            if(!CollisionHelper.isCollideBlockVerticalTop(this, it)) {
                if(when(direction) { // проверка на колизиию по сторонам
                    Direction.LEFT -> { CollisionHelper.isCollideBlockHorizontalLeft(this, it) }
                    Direction.RIGHT -> { CollisionHelper.isCollideBlockHorizontalRight(this, it) }
                }) { // если нельзя двигаться
                    if(!CollisionHelper.isCollideBlockVerticalBottom(this, it))
                        return false
                }
            }
        }

        return true
    }
}