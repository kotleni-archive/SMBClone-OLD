package engine.toolkit

import engine.LOG
import engine.block.*
import engine.type.World
import engine.entity.Player
import org.json.JSONArray
import org.json.JSONObject
import java.awt.Color
import java.io.File
import javax.imageio.ImageIO

object WorldLoader {
    fun compileAll(force: Boolean = false): Int /* количество варнингов */ {
        // LOG("Проверка не скомпилированных карт")
        val url = javaClass.classLoader.getResource("worlds/")
        val list = File(url!!.file!!).listFiles()

        var warns = 0

        list.forEach {
            val cur = File(it.path) // fix stupid java

            if(cur.extension == "png") {
                val _w = compile(cur.nameWithoutExtension, force)
                warns += _w
            }
        }

        return warns
    }

    fun compile(name: String, force: Boolean): Int /* количество варнингов */ {
        var warns = 0
        // получаем оригинальный файл
        val url = javaClass.classLoader.getResource("worlds/$name.png")
        val file = File(url?.file!!)

        // новый файл
        //val url2 = javaClass.classLoader.getResource("worlds/$name.json")
        val file2 = File("${file.parentFile.path}\\$name.json")

        // не компилируем, если уже есть
        if(file2.exists() && !force) { return 0 }

        LOG("Компиляция карты: $name")

        // получаем изображение
        val img = ImageIO.read(file)

        val json = JSONObject()
        val blocks = JSONArray()
        val entities = JSONArray()

        var lastObj: JSONObject? = null
        for(x in 0 until img.width) {
            if(lastObj != null) {
                blocks.put(lastObj)
                lastObj = null
            }
            for(y in 0 until img.height) {
                val pix = img.getRGB(x, y)
                val clr = Color(pix)

                val newobj = JSONObject().apply {
                    put("type", when(clr) {
                        Color(0, 0, 0) -> "brick" // керпич
                        Color(255, 0, 0) -> "stone" // камень
                        Color(0, 255, 0) -> "stone2" // камень2
                        Color(0, 0, 255) -> "trap" // мостик
                        Color(0, 255, 255) -> "bonus" // бонусный блок
                        Color(255, 255, 0) -> "bonus2" // бонусный блок2 (не активный)
                        Color(255, 0, 255) -> "mushroom" // гриб (ножка)
                        Color(255, 255, 255) -> "" // воздух
                        else -> { LOG("ОШИБКА, НЕ ВЕРНЫЙ ЦВЕТ БЛОКА: ${clr.red}, ${clr.green}, ${clr.blue}"); warns += 1; "" }
                    })
                    put("pos", JSONArray().apply { put(x * 16); put(y * 16) })
                    put("size", JSONArray().apply { put(16); put(16) })
                }

                // если блоки одного типа
                if(lastObj != null && /* если не воздух */ clr != Color(255, 255, 255) && lastObj.getString("type") == newobj.getString("type")) {
                    lastObj.put("size", JSONArray().apply {
                        put(lastObj!!.getJSONArray("size").getInt(0))
                        put(lastObj!!.getJSONArray("size").getInt(1) + 16)
                    })
                } else if(/* если не воздух */true) {
                    blocks.put(newobj)
                    lastObj = newobj
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

        return warns
    }

    fun listWorlds(): List<String> {
        val tmp = ArrayList<String>()

        val url = javaClass.classLoader.getResource("worlds/")
        val list = File(url!!.file!!).listFiles()

        list?.forEach {
            val cur = File(it.path) // fix stupid java

            if(cur.extension == "json") {
                tmp.add(cur.nameWithoutExtension)
            }
        }

        return tmp
    }

    fun load(world: World, name: String): Boolean {
        LOG("Загрузка мира: $name")

        // получаем файл мира
        val url = javaClass.classLoader.getResource("worlds/$name.json")

        if(url == null) {
            LOG("Ошибка загрузки мира, не найден файл.")
            return false
        }

        val file = File(url.file!!)

        // парсим json
        val json = JSONObject(file.readText())

        val _size = json.getJSONArray("size")
        val blocks = json.getJSONArray("blocks")
        val entities = json.getJSONArray("entities")

        // получаем размер мира
        world.size.w = _size.getInt(0)
        world.size.h = _size.getInt(1)

        for(i in 0 until blocks.length()) {
            val block = blocks.getJSONObject(i)

            when(block.getString("type")) {
                "brick" -> {
                    world.blocksManager.addBlock(Brick(world).apply {
                        position.x = block.getJSONArray("pos").getInt(0)
                        position.y = block.getJSONArray("pos").getInt(1)
                        size.w = block.getJSONArray("size").getInt(0)
                        size.h = block.getJSONArray("size").getInt(1)
                    })
                }
                "stone" -> {
                    world.blocksManager.addBlock(Stone(world).apply {
                        position.x = block.getJSONArray("pos").getInt(0)
                        position.y = block.getJSONArray("pos").getInt(1)
                        size.w = block.getJSONArray("size").getInt(0)
                        size.h = block.getJSONArray("size").getInt(1)
                    })
                }
                "stone2" -> {
                    world.blocksManager.addBlock(Stone2(world).apply {
                        position.x = block.getJSONArray("pos").getInt(0)
                        position.y = block.getJSONArray("pos").getInt(1)
                        size.w = block.getJSONArray("size").getInt(0)
                        size.h = block.getJSONArray("size").getInt(1)
                    })
                }
                "trap" -> {
                    world.blocksManager.addBlock(Trap(world).apply {
                        position.x = block.getJSONArray("pos").getInt(0)
                        position.y = block.getJSONArray("pos").getInt(1)
                        size.w = block.getJSONArray("size").getInt(0)
                        size.h = block.getJSONArray("size").getInt(1)
                    })
                }
                "bonus" -> {
                    world.blocksManager.addBlock(Bonus(world).apply {
                        position.x = block.getJSONArray("pos").getInt(0)
                        position.y = block.getJSONArray("pos").getInt(1)
                        size.w = block.getJSONArray("size").getInt(0)
                        size.h = block.getJSONArray("size").getInt(1)
                    })
                }
                "bonus2" -> {
                    world.blocksManager.addBlock(Bonus2(world).apply {
                        position.x = block.getJSONArray("pos").getInt(0)
                        position.y = block.getJSONArray("pos").getInt(1)
                        size.w = block.getJSONArray("size").getInt(0)
                        size.h = block.getJSONArray("size").getInt(1)
                    })
                }
                "mushroom" -> {
                    world.blocksManager.addBlock(Mushroom(world).apply {
                        position.x = block.getJSONArray("pos").getInt(0)
                        position.y = block.getJSONArray("pos").getInt(1)
                        size.w = block.getJSONArray("size").getInt(0)
                        size.h = block.getJSONArray("size").getInt(1)
                    })
                }
            }
        }

        for(i in 0..entities.length()-1) {
            val entity = entities.getJSONObject(i)
        }

        // добавляем игрока
        world.entitiesManager.addEntity(Player(world).apply {
            position.update(
                json.getJSONArray("start_pos").getInt(0),
                json.getJSONArray("start_pos").getInt(1)
            )
        })

        LOG("Мир загружен: ${world.size.w}x${world.size.h}; Блоков: ${world.blocksManager.getBlocksList().count()}, Энтити: ${world.entitiesManager.getEntitiesList().count()}")
        return true
    }
}