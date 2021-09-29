package engine.manager

import engine.type.World
import engine.entity.Entity
import engine.entity.Player

class EntitiesManager(var world: World) {
    private val entities = ArrayList<Entity>()

    fun addEntity(e: Entity) {
        entities.add(e)
    }

    fun removeEntity(e: Entity) {
        entities.remove(e)
    }

    fun getEntity(id: Int): Entity {
        return entities[id]
    }

    fun getEntitiesList(isHidePlayer: Boolean = false): List<Entity> {
        if(isHidePlayer) {
            val buff = ArrayList<Entity>()
            entities.forEach {
                if(!(it is Player)) { // если не игрок
                    buff.add(it)
                }
            }

            return buff
        }

        return entities
    }

    fun getPlayer(): Player? {
        entities.forEach {
            if(it is Player) return it
        }

        return null
    }
}