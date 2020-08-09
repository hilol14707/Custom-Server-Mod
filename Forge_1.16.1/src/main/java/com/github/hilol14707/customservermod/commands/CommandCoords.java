package com.github.hilol14707.customservermod.commands;

import com.mojang.brigadier.CommandDispatcher;

import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;

public class CommandCoords {

    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        dispatcher.register(Commands.literal("coords").requires(source -> source.hasPermissionLevel(2))
                .then(Commands.argument("player", EntityArgument.player()).executes(context -> {
                    ServerPlayerEntity player = EntityArgument.getPlayer(context, "player");
                    ServerPlayerEntity sender = context.getSource().asPlayer();
                    double x = player.getPosX();
                    double y = player.getPosY();
                    double z = player.getPosZ();

                    // todo log
                    ResourceLocation dimLocation = player.getServerWorld().func_234923_W_().func_240901_a_(); // from net.minecraft.server.MinecraftServer:1154
                    String dim = dimLocation.getPath(); // dimension name
                    StringTextComponent s_base = new StringTextComponent(player.getName().getUnformattedComponentText() + " is in (" + dim  + ") at the coordinates "); // todo dim
                    Style style = Style.field_240709_b_ // create style instance
                            // setColor()
                            .func_240712_a_(TextFormatting.AQUA)
                            // setClickEvent()
                            .func_240715_a_(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND,
                                    String.format("/execute in %s:%s run tp @s %f %f %f", dimLocation.getNamespace(), dim, x, y, z)))
                            // setHoverEvent()
                            .func_240716_a_(new HoverEvent(HoverEvent.Action.field_230550_a_, // showText on hover
                                new StringTextComponent("customservermod.command.coords.output.coord.hover"))) // todo translation
                    ;
                    sender.sendMessage(s_base.func_230529_a_(
                            // appendSibling()
                            new StringTextComponent("[x: " + (int) x + " y: " + (int) y + " z: " + (int) z + "]")
                                // setStyle()
                                .func_230530_a_(style)
                    ), sender.getUniqueID());
                    return 1;
                })).executes(context -> {
                    context.getSource().asPlayer().sendMessage(
                        // new StringTextComponent("Please input in a player") // todo translation
                        new TranslationTextComponent("argument.entity.selector.missing")
                        , context.getSource().getEntity().getUniqueID());
                    return 1;
                }));
    }

    /*
     * 
     * @Override public String getUsage(final ICommandSender sender) { return "/" +
     * Main.getConfig().commands.coords.NAME + " <player name>"; }
     * 
     * 
     * @Override public int getRequiredPermissionLevel() { return
     * Main.getConfig().commands.coords.PERM_LEVEL; }
     * 
     * @Override public boolean checkPermission(MinecraftServer server,
     * ICommandSender sender) { if (getRequiredPermissionLevel() == 0) { return
     * true; } else { return sender.canUseCommand(getRequiredPermissionLevel(),
     * getName()); } }
     * 
     * 
     * @Override public void execute(final MinecraftServer server, final
     * ICommandSender sender, final String[] args) throws CommandException {
     * 
     * if (args.length < 1 || args.length > 1) { throw new
     * WrongUsageException(Translation.translatedString(
     * "customservermod.command.coords.argument.require"), new Object[0]); }
     * 
     * try { final EntityPlayerMP sp = getPlayer(server, sender, args[0]); int x =
     * (int) sp.posX; int y = (int) sp.posY; int z = (int) sp.posZ;
     * sender.sendMessage(new TextComponentString(sp.getName() + " is in " +
     * getDimName(sp.dimension) + " at the coordinates ").appendSibling( new
     * TextComponentString("[x: " + x + " y: "+ y + " z: " + z + "]") .setStyle(new
     * Style().setColor(TextFormatting.AQUA) .setClickEvent(new
     * ClickEvent(net.minecraft.util.text.event.ClickEvent.Action.SUGGEST_COMMAND,
     * "/" + Main.getConfig().commands.tpDim.NAME + " " + sender.getName() + " " +
     * sp.posX + " " + sp.posY + " " + sp.posZ + " " + sp.dimension))
     * .setHoverEvent(new
     * HoverEvent(net.minecraft.util.text.event.HoverEvent.Action.SHOW_TEXT,
     * Translation.getTextComponent(
     * "customservermod.command.coords.output.coord.hover"))))));
     * LogHelper.logCommand("coords", "ran", sender.getName(), sp.getName() +
     * " is in Dim(" + sp.dimension + ") [" + x + ", " + y + ", " + z +"]"); } catch
     * (final Exception e) { throw new CommandException(args[0] + " " +
     * Translation.translatedString("customservermod.command.coords.error"), new
     * Object[0]); } }
     */

}