package engine.entity

import engine.type.Direction
import engine.World
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
            if(this.isCollide(it))
                return true
        }

        return false
    }

    // если нахоидтся в пределах мира
    open fun isInWorld(): Boolean {
        return CollisionHelper.isCollideEntityWithWorld(this, world)
    }

    // если касается любого блока
    open fun isCollideAnyBlock(): Boolean {
        world.blocksManager.getBlocksList().forEach {
            if(isCollide(it))
                return true
        }

        return false
    }

    // если касается другого энтити
    open fun isCollide(entity: Entity): Boolean {
        return CollisionHelper.isCollideEntityWithEntity(this, entity)
    }

    // если касается блока
    open fun isCollide(block: Block): Boolean {
        return CollisionHelper.isCollideEntityWithBlock(this, block)
    }

    // если энтити может подвинуться
    open fun isCanMove(direction: Direction): Boolean {
        world.blocksManager.getBlocksList().forEach {
            if(isCollide(it)) {
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