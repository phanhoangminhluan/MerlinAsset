package com.merlin.asset.configuration.logback;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.LayoutBase;
import com.merlin.asset.core.utils.DateTimeUtils;

/**
 * @author Minh-Luan H. Phan
 * Created on: 2021.05.29 20:41
 */
public class ConsoleLogPattern extends LayoutBase<ILoggingEvent> {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_BRIGHT_RED = "\u001B[91m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_BRIGHT_CYAN = "\u001B[96m";
    public static final String ANSI_WHITE = "\u001B[37m";
    public static final String ANSI_GRAY = "\u001B[90m";

    private String setColor(String message, String color) {
        return color + message + ANSI_RESET;
    }

    @Override
    public String doLayout(ILoggingEvent iLoggingEvent) {
        String loggingDate = DateTimeUtils.convertZonedDateTimeToFormat(
                DateTimeUtils.fromEpochMilli(iLoggingEvent.getTimeStamp(), "Asia/Ho_Chi_Minh"),
                "Asia/Ho_Chi_Minh",
                DateTimeUtils.CMIE_YYYY_MM_DD_HH_MM_SS
        );
        loggingDate = setColor(loggingDate, ANSI_GRAY);
        Level level = iLoggingEvent.getLevel();
        String levelStr = level.levelStr;

        if (level.equals(Level.ALL)) {
            levelStr = setColor("ALL   ", ANSI_GREEN);
        }

        if (level.equals(Level.INFO)) {
            levelStr = setColor("INFO  ", ANSI_GREEN);
        }

        if (level.equals(Level.WARN)) {
            levelStr = setColor("WARN  ", ANSI_YELLOW);
        }

        if (level.equals(Level.ERROR)) {
            levelStr = setColor("ERROR ", ANSI_BRIGHT_RED);
        }

        if (level.equals(Level.TRACE)) {
            levelStr = setColor("TRACE ", ANSI_BRIGHT_CYAN);
        }

        String sourceClassName = iLoggingEvent.getCallerData()[0].getClassName();
        String methodName = iLoggingEvent.getCallerData()[0].getMethodName();
        int lineNumber = iLoggingEvent.getCallerData()[0].getLineNumber();

        String tracePath = setColor(sourceClassName + "#" + methodName + ":" + lineNumber, ANSI_CYAN);

        String message = iLoggingEvent.getFormattedMessage();

        return loggingDate + " - " + levelStr + " - " + tracePath + ": " + message + "\n";
    }
}
