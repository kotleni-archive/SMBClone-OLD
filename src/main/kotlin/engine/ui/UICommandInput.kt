package engine.ui

import engine.Globals
import engine.ui.etc.UIKey
import java.awt.Color
import java.awt.Font
import java.awt.Graphics
import java.awt.event.KeyEvent
import java.awt.font.FontRenderContext

class UICommandInput(var onCommand: ((line: String) -> Unit)) : UIElement() {
    override var isVisible: Boolean = false
    override var isEnabled: Boolean = false

    var inputLine = ""

    override fun onDraw(g: Graphics) {
        val margin = 16
        g.font = Font("monospace", Font.BOLD, 14)
        val frc = FontRenderContext(null, true, true)
        val bounds = g.font.getStringBounds(inputLine, frc)

        g.color = Color(0, 0, 0, 180)
        g.fillRect(
            0, (Globals.WINDOW_HEIGHT - margin) - bounds.height.toInt(),
            Globals.WINDOW_WIDTH, bounds.height.toInt() + margin
        )

        g.color = Color.WHITE
        g.drawString(inputLine, margin, Globals.WINDOW_HEIGHT - margin)
    }

    override fun onInput(keys: List<UIKey>): Boolean {
        keys.forEach {
            when(it.keyCode) {
                KeyEvent.VK_BACK_SPACE -> {
                    // удаляем последний символ
                    if(inputLine.isNotEmpty())
                        inputLine= inputLine.removeSuffix("${inputLine[inputLine.length-1]}")
                }

                KeyEvent.VK_ENTER -> {
                    if(inputLine.isNotEmpty()) {
                        onCommand.invoke(inputLine)
                        inputLine = ""
                    }

                    isEnabled = false
                    isVisible = false
                }

                else -> { if(it.keyChar.toInt() != 65535) { inputLine += it.keyChar} }
            }
        }

        return true
    }
}