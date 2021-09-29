package engine.type

data class Size(
    var w: Int,
    var h: Int
) {
    fun update(w: Int, h: Int) {
        this.w = w
        this.h = h
    }
}
