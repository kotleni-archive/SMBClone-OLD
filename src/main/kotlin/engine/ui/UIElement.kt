package engine.ui

import engine.ui.etc.UIKey
import java.awt.Graphics

open class UIElement() {
    open var isVisible = true
    open var isEnabled = true

    open fun onDraw(g: Graphics) { }
    open fun onInput(keys: List<UIKey>) { }
}