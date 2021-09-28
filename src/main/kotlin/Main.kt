import engine.view.GameView
import engine.view.MenuView
import kotlin.jvm.JvmStatic

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        MenuView().also {
            it.createWindow()
        }
    }
}