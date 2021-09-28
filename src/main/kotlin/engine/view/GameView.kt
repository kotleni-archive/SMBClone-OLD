package engine.view

import engine.Constants
import engine.Globals
import engine.toolkit.Loader
import engine.type.World
import engine.type.Size
import java.awt.Color
import java.awt.Graphics
import java.awt.event.KeyEvent
import kotlin.concurrent.thread

class GameView() : View() {
    private val world = World(
        Size(0, 0)
    )

    override fun createWindow() {
        super.createWindow()
    }

    fun loadWorld(name: String) {
        Loader.loadWorld(world, name)
    }

    override fun startBackgroundLoops() {
        super.startBackgroundLoops() // запустить стандартные потоки

        thread { // поток обработки физики
            while(true) {
                world.physicsManager.updateForEntities()
                Thread.sleep(1000 / Constants.MAX_PhysTPS)
            }
        }
    }

    override fun updateInput() {
        super.updateInput()
        world.entitiesManager.getPlayer().also { it?.updateInput(buffer) }

        buffer.forEach {
            when(it) {
                KeyEvent.VK_ESCAPE -> {
                    closeWindow()
                    MenuView().also {
                        it.createWindow()
                    }
                }

                KeyEvent.VK_F2 -> {
                    if(Globals.DEBUG_MODE)
                        world.entitiesManager.getPlayer()?.also {
                            it.position.x = 0
                            it.position.y = 0
                        }
                }
            }
        }
    }

    override fun updateDrawing(graphics: Graphics) {
        // заполняем фон
        graphics.color = Color(96, 96 ,199)
        graphics.fillRect(0, 0, width, height)

        // отрисовываем мир
        world.drawAll(graphics)

        // запускаем оригинальную функцию отрисовку
        super.updateDrawing(graphics)
    }
}