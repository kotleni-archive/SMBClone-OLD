package engine.toolkit

import engine.LOG
import java.awt.Image
import java.io.File
import javax.imageio.ImageIO

class TextureLoader() {
    private val array = HashMap<String, Image>()

    fun loadAll() {
        LOG("Загрузка текстур")

        // получаем папку с текстурами
        val url = javaClass.classLoader.getResource("textures/")
        val file = File(url?.file!!)

        file.listFiles()?.forEach {
            LOG("Загрузка ${it.nameWithoutExtension}")

            array[it.nameWithoutExtension] = ImageIO.read(File(it.path).inputStream())
        }

        LOG("Текстуры успешно загружены")
    }

    fun getTexture(name: String): Image {
        return array[name]!!
    }
}