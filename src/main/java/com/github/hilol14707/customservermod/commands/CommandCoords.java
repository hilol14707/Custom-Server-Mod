package com.github.hilol14707.customservermod.commands;

import java.util.Collections;
import java.util.List;

import com.github.hilol14707.customservermod.Main;
import com.github.hilol14707.customservermod.util.LogHelper;
import com.github.hilol14707.customservermod.util.Translation;
import com.google.common.collect.Lists;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;

public class CommandCoords extends CommandBase {

	private final List<String> aliases = Lists.newArrayList(Main.getConfig().commands.coords.NAME, Main.getConfig().commands.coords.ALIASES);

	private final String getDimName(final int dim) {
		if (dim == 0)
			return String.valueOf("the Overworld");
		if (dim == -1)
			return String.valueOf("the Nether");
		if (dim == 1)
			return String.valueOf("the End");
		return String.valueOf("Dim(" + dim + ")");
	}

	@Override
	public String getName() {
		return Main.getConfig().commands.coords.NAME;
	}

	@Override
	public String getUsage(final ICommandSender sender) {
		return "/" + Main.getConfig().commands.coords.NAME + " <player name>";
	}

	@Override
	public List<String> getAliases() {
		return aliases;
	}

	@Override
	public int getRequiredPermissionLevel() {
		return Main.getConfig().commands.coords.PERM_LEVEL;
	}

	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args,
			BlockPos targetPos) {
		if (args.length == 1) {
			return getListOfStringsMatchingLastWord(args, server.getOnlinePlayerNames());
		} else {
			return Collections.emptyList();
		}
	}

	@Override
	public void execute(final MinecraftServer server, final ICommandSender sender, final String[] args) throws CommandException {

		if (args.length < 1 || args.length > 1) {
			throw new WrongUsageException(Translation.translatedString("customservermod.command.coords.argument.require"), new Object[0]);
		}

		try {
			final EntityPlayerMP sp = getPlayer(server, sender, args[0]);
			int x = (int) sp.posX;
			int y = (int) sp.posY;
			int z = (int) sp.posZ;
			sender.sendMessage(new TextComponentString(sp.getName() + " is in " + getDimName(sp.dimension) + " at the coordinates ").appendSibling(
				new TextComponentString("[x: " + x + " y: "+ y + " z: " + z + "]")
					.setStyle(new Style().setColor(TextFormatting.AQUA)
					.setClickEvent(new ClickEvent(net.minecraft.util.text.event.ClickEvent.Action.SUGGEST_COMMAND, "/" + Main.getConfig().commands.tpDim.NAME + " " + sender.getName() + " " + sp.posX + " " + sp.posY + " " + sp.posZ + " " + sp.dimension))
					.setHoverEvent(new HoverEvent(net.minecraft.util.text.event.HoverEvent.Action.SHOW_TEXT, Translation.getTextComponent("customservermod.command.coords.output.coord.hover"))))));
			LogHelper.logCommand("coords", "ran", sender.getName(), sp.getName() + " is in Dim(" + sp.dimension + ") [" + x + ", " + y + ", " + z +"]");
		} catch (final Exception e) {
			throw new CommandException(args[0] + " " + Translation.translatedString("customservermod.command.coords.error"), new Object[0]);
		}
	}

}