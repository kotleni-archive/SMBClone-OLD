package engine.view

import engine.render.Camera
import engine.Constants
import engine.command.CommandInterpreter
import engine.entity.Bembi
import engine.entity.Cat
import engine.entity.Cat2
import engine.entity.Luigi
import engine.toolkit.ViewLoader
import engine.toolkit.WorldLoader
import engine.type.World
import engine.ui.UICommandInput
import engine.ui.UIText
import engine.ui.etc.UIKey
import java.awt.Color
import java.awt.Graphics
import java.awt.event.KeyEvent
import kotlin.concurrent.thread

class GameView() : View() {
    lateinit var world: World
    lateinit var camera: Camera

    override fun createWindow() {
        super.createWindow()

        addUiElement("command", UICommandInput() {
            return@UICommandInput CommandInterpreter.exec(this, it)
        })
    }

    fun loadWorld(name: String) {
        world = World(this, name)
        WorldLoader.load(world, name)

        camera = Camera(world)

        world.entitiesManager.addEntity(
            Luigi(world).apply {
                position.update(100, 100)
            }
        )
        world.entitiesManager.addEntity(
            Bembi(world).apply {
                position.update(120, 100)
            }
        )
        world.entitiesManager.addEntity(
            Cat(world).apply {
                position.update(140, 100)
            }
        )
        world.entitiesManager.addEntity(
            Cat2(world).apply {
                position.update(160, 100)
            }
        )
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

        world.entitiesManager.getPlayer().also { it?.updateInput(buffer.clone() as List<UIKey>) }

        (buffer.clone() as List<UIKey>).forEach {
            when(it.keyCode) {
                KeyEvent.VK_ESCAPE -> {
                    closeWindow()
                    ViewLoader.openMenu()
                }

                KeyEvent.VK_SLASH -> {
                    getUiElement("command").also { it.isVisible = !it.isVisible }
                    getUiElement("command").also { it.isEnabled = !it.isEnabled }

                    clearKeyBuffer()
                }
            }
        }
    }

    override fun onDraw(graphics: Graphics) {
        // заполняем фон
        graphics.color = Color(96, 96 ,199)
        graphics.fillRect(0, 0, width, height)

        // отрисовываем мир
        camera.drawAll(graphics)
        //world.drawAll(graphics, camera)

        // запускаем оригинальную функцию отрисовки
        super.onDraw(graphics)
    }
}