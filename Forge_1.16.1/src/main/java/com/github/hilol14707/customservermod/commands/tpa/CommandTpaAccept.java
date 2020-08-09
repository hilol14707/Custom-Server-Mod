package com.github.hilol14707.customservermod.commands.tpa;


import java.util.Date;

import com.mojang.brigadier.CommandDispatcher;

import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class CommandTpaAccept {
    private static final String NAME = "tpaaccept";
    // todo config | technically should be final below but need to add to config file later & less ide errors
    private static int REQUIRED_PERM_LEVEL = 0;

    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        dispatcher.register(Commands.literal(NAME).requires(source -> {
            if (REQUIRED_PERM_LEVEL == 0) {
                return true;
            } else {
                return source.hasPermissionLevel(REQUIRED_PERM_LEVEL);
            }
        }).then(Commands.argument("player", EntityArgument.player()).executes(context -> {
            Entity source = context.getSource().getEntity();
            if (source instanceof ServerPlayerEntity) {
                ServerPlayerEntity target = EntityArgument.getPlayer(context, "player");
                ServerPlayerEntity sender = context.getSource().asPlayer();
                if (TpaData.doesRequestExist(target.getName().getUnformattedComponentText(), sender.getName().getUnformattedComponentText())) {
                    if (TpaData.getRequest(target.getName().getUnformattedComponentText(), sender.getName().getUnformattedComponentText()).expireTime < new Date().getTime()) {
                        sender.sendMessage(new StringTextComponent("The tpa request has expired."), sender.getUniqueID());
                        TpaData.removeRequest(target.getName().getUnformattedComponentText(), sender.getName().getUnformattedComponentText());
                    } else {
                        sender.sendMessage(new StringTextComponent(String.format("Teleporting %s to you...", target.getName().getUnformattedComponentText())), sender.getUniqueID());
                        target.sendMessage(new StringTextComponent(String.format("Teleporting you to %s...", sender.getName().getUnformattedComponentText())), target.getUniqueID());
                        target.teleport(context.getSource().getWorld(), sender.getPosX(), sender.getPosY(), sender.getPosZ(), sender.rotationYaw, sender.rotationPitch);
                        TpaData.removeRequest(target.getName().getUnformattedComponentText(), sender.getName().getUnformattedComponentText());
                    }
                } else {
                    sender.sendMessage(new StringTextComponent(target.getName().getUnformattedComponentText() + " has not sent a tpa request to you."), sender.getUniqueID());
                }
            } else {
                context.getSource().sendErrorMessage(new StringTextComponent("Only players can use this command"));
            }
            return 1;
        })).executes(context -> {
            context.getSource().asPlayer().sendMessage(new TranslationTextComponent("argument.entity.selector.missing"), context.getSource().getEntity().getUniqueID());
            return 1;
        }));
    }
}