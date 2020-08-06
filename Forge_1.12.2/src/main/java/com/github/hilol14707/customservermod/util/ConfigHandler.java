package com.github.hilol14707.customservermod.util;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

public class ConfigHandler {
    private File configFolder;
    private Configuration config;
    private ConfigValues configValues = new ConfigValues();

    public ConfigHandler(File modConfigDir) {
        configFolder = new File(modConfigDir + "/" + Reference.MOD_ID);
        configFolder.mkdirs();
    }


    public void init() {
        String category;
        config = new Configuration(new File(configFolder.getPath(), Reference.MOD_ID + ".cfg"));

        category = "_IMPORTANT_INFO";
        config.addCustomCategoryComment(category,
            "This is the config file for "
            + Reference.NAME
            + ".\n"
            + "\nCommands must have a Name to use them in game when they are enabled."
            + "\nA command's aliases is an alternative command name that can be used in addition to that command's name to call that command."
            + "\nA command's alias can be blank which means there is no aliases in game for them to be used.\n"
            + "\n*If you have upgraded from a previous version there may be a chance that a config has changed."
            + "\nConfigs that are currently in use have a comment above them."
            + "\nThis means the configs that have no comment above them are no longer in use.");
        config.get(category, "example_used", "this config WOULD be USED", "has a comment so this config is used");
        config.get(category, "example_not_used", "this config would NOT be used");

        category = "Command_Coords";
        config.addCustomCategoryComment(category, "Settings related to the /coords command from this mod.");
        config.setCategoryRequiresWorldRestart(category, true);
        configValues.commands.coords.ENABLED = config.getBoolean("Enabled", category, true, "True to enable, false disable the coords command.");
        configValues.commands.coords.NAME = config.getString("Name", category, "coords", "Name of /coords command. (required)");
        configValues.commands.coords.PERM_LEVEL = config.getInt("Permission_Level", category, 4, 0, 4, "Minium OP level required to use /coords command.");
        configValues.commands.coords.ALIASES = config.getString("Alias", category, "dox", "An alias for the /coords command that can be used to run it. (can be empty)");

        category = "Command_TpDim";
        config.addCustomCategoryComment(category, "Settings related to the /tpdim command from this mod.");
        config.setCategoryRequiresWorldRestart(category, true);
        configValues.commands.tpDim.ENABLED = config.getBoolean("Enabled", category, true, "True to enable, false to disable the tpdim command.");
        configValues.commands.tpDim.NAME = config.getString("Name", category, "tpdim", "Name of /tpdim command. (required)");
        configValues.commands.tpDim.PERM_LEVEL = config.getInt("Permission_Level", category, 2, 0, 4, "Minium OP level required to use /tpdim command.");
        configValues.commands.tpDim.ALIASES = config.getString("Alias", category, "tpdimension", "An alias for the /tpdim command that can be used to run it. (can be empty)");

        category = "Logging_Events";
        config.addCustomCategoryComment(category, "Enable of disable some logging features.\nThe log file is located at logs/cms/");
        config.setCategoryRequiresWorldRestart(category, true);
        configValues.events.logEvents       = config.getBoolean("_Enable_All_Events",   category, true, "Enables (true) or disables (false) all the event logging messages. (this overrides the settings bellow)\n*Note the logging messages from the commands will still be logged");
        configValues.events.logAdvancements = config.getBoolean("Log_Advancements",     category, true, "Enables (true) or disables (false) logging of whenever a player gets an advancement.");
        configValues.events.logServerChat   = config.getBoolean("Log_Chat_Messages",    category, true, "Enables (true) or disables (false) logging of chat messages.");
        configValues.events.logCommands     = config.getBoolean("Log_Commands",         category, true, "Enables (true) or disables (false) logging of specified commands.");
        configValues.events.logCommandsList = config.getStringList("Log_Commands_List", category, new String[] {"give", "gamemode", "tp"}, "List of commands to log when they are used.");
        configValues.events.logPlayerDeaths = config.getBoolean("Log_Player_Deaths",    category, true, "Enables (true) or disables (false) logging of player deaths.");
        configValues.events.logPlayerJoin   = config.getBoolean("Log_Player_Join",      category, true, "Enables (true) or disables (false) logging of whenever a player joins the game.");
        configValues.events.logPlayerLeave  = config.getBoolean("Log_Player_Leave",     category, true, "Enables (true) or disables (false) logging of whenever a player leaves the game .");

        config.save();
        config.load();
    }

    public ConfigValues getConfig() {
        return configValues;
    }


    public class ConfigValues {
        public Commands commands = new Commands();
        public Events events = new Events();

        public class Commands {
            public CommandConfigBase coords = new CommandConfigBase();
            public CommandConfigBase  tpDim = new CommandConfigBase();

            public class CommandConfigBase {
                public Boolean  ENABLED;
                public String   NAME;
                public int      PERM_LEVEL;
                public String   ALIASES;
            }
        }

        public class Events {
            public boolean  logEvents;
            public boolean  logServerChat;
            public boolean  logCommands;
            public String[] logCommandsList;
            public boolean  logPlayerDeaths;
            public boolean  logPlayerJoin;
            public boolean  logPlayerLeave;
            public boolean  logAdvancements;
        }
    }
}