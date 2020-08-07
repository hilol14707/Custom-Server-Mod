package com.github.hilol14707.customservermod.logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.github.hilol14707.customservermod.Main;
import com.github.hilol14707.customservermod.util.Reference;

public class ModLogger {
    private String logFileLocation;
    private File file;
    private BufferedWriter writer;

    public ModLogger() {
        // file is located at logs/csm/<@date>.log
        String logFolder = System.getProperty("user.dir") + "\\logs\\csm\\";
        String currentSession = new SimpleDateFormat("yyyy-MM-dd_HH-mm").format(new Date());
        logFileLocation = logFolder + currentSession + ".log";
        file = new File(logFolder);
        if (!file.exists()) {
            file.mkdirs();
        }
        try {
            writer = new BufferedWriter(new FileWriter(logFileLocation, true));
            Main.LOGGER.info("initialized log file at " + logFileLocation);
        } catch (IOException e) {
            e.printStackTrace();
            Main.LOGGER.error("An error has occurred when trying to start the log file write stream.");
        }
        write(Reference.NAME + " version " + Reference.VERSION + " is initialized." + System.lineSeparator()
            + "  name  |  description" + System.lineSeparator()
            + "--------|---------------" + System.lineSeparator()
            + "general | general message" + System.lineSeparator()
            + "command | recorded command is attempted to be used" + System.lineSeparator()
            + "chatMsg | chat message" + System.lineSeparator()
            + "playerJ | player joined the game" + System.lineSeparator()
            + "playerL | player left the game" + System.lineSeparator()
            + "playerD | player died" + System.lineSeparator()
            + "advance | advancement" + System.lineSeparator());
    }

    private void writeData(String data, String type) {
        try {
            writer.newLine();
            writer.write(type + "[" + new SimpleDateFormat("HH:mm:ss.SSS").format(new Date()) + "] " + data);
        } catch (IOException e) {
            e.printStackTrace();
            Main.LOGGER.error("An error has occurred when trying to append to file:");
            Main.LOGGER.error(data);
            e.printStackTrace();
        }
    }

    public void save() {
        try {
            writer.close();
            writer = new BufferedWriter(new FileWriter(logFileLocation, true));
        } catch (IOException e) {
            Main.LOGGER.error("Error in saving the log file at:" + logFileLocation);
            e.printStackTrace();
        }
    }

    public void write(String data) {
        writeData(data, "general");
    }

    public void writerStop() {
        write("server stopping");
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            Main.LOGGER.error("An error has occurred when trying to close file on server shutdown.");
        }
    }

    public void writeOnCmd(String data) {
        writeData(data, "command");
    }

    public void writeOnChat(String data) {
        writeData(data, "chatMsg");
    }

    public void writeOnPlayerDeath(String data) {
        writeData(data, "playerD");
    }

    public void writeOnPlayerJoin(String data) {
        writeData(data, "playerJ");
    }

    public void writeOnPlayerLeave(String data) {
        writeData(data, "playerL");
    }

    public void writeOnAdvancement(String data) {
        writeData(data, "advance");
    }
}