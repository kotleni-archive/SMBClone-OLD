package engine.listener

import engine.ui.etc.UIKey
import java.awt.event.KeyEvent
import java.awt.event.KeyListener
import javax.swing.JFrame

interface GameKeyListener : KeyListener {
    var buffer: ArrayList<UIKey> // буффер нажатых кнопок

    fun initKeyListener(frame: JFrame) {
        frame.addKeyListener(this)
        buffer = arrayListOf()
    }

    fun clearKeyBuffer() {
        buffer.clear()
    }

    override fun keyTyped(p0: KeyEvent) { }

    override fun keyPressed(p0: KeyEvent) { if(buffer.indexOf(UIKey(p0.keyCode, p0.keyChar)) == -1) buffer.add(UIKey(p0.keyCode, p0.keyChar)) }

    override fun keyReleased(p0: KeyEvent) { buffer.remove(UIKey(p0.keyCode, p0.keyChar)) }
}