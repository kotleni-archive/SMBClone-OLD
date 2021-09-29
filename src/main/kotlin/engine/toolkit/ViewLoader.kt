package engine.toolkit

import engine.view.GameView
import engine.view.MenuView
import engine.view.UITestView

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

    fun openUiTest() {
        UITestView().also {
            it.createWindow()
            it.startBackgroundLoops()
        }
    }
}