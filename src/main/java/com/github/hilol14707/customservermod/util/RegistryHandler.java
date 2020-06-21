package com.github.hilol14707.customservermod.util;

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
        event.registerServerCommand(new CommandCoords());
        event.registerServerCommand(new CommandTpDim());

        // events
        MinecraftForge.EVENT_BUS.register(new Events());
    }
}