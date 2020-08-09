package com.github.hilol14707.customservermod.util;

import com.github.hilol14707.customservermod.commands.CommandCoords;
import com.github.hilol14707.customservermod.commands.tpa.CommandTpa;
import com.github.hilol14707.customservermod.commands.tpa.CommandTpaAccept;

import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

// You can use EventBusSubscriber to automatically subscribe events on the
// contained class (this is subscribing to the MOD
// Event bus for receiving Registry Events)
// @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class RegistryHandler {
    @SubscribeEvent
    public static void onCommandRegistry(final RegisterCommandsEvent event) {
        CommandCoords.register(event.getDispatcher());
        CommandTpa.register(event.getDispatcher());
        CommandTpaAccept.register(event.getDispatcher());
    }
}