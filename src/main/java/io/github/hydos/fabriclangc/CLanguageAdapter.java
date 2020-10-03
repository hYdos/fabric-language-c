package io.github.hydos.fabriclangc;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.LanguageAdapter;
import net.fabricmc.loader.api.ModContainer;

import java.util.HashMap;
import java.util.function.Function;

public class CLanguageAdapter implements LanguageAdapter {

    public static final String OS_LIB_FORMAT = System.getProperty("os.name").contains("Win") ? ".dll" : ".so"; //uuh... mac can be left out for now.

    @Override
    public <T> T create(ModContainer mod, String entrypointName, Class<T> type) {
        return type.cast(new NativeModInitializer(entrypointName));
    }
}
