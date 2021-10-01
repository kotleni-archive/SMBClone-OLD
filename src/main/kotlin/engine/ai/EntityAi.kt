package engine.ai

import engine.entity.Entity
import engine.helper.CollisionHelper
import engine.type.Direction
import kotlin.concurrent.thread
import kotlin.random.Random
import kotlin.random.nextInt

class EntityAi(var entity: Entity) {
    val random = Random(hashCode())

    init {
        // AI
        thread {
            var isSleep = false

            while (true) {
                Thread.sleep(7)

                if(random.nextInt(0..200) == 100)
                    isSleep = !isSleep

                val p = random.nextInt(0..500)
                if(p == 10) entity.direction = Direction.RIGHT
                if(p == 20) entity.direction = Direction.LEFT

                // если не может туда идти
                if(!entity.isCanMove(entity.direction))
                    if(random.nextBoolean()) {
                        when (entity.direction) {
                            Direction.LEFT -> { entity.direction = Direction.RIGHT }
                            Direction.RIGHT -> { entity.direction = Direction.LEFT }
                        }
                    } else {
                        entity.jump()
                    }

                // перепрыгивание
                if(CollisionHelper.isSpaceNext(entity, entity.direction, entity.world))
                    entity.jump()

                if(random.nextInt(0..10) == 10 && entity.isCollide(entity.world.getPlayer()!!)) {
                    //jump()
                    isSleep = false
                }

                if(!isSleep) { entity.move(2); entity.animator?.doTick() }
            }
        }
    }
}