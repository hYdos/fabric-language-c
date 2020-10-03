package io.github.hydos.fabriclangc.bridge;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;

public class CBridge implements ModInitializer{

    static {
//        System.loadLibrary("FabricLangC");
    }

    static native void parseJniEnv(boolean isInDev);

    /**
     * Runs the mod initializer.
     */
    @Override
    public void onInitialize() {
//        parseJniEnv(FabricLoader.getInstance().isDevelopmentEnvironment());
    }
}
