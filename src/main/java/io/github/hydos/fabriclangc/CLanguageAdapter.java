package io.github.hydos.fabriclangc;

import net.fabricmc.loader.api.LanguageAdapter;
import net.fabricmc.loader.api.LanguageAdapterException;
import net.fabricmc.loader.api.ModContainer;

public class CLanguageAdapter implements LanguageAdapter {
    /**
     * Creates an object of {@code type} from an arbitrary string declaration.
     *
     * @param mod   the mod which the object is from
     * @param value the string declaration of the object
     * @param type  the type that the created object must be an instance of
     * @return the created object
     * @throws LanguageAdapterException if a problem arises during creation, such as an invalid declaration
     */
    @Override
    public <T> T create(ModContainer mod, String value, Class<T> type) throws LanguageAdapterException {
        return null;
    }
}
