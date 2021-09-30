package engine.view

import engine.Globals
import engine.LOG
import engine.menu.Item
import engine.toolkit.ViewLoader
import engine.toolkit.WorldLoader
import engine.ui.UIText
import engine.ui.etc.UIKey
import java.awt.Color
import java.awt.Desktop
import java.awt.Font
import java.awt.Graphics
import java.awt.event.KeyEvent
import java.awt.font.FontRenderContext
import java.net.URI


class MenuView() : View() {
    private val margin = 80
    private val padding = 8

    private val items = arrayListOf(
        Item("ОДИНОЧНЫЙ РЕЖИМ", true) {
            closeWindow()
            ViewLoader.openGameInWorld("world1")
        },
        Item("ТЕСТОВАЯ КОМНАТА", true) {
            closeWindow()
            ViewLoader.openGameInWorld("test_room")
        },
        Item("СКАЧАТЬ МИРЫ", true) {
            Desktop.getDesktop().browse(URI("http://api.hjee.xyz/worlds/"))
        },
        Item("ВЫХОД", true) {
            closeWindow()
        }
    )
    private var selectedItem = 0
    private var selectorWidth = 0

    override fun createWindow() {
        super.createWindow()
        addUiElement("label1", UIText("https://github.com/kotlenis").apply {
            setOnSizeChanged {
                position.x = it.w - 170
                position.y = it.h - 20
            }
        })

        // высчитываем максимальный размер плитки выбора
        calcSelectorWidth()

        // компилируем все карты
        WorldLoader.compileAll()
    }

    private fun calcSelectorWidth() {
        items.forEach {
            graphics.font = Font("monospace", Font.BOLD, 18)
            val frc = FontRenderContext(null, true, true)
            val bounds = graphics.font.getStringBounds(it.title, frc)

            if(bounds.width > selectorWidth)
                selectorWidth = bounds.width.toInt()
        }
    }

    private fun unlockDebugMode() {
        if(!Globals.DEBUG_MODE) {
            items.add(Item("You are developer? Welcome to your project, sir!", false) { })
            calcSelectorWidth()

            Globals.DEBUG_MODE = true

            LOG("Включен режим отладки")
        }
    }

    private fun selectItem(id: Int) {
        items[id].onSelected.invoke()
    }

    override fun onDraw(graphics: Graphics) {
        graphics.color = Color.DARK_GRAY
        graphics.fillRect(0, 0, width, height)

        var i = 0
        items.forEach {
            graphics.color = if(it.isEnabled) Color.WHITE else Color.LIGHT_GRAY
            graphics.font = Font("monospace", Font.BOLD, 18)
            graphics.drawString(it.title, margin, margin + ((padding * 4) * i))

            val frc = FontRenderContext(null, true, true)
            val bounds = graphics.font.getStringBounds(items[i].title, frc)

            if(selectedItem == i) {
                graphics.drawRect(
                    margin - padding,
                    ((margin + ((padding * 4) * i)) - bounds.height.toInt()) - padding,
                    (selectorWidth * 2) + (padding * 2), // bounds.width.toInt() + (padding * 2),
                    bounds.height.toInt() + (padding * 3)
                )

                graphics.fillRect(
                    (margin - padding) - 10,
                    ((margin + ((padding * 4) * i)) - bounds.height.toInt()) - padding,
                    10, // bounds.width.toInt() + (padding * 2),
                    bounds.height.toInt() + (padding * 3) + 1
                )
            }

            i += 1
        }

        super.onDraw(graphics)
    }

    override fun updateInput() {
        (buffer.clone() as ArrayList<UIKey>).forEach {
            when(it.keyCode) {
                KeyEvent.VK_W, KeyEvent.VK_UP -> { selectedItem -= 1; if(selectedItem < 0) { selectedItem = items.count() - 1 } }
                KeyEvent.VK_S, KeyEvent.VK_DOWN -> { selectedItem += 1; if(selectedItem > items.count() - 1) { selectedItem = 0 }}
                KeyEvent.VK_ENTER -> { selectItem(selectedItem) }

                // пасхалка
                KeyEvent.VK_F7 -> { unlockDebugMode() }
            }
        }

        super.updateInput()
        clearKeyBuffer() // очистить буффер
    }
}