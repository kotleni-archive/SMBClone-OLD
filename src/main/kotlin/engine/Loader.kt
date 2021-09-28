package engine

import engine.block.Ground
import engine.entity.Player
import org.json.JSONObject
import java.io.File

object Loader {
    fun loadWorld(world: World, name: String) {
        LOG("Loading world: $name")

        // получаем файл мира
        val url = javaClass.classLoader.getResource("worlds/$name.json")
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
                "ground" -> {
                    world.blocksManager.addBlock(Ground(world).also {
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
            it.position.x = 100
            it.position.y = 10
        })

        LOG("World loaded, Size: ${world.size.w}x${world.size.h}; Blocks: ${world.blocksManager.getBlocksList().count()}, Entities: ${world.entitiesManager.getEntitiesList().count()}")
    }
}