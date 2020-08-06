package com.github.hilol14707.customservermod.util;

import com.github.hilol14707.customservermod.Main;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogHelper {
    public static final Logger logger = LogManager.getLogger(Reference.MOD_ID);

    /**
     * logs the command sent
     * @param name name of command
     * @param action what about the command
     * @param caller who initiated the command
     * @param msg what to log
     */
    public static final void logCommand(String name, String action, String caller, String msg) {
        logger.info("[CMD " + name + "] " + caller + " [" + action + "] " + msg);
        Main.getModLogger().writeOnCmd("[" + name + "] " + caller + " [" + action + "] " + msg);
    }
}