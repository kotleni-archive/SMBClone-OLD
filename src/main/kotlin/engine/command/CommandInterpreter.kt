package engine.command

import engine.Globals
import engine.LOG
import engine.toolkit.ViewLoader
import engine.toolkit.WorldLoader
import engine.view.GameView

object CommandInterpreter {
    fun exec(view: GameView, line: String) {
        val pars = line.split(" ")

        when(pars[0]) {
            "load", "world" -> {
                view.closeWindow()
                ViewLoader.openGameInWorld(pars[1])
            }

            "recompile", "compile" -> {
                WorldLoader.compileAll(true)
            }

            "teleport", "tp" -> {
                view.world.getPlayer()?.also {
                    it.position.x = pars[1].toInt()
                    it.position.y = pars[2].toInt()
                }
            }

            else -> { LOG("Unknown command: $line"); return }
        }

        LOG("Executed command: $line")
    }
}