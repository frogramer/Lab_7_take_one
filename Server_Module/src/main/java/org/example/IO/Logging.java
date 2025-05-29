package org.example.IO;
// Logging.java
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.FileAppender;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.SocketAddress;
import java.util.ArrayList;

public class Logging {
    private static final String LOG_PATH = "log.txt";
    private static boolean initialized = false;
    public static ArrayList<SocketAddress> addresses = new ArrayList<SocketAddress>();
    public static ArrayList<String> logins = new ArrayList<>();

    public static void init() {
        if (initialized) return;
        File file = new File(LOG_PATH);
        file.delete();
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();


        PatternLayoutEncoder encoder = new PatternLayoutEncoder();
        encoder.setPattern("%d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger{36} - %msg%n");
        encoder.setContext(context);
        encoder.start();


        FileAppender<ILoggingEvent> fileAppender = new FileAppender<>();
        fileAppender.setFile(LOG_PATH);
        fileAppender.setEncoder(encoder);
        fileAppender.setContext(context);
        fileAppender.start();


        Logger rootLogger = context.getLogger(Logger.ROOT_LOGGER_NAME);
        rootLogger.detachAndStopAllAppenders();
        rootLogger.addAppender(fileAppender);
        rootLogger.setLevel(Level.INFO);

        initialized = true;
        getLogger(Logging.class).info("Logging system initialized");
    }


    public static Logger getLogger(Class<?> logclass) {
        if (!initialized) init();
        return (Logger) LoggerFactory.getLogger(logclass);
    }
}