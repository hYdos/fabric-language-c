package io.github.hydos.fabriclangc;

import io.github.hydos.fabriclangc.util.ZipUtils;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.LanguageAdapterException;

import java.io.IOException;

public class NativeModInitializer implements ModInitializer {

    public NativeModInitializer(String libName) {
        try {
            ZipUtils.extractAndLoadNative(libName, libName);
        } catch (UnsatisfiedLinkError | IOException e) {
            System.err.println("Native code library failed to load.");
            e.printStackTrace();
            System.exit(69);
        }
    }

    @Override
    public native void onInitialize();

}
