package com.github.hilol14707.customservermod.logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.github.hilol14707.customservermod.util.LogHelper;
import com.github.hilol14707.customservermod.util.Reference;

public class ModLogger {
    private String logFolder, currentSession;
    private File file;
    private BufferedWriter writer;

    public ModLogger() {
        // file is located at logs/csm/<@date>.log
        logFolder = System.getProperty("user.dir") + "\\logs\\csm\\";
        currentSession = new SimpleDateFormat("yyyy-MM-dd_HH-mm").format(new Date());
        file = new File(logFolder);
        // creates dir if non existent
        if (!file.exists()) {
            file.mkdirs();
        }
        LogHelper.logger.info("initialized log file at " + currentSession);
        try {
            writer = new BufferedWriter(new FileWriter(logFolder + currentSession + ".log", true));
        } catch (IOException e) {
            e.printStackTrace();
            LogHelper.logger.error("An error has occurred when trying to start the log file write stream.");
        }
        write(Reference.NAME + " version " + Reference.VERSION + " is initialized.\n  name  |  description\n--------|---------------\ngeneral | general message\ncommand | recorded command is attempted to be used\nchatMsg | chat message\nplayerJ | player joined the game\nplayerL | player left the game\nplayerD | player died\nadvance | advancement\n");
    }

    private void writeData(String data, String type) {
        try {
            writer.newLine();
            writer.write(type + "[" + new SimpleDateFormat("HH:mm:ss.SSS").format(new Date()) + "] " + data);
        } catch (IOException e) {
            e.printStackTrace();
            LogHelper.logger.error("An error has occurred when trying to append to file:");
            LogHelper.logger.error(data);
        }
    }

    public void save() {
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            writer = new BufferedWriter(new FileWriter(logFolder + currentSession + ".log", true));
        } catch (IOException e) {
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
            LogHelper.logger.error("An error has occurred when trying to close file on server shutdown.");
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