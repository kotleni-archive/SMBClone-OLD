package engine.manager

import engine.World

class PhysicsManager(var world: World) {
    fun updateForEntities() {
        world.entitiesManager.getEntitiesList().forEach { entity ->
            var isNeedMove = true

            if(!entity.isInWorld()) { // если не в пределах мира
                isNeedMove = false
            }

            // проверка колизии с блоками
            world.blocksManager.getBlocksList().forEach {  block ->
                if(entity.isCollide(block)) { // если касается блока
                    isNeedMove = false
                }
            }

            // проверка колизии с энтити
            world.entitiesManager.getEntitiesList().forEach {  entity2 ->
                if(entity != entity2 && entity.isCollide(entity2)) { // если касается энтити
                    isNeedMove = false
                }
            }

            // если ничего не мешает
            if(isNeedMove) {
                entity.position.y += 2 // опускаем ниже
            }
        }
    }
}