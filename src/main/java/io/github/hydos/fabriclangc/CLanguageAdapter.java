package io.github.hydos.fabriclangc;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.LanguageAdapter;
import net.fabricmc.loader.api.ModContainer;

import java.util.HashMap;
import java.util.function.Function;

public class CLanguageAdapter implements LanguageAdapter {

    static {
        proxies = new HashMap<>();
        putProxy(ModInitializer.class, NativeModInitializer::new);
    }

    private static final HashMap<Class, Function<String, BaseProxy>> proxies;

    public static void putProxy(Class classs, Function<String, BaseProxy> proxyCreator) {
        proxies.put(classs, proxyCreator);
    }

    @Override
    public <T> T create(ModContainer mod, String value, Class<T> type) {
        Function<String, BaseProxy> a = proxies.get(type);
        if (a == null) return null;
        return type.cast(a.apply(value));
    }

    public static class BaseProxy {
        protected String libName;

        public BaseProxy(String libName) {
            this.libName = libName;
        }
    }
}
