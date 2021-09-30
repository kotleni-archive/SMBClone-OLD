package engine.render

class SpriteMapAnimator(var map: SpriteMap, var frames: List<Int>, var fps: Int) {
    private var currentFrame = 0
    private var lastTime = System.currentTimeMillis()

    fun doTick() {
        if(System.currentTimeMillis() - lastTime > 1000 / fps) {
            currentFrame += 1
            if(currentFrame > frames.count()-1) currentFrame = 0

            lastTime = System.currentTimeMillis()
        }
    }

    fun getCurrentFrame(): Int {
        return frames[currentFrame]
    }
}