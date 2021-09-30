package engine.command

import engine.Globals
import engine.LOG
import engine.toolkit.ViewLoader
import engine.toolkit.WorldLoader
import engine.type.Pos
import engine.view.GameView

object CommandInterpreter {
    fun exec(view: GameView, line: String) {
        val pars = line.split(" ")

        when(pars[0]) {
            "load" -> {
                view.closeWindow()
                ViewLoader.openGameInWorld(pars[1])
            }

            "recompile" -> {
                WorldLoader.compileAll(true)
            }

            "teleport" -> {
                view.world.getPlayer()?.teleport(Pos(pars[1].toInt(), pars[2].toInt()))
            }

            "testblock" -> {
                Globals.DRAW_BLOCKRECT = true
            }

            "testdraw" -> {
                var i = 0
                view.world.blocksManager.getBlocksList().forEach {
                    if(view.camera.isBlockInCamera(it))
                        i += 1
                }

                LOG("Blocks in camera: $i")
            }

            else -> { LOG("Unknown command: $line"); return }
        }

        LOG("Executed command: $line")
    }
}