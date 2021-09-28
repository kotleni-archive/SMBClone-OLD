package engine.view

import engine.Constants
import engine.Loader
import engine.World
import engine.listener.GameKeyListener
import engine.type.Size
import java.awt.Color
import java.awt.Dimension
import java.awt.Graphics
import java.awt.Toolkit
import java.awt.event.KeyEvent
import javax.swing.JFrame
import kotlin.concurrent.thread

class GameView() : View() {
    private val world = World(
        Size(0, 0)
    )

    override fun createWindow() {
        super.createWindow()

        Loader.loadWorld(world, "a")
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