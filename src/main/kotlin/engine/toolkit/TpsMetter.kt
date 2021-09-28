package engine.toolkit

class TpsMetter {
    private var lastTime: Long = 0
    private var lastTps = 60
    private var currentTps = 0

    fun doTick() {
        if(System.currentTimeMillis() - lastTime > 1000) { // если прошло 1 сек
            lastTps = currentTps
            currentTps = 0

            lastTime = System.currentTimeMillis() // сохраняем время
        }

        currentTps += 1
    }

    fun getTps(): Int {
        return lastTps
    }
}