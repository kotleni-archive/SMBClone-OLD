package engine.view

import engine.ui.UIButton
import engine.ui.UICommandInput
import engine.ui.UIText
import java.awt.Color
import java.awt.Graphics

class UITestView() : View() {
    override fun createWindow() {
        super.createWindow()

        addUiElement("text", UIText().also {
            it.position.update(100, 100)
            it.text = "UIText"
        })

        addUiElement("command", UICommandInput({}).also {
            it.isEnabled = true
            it.isVisible = true
        })

        addUiElement("button", UIButton().also {
//            it.position.update(400, 400)
//            it.text = "UIButton"
        })
    }

    override fun onDraw(graphics: Graphics) {
        graphics.color = Color.DARK_GRAY
        graphics.fillRect(0, 0, width, height)

        super.onDraw(graphics)
    }
}