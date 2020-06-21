package com.github.hilol14707.customservermod;

import java.io.File;

import com.github.hilol14707.customservermod.logger.ModLogger;
import com.github.hilol14707.customservermod.proxy.CommonProxy;
import com.github.hilol14707.customservermod.util.ConfigHandler;
import com.github.hilol14707.customservermod.util.LogHelper;
import com.github.hilol14707.customservermod.util.Reference;
import com.github.hilol14707.customservermod.util.RegistryHandler;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;

@Mod(modid = Reference.MOD_ID, name = Reference.NAME, version = Reference.VERSION, acceptableRemoteVersions = "*")// , serverSideOnly = false, )
public class Main {
    private static File modConfigDir;
    private static ModLogger modLogger;
    public static File config;

    @Instance(Reference.MOD_ID)
    public static Main instance;

    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.COMMON_PROXY_CLASS)
    public static CommonProxy proxy;

    @EventHandler
    public static void PreInit(final FMLPreInitializationEvent event) {
        modConfigDir = event.getModConfigurationDirectory();
        ConfigHandler.registerConfig(modConfigDir);
    }

    @EventHandler
    public static void serverInit(final FMLServerStartingEvent event) {
        ConfigHandler.registerConfig(modConfigDir);
        RegistryHandler.serverRegistries(event);
        Main.modLogger = new ModLogger();
        LogHelper.logger.info("initialized");
    }

    @EventHandler
    public static void serverStop(final FMLServerStoppingEvent event) {
        Main.modLogger.writerStop();
    }

    public static ModLogger getModLogger() {
        return modLogger;
    }
}