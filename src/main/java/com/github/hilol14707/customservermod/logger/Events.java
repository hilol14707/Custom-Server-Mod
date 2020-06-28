package com.github.hilol14707.customservermod.logger;

import java.util.Arrays;

import com.github.hilol14707.customservermod.Main;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.CommandEvent;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.AdvancementEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;

public class Events {
    public static class onWorldSave {
        @SubscribeEvent
        public void onWorldSaveEvent(final WorldEvent.Save event) {
            Main.getModLogger().save();
        }
    }

    public static class onServerChat {
        @SubscribeEvent
        public void onServerChatEvent(final ServerChatEvent event) {
            Main.getModLogger().writeOnChat("<" + event.getUsername() + "> " + event.getMessage());
        }
    }

    public static class onCommand {
        @SubscribeEvent
        public void onCommandEvent(final CommandEvent event) {
            String cmd = event.getCommand().getName();
            if (cmd == "say") {
                Main.getModLogger().writeOnChat("[server] " + String.join(" ", event.getParameters()));
            }

            if (Arrays.asList(Main.getConfig().events.logCommandsList).contains(cmd)) {
                Main.getModLogger().writeOnCmd(event.getSender().getName() + " [ran] /" + cmd + " " + String.join(" ", event.getParameters()));
            }
        }
    }


    public static class onPlayerDeath {
        @SubscribeEvent
        public void onPlayerDeathEvent(final LivingDeathEvent event) {
            if (event.getEntity() instanceof EntityPlayer) {
                EntityPlayer player = event.getEntity().world.getPlayerEntityByUUID(event.getEntity().getUniqueID());
                Main.getModLogger().writeOnPlayerDeath(event.getSource().getDeathMessage(player).getUnformattedText());
            }
        }
    }

    public static class onPlayerJoin {
        @SubscribeEvent
        public void onPlayerJoinEvent(final PlayerLoggedInEvent event) {
            Main.getModLogger().writeOnPlayerJoin(event.player.getName() + " joined the game");
        }
    }


    public static class onPlayerLeave {
        @SubscribeEvent
        public void onPlayerLeaveEvent(final PlayerLoggedOutEvent event) {
            Main.getModLogger().writeOnPlayerLeave(event.player.getName() + " left the game");
        }
    }

    public static class onAdvancement {
        @SubscribeEvent
        public void onAdvancementEvent(final AdvancementEvent event) {
            String achieve = event.getAdvancement().getDisplayText().getUnformattedText();
            if (!achieve.contains("recipe")) {
                Main.getModLogger().writeOnAdvancement(event.getEntityPlayer().getName() + " has made the advancement " + achieve);
            }
        }
    }
}