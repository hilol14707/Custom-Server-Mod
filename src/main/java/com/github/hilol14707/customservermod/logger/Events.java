package com.github.hilol14707.customservermod.logger;

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
    @SubscribeEvent
    public void onWorldSave(final WorldEvent.Save event) {
        Main.getModLogger().save();
    }

    @SubscribeEvent
    public void onServerChat(final ServerChatEvent event) {
        Main.getModLogger().writeOnChat("<" + event.getUsername() + "> " + event.getMessage());
    }

    @SubscribeEvent
    public void onServerSay(final CommandEvent event) {
        switch (event.getCommand().getName()) {
            case "say":
                Main.getModLogger().writeOnChat("[server] " + String.join(" ", event.getParameters()));
                break;
            case "give":
                Main.getModLogger().writeOnCmd(event.getSender().getName() + " [ran] /give " + String.join(" ", event.getParameters()));
                break;
            case "gamemode":
                Main.getModLogger().writeOnCmd(event.getSender().getName() + " [ran] /gamemode " + String.join(" ", event.getParameters()));
                break;
            case "tp":
                Main.getModLogger().writeOnCmd(event.getSender().getName() + " [ran] /tp " + String.join(" ", event.getParameters()));
                break;
            default:
                break;
        }
    }

    @SubscribeEvent
    public void onPlayerDeath(final LivingDeathEvent event) {
        try {
            EntityPlayer player = event.getEntity().world.getPlayerEntityByUUID(event.getEntity().getUniqueID());
            Main.getModLogger().writeOnPlayerDeath(event.getSource().getDeathMessage(player).getUnformattedText());
        } catch (Exception e) {
        }
    }

    @SubscribeEvent
    public void onPlayerJoin(final PlayerLoggedInEvent event) {
        Main.getModLogger().writeOnPlayerJoin(event.player.getName() + " joined the game");
    }

    @SubscribeEvent
    public void onPlayerLeave(final PlayerLoggedOutEvent event) {
        Main.getModLogger().writeOnPlayerLeave(event.player.getName() + " left the game");
    }

    @SubscribeEvent
    public void onAdvancement(final AdvancementEvent event) {
        String achieve = event.getAdvancement().getDisplayText().getUnformattedText();
        if (!achieve.contains("recipe")) {
            Main.getModLogger().writeOnAdvancement(event.getEntityPlayer().getName() + " has made the advancement " + achieve);
        }
    }
}