package io.github.hydos.fabriclangc;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class NativeModInitializer implements ModInitializer {

    public static final File TEMP_DIR = new File(FabricLoader.getInstance().getGameDir().toAbsolutePath() + "/temp");

    public NativeModInitializer(String libName, String modid) {
        Path extractedNativeLoc = new File(TEMP_DIR, libName).toPath();
        Path b = FabricLoader.getInstance().getModContainer(modid).get().getPath(libName);
        try {
            Files.createDirectories(Paths.get(String.valueOf(TEMP_DIR)));
            Files.copy(b, extractedNativeLoc);
            System.load(extractedNativeLoc.toAbsolutePath().toString());
        } catch (FileAlreadyExistsException e){
            try {
                Files.delete(extractedNativeLoc);
                Files.createDirectories(Paths.get(String.valueOf(TEMP_DIR)));
                Files.copy(b, extractedNativeLoc);
                System.load(extractedNativeLoc.toAbsolutePath().toString());
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        } catch (UnsatisfiedLinkError | IOException e) {
            System.err.println("Native code library failed to load.");
            e.printStackTrace();
            System.exit(69);
        }
    }

    @Override
    public native void onInitialize();

}
