package io.github.hydos.fabriclangc.bridge;

import net.fabricmc.api.ModInitializer;

public class CBridge implements ModInitializer {

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

    public static native Object passToNative(long id, Object self, Object[] args);
}
