package com.github.hilol14707.customservermod.commands.tpa;

import java.util.Date;

import com.mojang.brigadier.CommandDispatcher;

import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;

public class CommandTpa {
    private static final String NAME = "tpa";
    private static final long WAIT_TIME = 1000 * 120;
    // todo config | technically should be final below but need to add to config file later & less ide errors
    private static int REQUIRED_PERM_LEVEL = 0; 

    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        dispatcher.register(Commands.literal(NAME).requires(source -> {
            if (REQUIRED_PERM_LEVEL == 0) {
                return true;
            } else {
                return source.hasPermissionLevel(1);
            }
        }).then(Commands.argument("player", EntityArgument.player()).executes(context -> {
            Entity source = context.getSource().getEntity();
            if (source instanceof ServerPlayerEntity) {
                ServerPlayerEntity player = EntityArgument.getPlayer(context, "player");
                if (!TpaData.doesRequestExist(source.getName().getUnformattedComponentText(), player.getName().getUnformattedComponentText())) {
                    TpaData.addRequest(source.getName().getUnformattedComponentText(), player.getName().getUnformattedComponentText(), new Date().getTime() + WAIT_TIME);

                    player.sendMessage(new StringTextComponent(source.getName().getUnformattedComponentText() + " has requested to teleport to you.\n")
                        .func_230530_a_(Style.field_240709_b_.func_240712_a_(TextFormatting.GOLD)
                            .func_240715_a_(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, String.format("/msg %s", source.getName().getUnformattedComponentText())))
                            .func_240716_a_(new HoverEvent(HoverEvent.Action.field_230550_a_, new StringTextComponent(source.getUniqueID().toString()))))

                        .func_230529_a_(new StringTextComponent("[click to accept or run /tpaaccept " + source.getName().getUnformattedComponentText() + "]")
                            .func_230530_a_(Style.field_240709_b_.func_240712_a_(TextFormatting.AQUA)
                                .func_240715_a_(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tpaaccept " + source.getName().getUnformattedComponentText()))
                                .func_240716_a_(new HoverEvent(HoverEvent.Action.field_230550_a_, new StringTextComponent("click to accept the tpa request"))))
                    ), player.getUniqueID());

                    source.sendMessage(new StringTextComponent(
                        String.format("A tpa request has been sent to %s.", player.getName().getUnformattedComponentText())
                    ), source.getUniqueID());
                } else {
                    source.sendMessage(new StringTextComponent(
                        String.format("A tpa request to %s already exists. Please wait until that one expires before asking again.", player.getName().getUnformattedComponentText())
                    ), source.getUniqueID());
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