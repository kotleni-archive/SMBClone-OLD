package engine.listener

import java.awt.event.KeyEvent
import java.awt.event.KeyListener
import javax.swing.JFrame

interface GameKeyListener : KeyListener {
    var buffer: ArrayList<Int> // буффер нажатых кнопок

    fun initKeyListener(frame: JFrame) {
        frame.addKeyListener(this)
        buffer = arrayListOf()
    }

    override fun keyTyped(p0: KeyEvent) { }

    override fun keyPressed(p0: KeyEvent) { if(buffer.indexOf(p0.keyCode) == -1) buffer.add(p0.keyCode) }

    override fun keyReleased(p0: KeyEvent) { buffer.remove(p0.keyCode) }
}