package engine.manager

import engine.LOG
import engine.type.World

class PhysicsManager(var world: World) {
    var isEnabled = true

    fun updateForEntities() {
        if(!isEnabled) return

        world.entitiesManager.getEntitiesList().forEach { entity ->
            var isNeedFalling = true

            // если энтити может упасть
            if(!entity.isCanMoveDown()) {
                isNeedFalling = false
            }

            // если ничего не мешает
            if(isNeedFalling) {
                entity.position.y += 2 // опускаем ниже
            }
        }
    }
}