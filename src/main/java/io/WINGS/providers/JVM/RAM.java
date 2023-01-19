package io.WINGS.providers.JVM;

public class RAM {
    Runtime r = Runtime.getRuntime();
    final int mb = 1048576;
    public long max() {
        return r.maxMemory() / mb;
    }

    public long used() {
        return (total() - free()) / mb;
    }

    public long total() {
        return (r.totalMemory() / mb);
    }

    public long free() {
        return r.freeMemory() / mb;
    }
}
