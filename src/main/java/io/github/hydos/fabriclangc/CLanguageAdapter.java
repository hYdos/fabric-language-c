package io.github.hydos.fabriclangc;

import net.fabricmc.loader.api.LanguageAdapter;
import net.fabricmc.loader.api.ModContainer;

public class CLanguageAdapter implements LanguageAdapter {

    public static final String OS_LIB_FORMAT = System.getProperty("os.name").contains("Win") ? ".dll" : ".so"; //TODO uuh... mac can be left out for now. OSX(darwin) will be detected as .dll because win = windows

    @Override
    public <T> T create(ModContainer mod, String entrypointName, Class<T> type) {
        return type.cast(new NativeModInitializer(entrypointName + OS_LIB_FORMAT, mod.getMetadata().getId()));
    }
}
