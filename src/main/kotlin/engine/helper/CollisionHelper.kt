package engine.helper

import engine.Constants
import engine.type.World
import engine.block.Block
import engine.entity.Entity
import java.awt.Rectangle

object CollisionHelper {
    fun isCollideEntityWithEntity(entity: Entity, entity2: Entity): Boolean {
        val p0 = Rectangle().also {
            it.x = entity.position.x
            it.y = entity.position.y
            it.width = entity.size.w
            it.height = entity.size.h
        }

        val p1 = Rectangle().also {
            it.x = entity2.position.x
            it.y = entity2.position.y
            it.width = entity2.size.w
            it.height = entity2.size.h
        }

        if(p0.intersects(p1))
            return true

        return false
    }

    fun isCollideEntityWithBlock(entity: Entity, block: Block): Boolean {
        val p0 = Rectangle().also {
            it.x = entity.position.x
            it.y = entity.position.y
            it.width = entity.size.w
            it.height = entity.size.h
        }

        val p1 = Rectangle().also {
            it.x = block.position.x
            it.y = block.position.y
            it.width = block.size.w
            it.height = block.size.h
        }

        if(p0.intersects(p1))
            return true

        return false
    }

    fun isCollideEntityWithWorld(entity: Entity, world: World): Boolean {
        val p0 = Rectangle().also {
            it.x = entity.position.x
            it.y = entity.position.y
            it.width = entity.size.w
            it.height = entity.size.h
        }

        val p1 = Rectangle().also {
            it.x = 0
            it.y = 0
            it.width = world.size.w
            it.height = world.size.h
        }

        if(p0.intersects(p1))
            return true

        return false
    }

    // проверить, соприкасается ли энтити с блоком слева
    fun isCollideBlockHorizontalLeft(entity: Entity, block: Block): Boolean {
        val offset = Constants.COLLIDE_MARGIN
        if(entity.position.x < (block.position.x + block.size.w)) {
            if(entity.position.x > (block.position.x + block.size.w - offset)) {
                return true
            }
        }

        return false
    }

    // проверить, соприкасается ли энтити с блоком справа
    fun isCollideBlockHorizontalRight(entity: Entity, block: Block): Boolean {
        val offset = Constants.COLLIDE_MARGIN
        if((entity.position.x + entity.size.w) > block.position.x) {
            if((entity.position.x + entity.size.w - offset) < block.position.x) {
                return true
            }
        }

        return false
    }

    // проверить, соприкасается ли энтити с блоком внизу
    fun isCollideBlockVerticalBottom(entity: Entity, block: Block): Boolean {
        val offset = Constants.COLLIDE_MARGIN
        if((entity.position.y + entity.size.h - offset) < (block.position.y)) {
            return true
        }

        return false
    }

    // проверить, соприкасается ли энтити с блоком вверху
    fun isCollideBlockVerticalTop(entity: Entity, block: Block): Boolean {
        val offset = Constants.COLLIDE_MARGIN
        if((entity.position.y) > (block.position.y + block.size.h - offset)) {
            return true
        }

        return false
    }
}