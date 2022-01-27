package WINGS.ChunkWorker;

import org.bukkit.Chunk;

public class ChunkLoader {

    public ChunkLoader(Chunk chunk) {
        chunk.load();
    }
}
