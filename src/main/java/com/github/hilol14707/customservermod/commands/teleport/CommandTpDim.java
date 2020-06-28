package com.github.hilol14707.customservermod.commands.teleport;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.github.hilol14707.customservermod.Main;
import com.github.hilol14707.customservermod.util.LogHelper;
import com.google.common.collect.Lists;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

public class CommandTpDim extends CommandBase {
    private final List<String> aliases = Lists.newArrayList(Main.getConfig().commands.tpDim.NAME, Main.getConfig().commands.tpDim.ALIASES);

    @Override
    public String getName() {
        return Main.getConfig().commands.tpDim.NAME;
    }

    @Override
    public String getUsage(final ICommandSender sender) {
        return "/"  + Main.getConfig().commands.tpDim.NAME + " <player to tp> <x> <y> <z> <dim number>";
    }

    @Override
    public List<String> getAliases() {
        return aliases;
    }

    @Override
    public int getRequiredPermissionLevel() {
        return Main.getConfig().commands.tpDim.PERM_LEVEL;
    }

    @Override
    public List<String> getTabCompletions(final MinecraftServer server, final ICommandSender sender,
            final String[] args, final BlockPos targetPos) {
        if (args.length == 1) {
            return getListOfStringsMatchingLastWord(args, server.getOnlinePlayerNames());
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public void execute(final MinecraftServer server, final ICommandSender sender, final String[] args) throws CommandException {

        // /tpdim <player> <x> <y> <z> <dim>

        if (5 > args.length)
            throw new WrongUsageException(getUsage(sender), new Object[0]);

        int dimension = 0;
        List<Double> coords = new ArrayList<Double>();
        EntityPlayerMP player;

        try {
            player = getPlayer(server, sender, args[0]);
        } catch (Exception e) {
            throw new WrongUsageException("Please enter in a valid player", new Object[0]);
        }

        /**
         * tries to parse each argument into a double value
         */
        for (int i = 1; i <= 3; i++) {
            try {
                coords.add(Double.parseDouble(args[i]));
            } catch (Exception e) {
                throw new WrongUsageException("Make sure all coordinates are numbers", new Object[0]);
            }
        }

        if (coords.size() != 3)
            throw new WrongUsageException("Please enter a full coordinate <x> <y> <z>", new Object[0]);


        try {
            dimension = Integer.parseInt(args[4]);
        } catch (Exception e) {
            throw new WrongUsageException("Dimension: " + args[4] + " is not a valid dimension", new Object[0]);
        }

        TeleportManager.teleportToDimension(player, coords.get(0), coords.get(1), coords.get(2), dimension);
        LogHelper.logCommand(Main.getConfig().commands.tpDim.NAME, "ran", sender.getName(), player.getName() + " to dim(" + dimension + ") " + coords.toString());
    }

}