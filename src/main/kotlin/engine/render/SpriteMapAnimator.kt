package engine.render

class SpriteMapAnimator(var map: SpriteMap, var frames: List<Int>) {
    private var currentFrame = 0

    fun doTick() {
        currentFrame += 1
        if(currentFrame > frames.count()-1) currentFrame = 0
    }

    fun getCurrentFrame(): Int {
        return frames[currentFrame]
    }
}