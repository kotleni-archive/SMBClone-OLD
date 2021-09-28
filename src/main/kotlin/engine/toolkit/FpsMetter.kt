package engine.toolkit

class FpsMetter {
    private var lastTime: Long = 0
    private var lastFps = 60
    private var currentFps = 0

    fun doTick() {
        if(System.currentTimeMillis() - lastTime > 1000) { // если прошло 1 сек
            lastFps = currentFps
            currentFps = 0

            lastTime = System.currentTimeMillis() // сохраняем время
        }

        currentFps += 1
    }

    fun getFps(): Int {
        return lastFps
    }
}