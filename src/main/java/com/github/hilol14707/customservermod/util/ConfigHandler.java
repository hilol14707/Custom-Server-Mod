package com.github.hilol14707.customservermod.util;

import java.io.File;

import com.github.hilol14707.customservermod.Main;

import net.minecraftforge.common.config.Configuration;

public class ConfigHandler {
    public static Configuration config;

    public static class Commands {
        public static class Coords {
            public static Boolean ENABLED = true;
            public static String NAME = "coords";
            public static int PERM_LEVEL = 4;
            public static String ALIASES = "dox";
        }
        public static class TpDim {
            public static Boolean ENABLED = true;
            public static String NAME = "tpdim";
            public static int PERM_LEVEL = 2;
            public static String ALIASES = "tpdimension";
        }
    }
    public static void init(File file) {
        config = new Configuration(file);
        String category;

        category = "Discord";
        config.addCustomCategoryComment(category, "Settings related to the relaying messages to Discord via Webhooks.");

        category = "Command Coords";
        config.addCustomCategoryComment(category, "Settings related to the /coords command from this mod.");
        config.setCategoryRequiresMcRestart(category, true);
        Commands.Coords.ENABLED = config.getBoolean("Enable Coord", category, true, "To enable or disable the coords command.");
        Commands.Coords.NAME = config.getString("Coords Name", category, "coords", "Name of /coords command.");
        Commands.Coords.PERM_LEVEL = config.getInt("Coords Perm Level", category, 4, 0, 4, "Minium OP level required to use /coords command.");
        Commands.Coords.ALIASES = config.getString("Coords Alias", category, "dox", "An alias for the /coords command that can be used to run it.");
        
        category = "Command TpDim";
        config.addCustomCategoryComment(category, "Settings related to the /tpdim command from this mod.");
        config.setCategoryRequiresMcRestart(category, true);
        Commands.TpDim.ENABLED = config.getBoolean("Enable Coord", category, true, "To enable or disable the tpdim command.");
        Commands.TpDim.NAME = config.getString("TpDim Name", category, "tpdim", "Name of /tpdim command.");
        Commands.TpDim.PERM_LEVEL = config.getInt("TpDim Perm Level", category, 4, 0, 4, "Minium OP level required to use /tpdim command.");
        Commands.TpDim.ALIASES = config.getString("TpDim Alias", category, "tpdimension", "An alias for the /tpdim command that can be used to run it.");

        config.save();
    }

    public static void registerConfig(File configDir) {
        Main.config = new File(configDir + "/" + Reference.MOD_ID);
        Main.config.mkdirs();
        init(new File(Main.config.getPath(), Reference.MOD_ID + ".cfg"));
    }
}