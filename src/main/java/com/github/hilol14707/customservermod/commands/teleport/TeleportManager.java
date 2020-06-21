package com.github.hilol14707.customservermod.commands.teleport;

import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

public class TeleportManager extends Teleporter {
    private double x, y, z;

    public TeleportManager(WorldServer world, double x, double y, double z) {
        super(world);
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public void placeInPortal(Entity entityIn, float rotationYaw) {
        entityIn.setPosition(this.x, this.y, this.z);
        int i = MathHelper.floor(entityIn.posX);
        int j = MathHelper.floor(entityIn.posY) - 1;
        int k = MathHelper.floor(entityIn.posZ);

        entityIn.setLocationAndAngles((double) i, (double) j, (double) k, entityIn.rotationYaw, 0.0F);
    }

    public static void teleportToDimension(EntityPlayer player, double x, double y, double z, int dimension) throws WrongUsageException {
        EntityPlayerMP entityPlayerMP = (EntityPlayerMP) player;
        MinecraftServer server = player.getEntityWorld().getMinecraftServer();
        WorldServer worldServer = server.getWorld(dimension);

        if (worldServer == null || worldServer.getMinecraftServer() == null)
            throw new WrongUsageException("Dimension: " + dimension + " doesn't exist!", new Object[0]);

        worldServer.getMinecraftServer().getPlayerList().transferPlayerToDimension(entityPlayerMP, dimension, new TeleportManager(worldServer, x, y, z));
        player.setPositionAndUpdate(x, y, z);
    }
}