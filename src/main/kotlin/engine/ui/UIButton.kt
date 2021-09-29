package engine.ui

import engine.type.Pos
import engine.type.Size
import java.awt.Color
import java.awt.Font
import java.awt.Graphics
import java.awt.font.FontRenderContext

class UIButton() : UIElement() {
//    var position = Pos(0, 0)
//    var size = Size(0, 0)
//    var font = Font("monospace", Font.BOLD, 11)
//    var color = Color.BLACK
//    var textColor = Color.WHITE
//    var text = ""

    override fun onDraw(g: Graphics) {
        // рисуем текст
//        g.color = textColor
//        g.drawString(text, position.x, position.y)

        // получаем рамки текста
//        val frc = FontRenderContext(null, true, true)
//        val bounds = g.font.getStringBounds(text, frc)

        // рисуем рамку
        //g.color = color
        //g.drawRect(position.x, position.y, bounds.width.toInt(), -bounds.height.toInt())
    }
}