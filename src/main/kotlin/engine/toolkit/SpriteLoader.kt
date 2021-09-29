package engine.toolkit

import engine.LOG
import engine.render.Sprite
import engine.render.SpriteMap
import java.awt.Image
import java.io.File
import javax.imageio.ImageIO

class SpriteLoader() {
    private val array = HashMap<String, Sprite>()

    fun loadAll() {
        LOG("Загрузка спрайтов")

        // получаем папку с текстурами
        val url = javaClass.classLoader.getResource("textures/")
        val file = File(url?.file!!)

        file.listFiles()?.forEach {
            LOG("Загрузка ${it.nameWithoutExtension}")

            val image = ImageIO.read(File(it.path).inputStream())

            if(image.width > 16) // если spritemap
                array[it.nameWithoutExtension] = SpriteMap.fromImage(image)
            else // если sprite
                array[it.nameWithoutExtension] = Sprite(image)
        }

        LOG("Спрайты успешно загружены")
    }

    fun getSprite(name: String): Sprite {
        return array[name]!!
    }

    fun getSpriteMap(name: String): SpriteMap {
        return (array[name] as SpriteMap?)!!
    }
}