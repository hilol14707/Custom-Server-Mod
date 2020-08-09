package com.github.hilol14707.customservermod.logger;

import com.github.hilol14707.customservermod.Main;
import com.github.hilol14707.customservermod.util.ConfigHandler;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.CommandEvent;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.AdvancementEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedOutEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.server.FMLServerStoppingEvent;

public class Events {
    @SubscribeEvent
    public static void onServerStop(final FMLServerStoppingEvent event) {
        String[] playersOnline = event.getServer().getOnlinePlayerNames();
        Boolean PLAYERS_PRESENT = playersOnline.length > 0;
        String endMsg = PLAYERS_PRESENT ? "Players online when the server stopped: [" : "No players were online when the server stopped.";
        if (PLAYERS_PRESENT) {
            for (int i = 0; i < playersOnline.length; i++) {
                endMsg += i > playersOnline.length-1 ? playersOnline[i] + ", " : playersOnline[i] + "]";
            }
        }
        Main.getModLogger().write(endMsg);
        Main.getModLogger().writerStop();
    }

    @SubscribeEvent
    public static void onWorldSaveEvent(final WorldEvent.Save event) {
        Main.getModLogger().save();
    }

    @SubscribeEvent
    public static void onServerChatEvent(final ServerChatEvent event) {
        Main.getModLogger().writeOnChat("<" + event.getUsername() + "> " + event.getMessage());
    }

    @SubscribeEvent
    public static void onCommandEvent(final CommandEvent event) {
        String fullCommand = event.getParseResults().getReader().getRead();
        String sender = event.getParseResults().getContext().getSource().getName();
        String[] cmd = fullCommand.replace("/", "").split(" ", 2);

        if (cmd[0].equals("say") && "Server".equals(sender)) {
            Main.getModLogger().writeOnChat("[server] " + cmd[1]);
        } else if (ConfigHandler.LOG_COMMANDS_LIST.get().contains(cmd[0])) {
            Main.getModLogger().writeOnCmd(sender + " [ran] " + fullCommand);
        }
    }

    @SubscribeEvent
    public static void onPlayerDeathEvent(final LivingDeathEvent event) {
        if (event.getEntity() instanceof PlayerEntity) {
            Main.getModLogger().writeOnPlayerDeath(event.getSource().getDeathMessage(event.getEntityLiving()).getString());
        }
    }

    @SubscribeEvent
    public static void onPlayerJoinEvent(final PlayerLoggedInEvent event) {
        Main.getModLogger().writeOnPlayerJoin(event.getPlayer().getName().getUnformattedComponentText() + " joined the game");
    }

    @SubscribeEvent
    public static void onPlayerLeaveEvent(final PlayerLoggedOutEvent event) {
        Main.getModLogger().writeOnPlayerLeave(event.getPlayer().getName().getUnformattedComponentText() + " left the game");
    }

    @SubscribeEvent
    public static void onAdvancementEvent(final AdvancementEvent event) {
        String achieve = event.getAdvancement().getDisplayText().getString();
        if (!achieve.contains("recipe")) {
            Main.getModLogger().writeOnAdvancement(event.getPlayer().getName().getUnformattedComponentText() + " has made the advancement " + achieve);
        }
    }
}