package com.github.hilol14707.customservermod;

import com.github.hilol14707.customservermod.logger.Events;
import com.github.hilol14707.customservermod.logger.ModLogger;
import com.github.hilol14707.customservermod.util.ConfigHandler;
import com.github.hilol14707.customservermod.util.Reference;
import com.github.hilol14707.customservermod.util.RegistryHandler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig.Type;
import net.minecraftforge.fml.event.lifecycle.FMLDedicatedServerSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Reference.MOD_ID)
public class Main {
    public static final Logger LOGGER = LogManager.getLogger(Reference.MOD_ID);
    private static ModLogger modLogger;

    public Main() {
        MinecraftForge.EVENT_BUS.register(ConfigHandler.class);
        
        ModLoadingContext.get().registerConfig(
            Type.SERVER, ConfigHandler.SERVER_SPEC,
            FMLPaths.CONFIGDIR.get().resolve(Reference.MOD_ID + "-server.toml").toString());


        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::serverInit);
        MinecraftForge.EVENT_BUS.register(RegistryHandler.class);
        MinecraftForge.EVENT_BUS.register(Events.class);
    }

    private void serverInit(final FMLDedicatedServerSetupEvent event) {
        Main.modLogger = new ModLogger();
        Main.LOGGER.info(Reference.NAME + " has initialized");
    }

    public static ModLogger getModLogger() {
        return modLogger;
    }
}
