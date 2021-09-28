package engine.manager

import engine.World
import engine.block.Block

class BlocksManager(var world: World) {
    private val blocks = ArrayList<Block>()

    fun addBlock(block: Block) {
        blocks.add(block)
    }

    fun removeBlock(block: Block) {
        blocks.remove(block)
    }

    fun getBlock(id: Int): Block {
        return blocks[id]
    }

    fun getBlocksList(): List<Block> {
        return blocks
    }
}