package engine.toolkit

import engine.LOG
import engine.block.Cobblestone
import engine.type.World
import engine.block.Grass
import engine.entity.Player
import org.json.JSONArray
import org.json.JSONObject
import java.awt.Color
import java.io.File
import java.nio.file.FileSystem
import java.nio.file.FileSystems
import javax.imageio.ImageIO
import javax.swing.ImageIcon

object WorldLoader {
    fun compileAll(force: Boolean = false) {
        // LOG("Проверка не скомпилированных карт")
        val url = javaClass.classLoader.getResource("worlds/")
        val list = File(url!!.file!!).listFiles()

        list.forEach {
            val cur = File(it.path) // fix stupid java

            if(cur.extension == "png") {
                compile(cur.nameWithoutExtension, force)
            }
        }
    }
    fun compile(name: String, force: Boolean) {
        // получаем оригинальный файл
        val url = javaClass.classLoader.getResource("worlds/$name.png")
        val file = File(url?.file!!)

        // новый файл
        //val url2 = javaClass.classLoader.getResource("worlds/$name.json")
        val file2 = File("${file.parentFile.path}\\$name.json")

        // не компилируем, если уже есть
        if(file2.exists() && !force) { return }

        LOG("Компиляция карты: $name")

        // получаем изображение
        val img = ImageIO.read(file)

        val json = JSONObject()
        val blocks = JSONArray()
        val entities = JSONArray()

        for(x in 0..img.width-1) {
            for(y in 0..img.height-1) {
                val pix = img.getRGB(x, y)
                val clr = Color(pix)

                if(clr.red == 0 && clr.green == 0 && clr.blue == 0) { // grass
                    blocks.put(JSONObject().also {
                        it.put("type", "grass")
                        it.put("pos", JSONArray().also { it.put(x * 16); it.put(y * 16) })
                        it.put("size", JSONArray().also { it.put(16); it.put(16) })
                    })
                }

                if(clr.red == 0 && clr.green == 255 && clr.blue == 0) { // grass
                    blocks.put(JSONObject().also {
                        it.put("type", "cobblestone")
                        it.put("pos", JSONArray().also { it.put(x * 16); it.put(y * 16) })
                        it.put("size", JSONArray().also { it.put(16); it.put(16) })
                    })
                }
            }
        }

        // сохраняем json
        json.put("start_pos", JSONArray().also { it.put(100); it.put(100) })
        json.put("size", JSONArray().also { it.put(img.width * 16); it.put(img.width * 16) })
        json.put("blocks", blocks)
        json.put("entities", entities)

        // записываем в файл
        file2.writeText(json.toString())

        LOG("Карта скомпилирована, ${file2.readBytes().size} байт")
    }

    fun load(world: World, name: String): Boolean {
        LOG("Загрузка мира: $name")

        // получаем файл мира
        val url = javaClass.classLoader.getResource("worlds/$name.json")

        if(url == null) {
            LOG("Ошибка загрузки мира, не найден файл.")
            return false
        }

        val file = File(url?.file!!)

        // парсим json
        val json = JSONObject(file.readText())

        val size = json.getJSONArray("size")
        val blocks = json.getJSONArray("blocks")
        val entities = json.getJSONArray("entities")

        // получаем размер мира
        world.size.w = size.getInt(0)
        world.size.h = size.getInt(1)

        for(i in 0..blocks.length()-1) {
            val block = blocks.getJSONObject(i)

            when(block.getString("type")) {
                "grass" -> {
                    world.blocksManager.addBlock(Grass(world).also {
                        it.position.x = block.getJSONArray("pos").getInt(0)
                        it.position.y = block.getJSONArray("pos").getInt(1)
                        it.size.w = block.getJSONArray("size").getInt(0)
                        it.size.h = block.getJSONArray("size").getInt(1)
                    })
                }
                "cobblestone" -> {
                    world.blocksManager.addBlock(Cobblestone(world).also {
                        it.position.x = block.getJSONArray("pos").getInt(0)
                        it.position.y = block.getJSONArray("pos").getInt(1)
                        it.size.w = block.getJSONArray("size").getInt(0)
                        it.size.h = block.getJSONArray("size").getInt(1)
                    })
                }
            }
        }

        for(i in 0..entities.length()-1) {
            val entity = entities.getJSONObject(i)
        }

        // добавляем игрока
        world.entitiesManager.addEntity(Player(world).also {
            it.position.x = json.getJSONArray("start_pos").getInt(0)
            it.position.y = json.getJSONArray("start_pos").getInt(1)
        })

        LOG("Мир загружен: ${world.size.w}x${world.size.h}; Блоков: ${world.blocksManager.getBlocksList().count()}, Энтити: ${world.entitiesManager.getEntitiesList().count()}")
        return true
    }
}