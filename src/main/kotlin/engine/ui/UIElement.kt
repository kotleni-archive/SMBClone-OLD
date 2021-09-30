package engine.ui

import engine.type.Size
import engine.ui.etc.UIKey
import java.awt.Graphics

open class UIElement() {
    open var isVisible = true
    open var isEnabled = true

    open fun onDraw(g: Graphics) { }
    open fun onInput(keys: List<UIKey>): Boolean { return false }
    open fun onSizeChanged(size: Size) {
        onSizeChangedHandler?.invoke(size)
    }

    private var onSizeChangedHandler: ((size: Size) -> Unit)? = null
    fun setOnSizeChanged(h: (size: Size) -> Unit) {
        onSizeChangedHandler = h
    }
}