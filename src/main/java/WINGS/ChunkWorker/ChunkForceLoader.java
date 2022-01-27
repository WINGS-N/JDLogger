package WINGS.ChunkWorker;

import org.bukkit.Chunk;

public class ChunkForceLoader {

    public ChunkForceLoader(Chunk chunk) {
        chunk.setForceLoaded(true);
        chunk.load();
        if (!chunk.isLoaded()) {
            chunk.load();
        }
    }
}
