package engine.render

import engine.type.Pos
import engine.type.Size
import java.awt.Color
import java.awt.Graphics
import java.awt.Image
import java.awt.image.BufferedImage

class SpriteMap() : Sprite() {
    var images = ArrayList<Image>()

    override fun draw(g: Graphics, pos: Pos, size: Size, frame: Int) {
        if(isFlip)
            g.drawImage(images[frame], pos.x, pos.y, size.w, size.h, null)
        else
            g.drawImage(images[frame], pos.x + size.w, pos.y, -size.w, size.h, null)
    }

    companion object {
        fun fromImage(image: Image): SpriteMap {
            val arr = ArrayList<Image>()
            val bi = BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB_PRE)
            bi.graphics.drawImage(image, 0, 0, null)

            for(i in 0..(image.getWidth(null) / 16)-1) {
                val newimage = BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB_PRE)

                // redraw
                for(x in 0..16-1) {
                    for(y in 0..16-1) {
                        val rgb = bi.getRGB((16 * i) + x, y)
                        val color = Color(rgb)

                        if(!(color.red == 255 && color.green == 255 && color.blue == 255))
                            newimage.setRGB(x, y, rgb)
                    }
                }

                arr.add(newimage)
            }

            return SpriteMap().also { it.images = arr }
        }
    }
}