package com.github.hilol14707.customservermod.util;

import java.util.ArrayList;
import java.util.List;

import net.minecraftforge.common.ForgeConfigSpec;

public class ConfigHandler {
    private static final ForgeConfigSpec.Builder SERVER_BUILDER = new ForgeConfigSpec.Builder();;
    public static final ForgeConfigSpec SERVER_SPEC;

    public static ForgeConfigSpec.ConfigValue<List<String>> LOG_COMMANDS_LIST;

    public static void init() {
        SERVER_BUILDER.comment(Reference.NAME + " Configurations").push("Logging");
        LOG_COMMANDS_LIST = SERVER_BUILDER
                .comment("Array of commands to log. (do not include the forward slash / only the command name)")
                .worldRestart()
                .define("list", list());
        SERVER_BUILDER.pop();
    }

    private static List<String> list() {
        ArrayList<String> list = new ArrayList<String>();
        list.add("tp");
        list.add("gamemode");
        list.add("give");
        return list;
    }

    static {
        init();
        SERVER_SPEC = SERVER_BUILDER.build();
    }
}