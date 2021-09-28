package engine.manager

import engine.type.World

class PhysicsManager(var world: World) {
    fun updateForEntities() {
        world.entitiesManager.getEntitiesList().forEach { entity ->
            var isNeedMove = true

            if(!entity.isInWorld()) { // если не в пределах мира
                entity.position.x = 0
                entity.position.y = 0
            }

            // проверка колизии с блоками
            world.blocksManager.getBlocksList().forEach {  block ->
                if(entity.isCollide(block) && !entity.isCanMoveDown()) { // если касается блока
                    isNeedMove = false
                }
            }

            // проверка колизии с энтити
            world.entitiesManager.getEntitiesList().forEach {  entity2 ->
                if(entity != entity2 && entity.isCollide(entity2) && !entity.isCanMoveDown()) { // если касается энтити
                    isNeedMove = false
                }
            }

            // если прилип к потолку, отцепляем
            if(!entity.isCanMoveUp()) {
                isNeedMove = true
            }

            // если ничего не мешает
            if(isNeedMove) {
                entity.position.y += 2 // опускаем ниже
            }
        }
    }
}