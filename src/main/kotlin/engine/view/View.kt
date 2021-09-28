package engine.view

import engine.Constants
import engine.toolkit.FpsMetter
import engine.Globals
import engine.LOG
import engine.listener.GameKeyListener
import engine.toolkit.TpsMetter
import java.awt.*
import javax.swing.JFrame
import kotlin.concurrent.thread

open class View() : JFrame(), GameKeyListener {
    override var buffer: ArrayList<Int> = arrayListOf()

    private val fpsMetter = FpsMetter()
    private val tpsMetter = TpsMetter()

    open fun createWindow() {
        this.size =  Dimension(1400, 900)
        this.defaultCloseOperation = EXIT_ON_CLOSE
        this.isVisible = true

        this.initKeyListener(this)

        startBackgroundLoops()
        LOG("Window created: ${this::class.simpleName}")
    }

    open fun closeWindow() {
        isVisible = false
        LOG("Window closed: ${this::class.simpleName}")
    }

    open fun startBackgroundLoops() {
        thread { // drawing
            while (isVisible) {
                updateDrawing(graphics)
                Thread.sleep(1000 / Constants.MAX_FPS)
            }
        }

        thread { // input
            while (isVisible) {
                updateInput()
                Thread.sleep(1000 / Constants.MAX_InputTPS)
            }
        }
    }

    open fun updateDrawing(graphics: Graphics) {
        // рисовать отладку
        if(Globals.DEBUG_MODE) {
            graphics.color = Color.WHITE
            graphics.font = Font("monospace", Font.BOLD, 14)
            graphics.drawString("FPS: ${fpsMetter.getFps()} | TPS: ${tpsMetter.getTps()} | Ver: ${Constants.VERSION_CODE}", width - (190), 60)
        }

        // синхронизация кадров
        Toolkit.getDefaultToolkit().sync()

        // обработка FPS
        fpsMetter.doTick()
    }

    open fun updateInput() {
        tpsMetter.doTick()
    }
}