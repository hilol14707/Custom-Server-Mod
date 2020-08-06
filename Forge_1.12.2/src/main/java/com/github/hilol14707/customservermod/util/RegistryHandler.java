package com.github.hilol14707.customservermod.util;

import com.github.hilol14707.customservermod.Main;
import com.github.hilol14707.customservermod.commands.CommandCoords;
import com.github.hilol14707.customservermod.commands.teleport.CommandTpDim;
import com.github.hilol14707.customservermod.logger.Events;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

@EventBusSubscriber
public class RegistryHandler {
    public static void serverRegistries(FMLServerStartingEvent event) {
        // commands
        if (Main.getConfig().commands.coords.ENABLED) {
            event.registerServerCommand(new CommandCoords());
        }
        if (Main.getConfig().commands.tpDim.ENABLED) {
            event.registerServerCommand(new CommandTpDim());
        }

        // events
        MinecraftForge.EVENT_BUS.register(new Events.onWorldSave());
        if (Main.getConfig().events.logEvents) {
            if (Main.getConfig().events.logAdvancements) {
                MinecraftForge.EVENT_BUS.register(new Events.onAdvancement());
            }
            if (Main.getConfig().events.logCommands) {
                MinecraftForge.EVENT_BUS.register(new Events.onCommand());
            }
            if (Main.getConfig().events.logPlayerDeaths) {
                MinecraftForge.EVENT_BUS.register(new Events.onPlayerDeath());
            }
            if (Main.getConfig().events.logPlayerJoin) {
                MinecraftForge.EVENT_BUS.register(new Events.onPlayerJoin());
            }
            if (Main.getConfig().events.logPlayerLeave) {
                MinecraftForge.EVENT_BUS.register(new Events.onPlayerLeave());
            }
            if (Main.getConfig().events.logServerChat) {
                MinecraftForge.EVENT_BUS.register(new Events.onServerChat());
            }
        }
    }
}