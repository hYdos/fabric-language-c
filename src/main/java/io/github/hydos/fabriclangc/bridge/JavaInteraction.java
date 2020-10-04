package io.github.hydos.fabriclangc.bridge;

import jdk.nashorn.internal.codegen.types.Type;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Constructor;

public class JavaInteraction {

    public static final Logger LOGGER = LogManager.getLogger("Fabric Lang C");

    public static void log(String message) {
        LOGGER.info(message);
    }

    public static void printHello() {
        LOGGER.info("The Native Code Is Working Somehow");
    }

    public static Block createBasicBlock() {
        return new Block(FabricBlockSettings.of(Material.STONE));
    }

    public static Item createBasicItem() {
        return new Item(new FabricItemSettings());
    }

    public static void registerBlock(Block block, String modid, String name) {
        Registry.register(Registry.BLOCK, new Identifier(modid, name), block);
    }

    public static void registerItem(Item item, String modid, String name) {
        Registry.register(Registry.ITEM, new Identifier(modid, name), item);
    }

    public static Object instantiateMcClass(int classId, String descriptor, Object[] constructorParams) {
        String className = getClass(classId);
        return null;
    }

    private static String getClass(int classId) {
        //TODO: yarn
        return "class_" + classId;
    }

    public static Object instantiateJavaClass(String className, String descriptor, Object[] constructorParams) {
        try {
            for (Constructor<?> constructor : Class.forName(className.replace('/', '.')).getDeclaredConstructors()) {
                if (Type.getMethodDescriptor(void.class, constructor.getParameterTypes()).equals(descriptor)) {
                    constructor.setAccessible(true);
                    return constructor.newInstance(constructorParams);
                }
            }
        } catch (Throwable ignored) {
            return null;
        }

        return null;
    }
}
