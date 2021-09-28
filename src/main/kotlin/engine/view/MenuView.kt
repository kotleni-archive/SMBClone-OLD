package engine.view

import engine.Globals
import engine.LOG
import java.awt.Color
import java.awt.Font
import java.awt.Graphics
import java.awt.event.KeyEvent
import java.awt.font.FontRenderContext




class MenuView() : View() {
    private val margin = 80
    private val padding = 8
    private val items = arrayListOf(
        "Play game",
        // "How to play",
        "Quit",
    )
    private var selectedItem = 0
    private var selectorWidth = 0

    override fun createWindow() {
        super.createWindow()

        // высчитываем максимальный размер плитки выбора
        calcSelectorWidth()
    }

    private fun calcSelectorWidth() {
        items.forEach {
            graphics.font = Font("monospace", Font.BOLD, 18)
            val frc = FontRenderContext(null, true, true)
            val bounds = graphics.font.getStringBounds(it, frc)

            if(bounds.width > selectorWidth)
                selectorWidth = bounds.width.toInt()
        }
    }

    private fun unlockDebugMode() {
        if(!Globals.DEBUG_MODE) {
            items.add("LOL! You are developer? WOW! Welcome to your project, sir! :D")
            calcSelectorWidth()

            Globals.DEBUG_MODE = true

            LOG("Debug mode enabled")
        }
    }

    private fun selectItem(id: Int) {
        when(id) {
            0 -> { // play
                closeWindow()
                GameView().also {
                    it.createWindow()
                }
            }

            // quit
            1 -> { closeWindow() }
        }
    }

    override fun updateDrawing(graphics: Graphics) {
        graphics.color = Color.LIGHT_GRAY
        graphics.fillRect(0, 0, width, height)

        var i = 0
        items.forEach {
            graphics.color = Color.WHITE
            graphics.font = Font("monospace", Font.BOLD, 18)
            graphics.drawString(it, margin, margin + ((padding * 4) * i))

            if(selectedItem == i) {
                val frc = FontRenderContext(null, true, true)
                val bounds = graphics.font.getStringBounds(items[i], frc)

                graphics.drawRect(
                    margin - padding,
                    ((margin + ((padding * 4) * i)) - bounds.height.toInt()) - padding,
                    selectorWidth * 2 + (padding * 2), // bounds.width.toInt() + (padding * 2),
                    bounds.height.toInt() + (padding * 2)
                )

                graphics.fillRect(
                    (margin - padding) - 10,
                    ((margin + ((padding * 4) * i)) - bounds.height.toInt()) - padding,
                    10,
                    bounds.height.toInt() + (padding * 2) + 1
                )
            }

            i += 1
        }

        super.updateDrawing(graphics)
    }

    override fun updateInput() {
        super.updateInput()

        buffer.forEach {
            when(it) {
                KeyEvent.VK_W, KeyEvent.VK_UP -> { selectedItem -= 1; if(selectedItem < 0) { selectedItem = items.count() - 1 } }
                KeyEvent.VK_S, KeyEvent.VK_DOWN -> { selectedItem += 1; if(selectedItem > items.count() - 1) { selectedItem = 0 }}
                KeyEvent.VK_ENTER -> { selectItem(selectedItem) }

                // пасхалка
                KeyEvent.VK_F7 -> { unlockDebugMode() }
            }
        }

        buffer.clear() // очистить буффер
    }
}