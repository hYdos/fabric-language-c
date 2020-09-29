package io.github.hydos.fabriclangc;

import net.fabricmc.api.ModInitializer;

public class CBridge implements ModInitializer{

    static {
        System.loadLibrary("FabricLangC");
    }

    static native void parseJniEnv();

    /**
     * Runs the mod initializer.
     */
    @Override
    public void onInitialize() {
        parseJniEnv();
    }
}
