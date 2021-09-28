package engine.type

import engine.manager.BlocksManager
import engine.manager.EntitiesManager
import engine.manager.PhysicsManager
import java.awt.Graphics

class World(var size: Size) {
    val entitiesManager = EntitiesManager(this)
    val physicsManager = PhysicsManager(this)
    val blocksManager = BlocksManager(this)
    
    fun drawAll(g: Graphics) {
        // рисуем всех энтити
        entitiesManager.getEntitiesList().forEach {
            it.draw(g)
        }

        // рисуем все блоки
        blocksManager.getBlocksList().forEach {
            it.draw(g)
        }
    }
}