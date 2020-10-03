package io.github.hydos.fabriclangc;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.LanguageAdapterException;

public class NativeModInitializer extends CLanguageAdapter.BaseProxy implements ModInitializer {

    public NativeModInitializer(String libName) {
        super(libName);
        try {
            System.load(FabricLoader.getInstance().getGameDir().toAbsolutePath().toString() + "/mods/" + libName + ".so");
        } catch (UnsatisfiedLinkError e) {
            System.err.println("Native code library failed to load.");
            e.printStackTrace();
            System.exit(69);
        }
    }

    @Override
    public native void onInitialize();

}
