package io.github.hydos.fabriclangc.util;

import io.github.hydos.fabriclangc.CLanguageAdapter;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ZipUtils {

    public static final File TEMP_DIR = new File(FabricLoader.getInstance().getGameDir().toAbsolutePath() + "/temp");

    public static void extractAndLoadNative(String name, String modFile) throws IOException {
        TEMP_DIR.mkdir();
        String modPath = FabricLoader.getInstance().getGameDir().toAbsolutePath() + "/mods/" + modFile + ".jar";
        byte[] buffer = new byte[1024];
        ZipInputStream zis = new ZipInputStream(new FileInputStream(modPath));
        ZipEntry entry = zis.getNextEntry();
        while (entry != null) {
            if (entry.getName().contains(CLanguageAdapter.OS_LIB_FORMAT)) {
                File newFile = new File(TEMP_DIR, entry.getName());
                FileOutputStream fos = new FileOutputStream(newFile);
                int len;
                while ((len = zis.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }
                fos.close();
            }
            entry = zis.getNextEntry();
        }
        zis.closeEntry();
        zis.close();
        System.load(new File(TEMP_DIR, name).getAbsolutePath());
    }
}
