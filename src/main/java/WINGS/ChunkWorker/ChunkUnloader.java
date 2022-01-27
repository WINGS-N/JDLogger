package WINGS.ChunkWorker;

import org.bukkit.Chunk;

public class ChunkUnloader {

    public ChunkUnloader(Chunk chunk) {
        chunk.setForceLoaded(false);
        chunk.unload();
    }
}
