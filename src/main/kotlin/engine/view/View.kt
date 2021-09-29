package engine.view

import engine.Constants
import engine.toolkit.FpsMetter
import engine.Globals
import engine.LOG
import engine.listener.GameKeyListener
import engine.toolkit.TextureLoader
import engine.toolkit.TpsMetter
import engine.ui.UIElement
import engine.ui.etc.UIKey
import java.awt.*
import javax.swing.JFrame
import kotlin.concurrent.thread

open class View() : JFrame(), GameKeyListener {
    override var buffer: ArrayList<UIKey> = arrayListOf()

    private val fpsMetter = FpsMetter()
    private val tpsMetter = TpsMetter()

    private val uiElements = HashMap<String, UIElement>()

    fun addUiElement(name: String, el: UIElement) {
        if(uiElements.containsKey(name))
            uiElements[name] = el
        else
            uiElements.put(name, el)
    }

    fun getUiElement(name: String): UIElement {
        return uiElements.get(name)!!
    }

    fun removeUiElement(name: String) {
        uiElements.remove(name)
    }

    open fun createWindow() {
        this.size =  Dimension(1000, 600)
        Globals.WINDOW_WIDTH = this.size.width
        Globals.WINDOW_HEIGHT = this.size.height
        this.title = Constants.WINDOW_NAME
        this.defaultCloseOperation = EXIT_ON_CLOSE
        this.isVisible = true

        this.initKeyListener(this)

        Globals.textureLoader = TextureLoader()
        Globals.textureLoader!!.loadAll()

        // startBackgroundLoops()
        LOG("Окно откыто: ${this::class.simpleName}")
    }

    open fun closeWindow() {
        isVisible = false
        LOG("Окно закрыто: ${this::class.simpleName}")
    }

    open fun startBackgroundLoops() {
        thread { // drawing
            while (isVisible) {
                onDraw(graphics)
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

    open fun onDraw(graphics: Graphics) {
        graphics.translate(0, 0)

        // рисуем UI
        (uiElements.clone() as HashMap<String, UIElement>).forEach {
            if(it.value.isVisible)
                it.value.onDraw(graphics)
        }

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
        (uiElements.clone() as HashMap<String, UIElement>).forEach {
            if(it.value.isEnabled) {
                it.value.onInput(buffer.clone() as List<UIKey>)
                clearKeyBuffer()
            }
        }

        tpsMetter.doTick()
    }
}