package engine.ui

import engine.type.Pos
import java.awt.Color
import java.awt.Font
import java.awt.Graphics

class UIText() : UIElement() {
    var position = Pos(0, 0)
    var color = Color.WHITE
    var font = Font("monospace", Font.BOLD, 12)
    var text = ""

    override fun onDraw(g: Graphics) {
        g.color = color
        g.font = font
        g.drawString(text, position.x, position.y)
    }
}