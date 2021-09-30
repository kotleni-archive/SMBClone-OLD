package engine.command

import engine.Globals
import engine.LOG
import engine.toolkit.ViewLoader
import engine.toolkit.WorldLoader
import engine.type.Pos
import engine.view.GameView

object CommandInterpreter {
    fun exec(view: GameView, line: String): String {
        var output = ""
        val pars = line.split(" ")
        LOG("Exec command: $line")

        when(pars[0]) {
            "help" -> {
                return "help, load, recompile, teleport, testblock, testdraw, words, reload"
            }
            "load" -> {
                view.closeWindow()
                ViewLoader.openGameInWorld(pars[1])
            }

            "recompile" -> {
                val warns = WorldLoader.compileAll(true)
                return "Все миры перекомпилированы! Предупреждений: $warns"
            }

            "teleport" -> {
                view.world.getPlayer()?.teleport(Pos(pars[1].toInt(), pars[2].toInt()))
                return "Игрок телепортирован на позицию: ${pars[1]}, ${pars[2]}"
            }

            "testblock" -> {
                Globals.DRAW_BLOCKRECT = true
                return "Включен режим отображения границ блоков"
            }

            "testdraw" -> {
                var i = 0
                view.world.blocksManager.getBlocksList().forEach {
                    if(view.camera.isBlockInCamera(it))
                        i += 1
                }

                LOG("Блоков отображается: $i")
                return "Блоков отображается: $i"
            }

            "worlds" -> {
                var str = ""
                WorldLoader.listWorlds().forEach { str += "$str, " }

                return str
            }

            "reload" -> {
                Globals.spriteLoader?.loadAll()
                return "Все текстуры были перезагружены."
            }

            else -> { LOG("Unknown command: $line"); return "Не известная комманда: $line" }
        }
        return output
    }
}