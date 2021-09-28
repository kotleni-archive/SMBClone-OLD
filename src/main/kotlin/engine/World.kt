package engine

import engine.block.Block
import engine.block.Ground
import engine.entity.Player
import engine.manager.BlocksManager
import engine.manager.EntitiesManager
import engine.manager.PhysicsManager
import engine.type.Size
import org.json.JSONObject
import java.awt.Graphics
import java.io.File

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