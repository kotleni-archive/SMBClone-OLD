package engine.type

data class Pos(
    var x: Int,
    var y: Int
) {
    fun update(x: Int, y: Int) {
        this.x = x
        this.y = y
    }
}