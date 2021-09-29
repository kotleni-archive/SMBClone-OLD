package engine.toolkit

import engine.view.GameView
import engine.view.MenuView

object ViewLoader {
    fun openMenu() {
        MenuView().also {
            it.createWindow()
            it.startBackgroundLoops()
        }
    }

    fun openGameInWorld(worldname: String) {
        GameView().also {
            it.createWindow()
            it.loadWorld(worldname)
            it.startBackgroundLoops()
        }
    }
}