package engine.type

import engine.Globals
import engine.entity.Player
import engine.manager.BlocksManager
import engine.manager.EntitiesManager
import engine.manager.PhysicsManager
import engine.render.Camera
import engine.render.CameraFocus
import java.awt.Graphics

class World(var name: String) {
    val size = Size(0, 0)

    val entitiesManager = EntitiesManager(this)
    val physicsManager = PhysicsManager(this)
    val blocksManager = BlocksManager(this)
    
    fun drawAll(g: Graphics, c: Camera) {
        val player = entitiesManager.getPlayer()!!

        // рисуем всех энтити
        entitiesManager.getEntitiesList(isHidePlayer = true).forEach {
            it.draw(g)
        }

        // рисуем все блоки
        blocksManager.getBlocksList().forEach {
            if(c.isBlockInCamera(it)) it.draw(g)
        }

        // рисуем игрока
        player.draw(g)
    }

    fun getPlayer(): Player? {
        return entitiesManager.getPlayer()
    }
}