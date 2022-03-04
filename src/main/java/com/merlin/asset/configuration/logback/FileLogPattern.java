package com.merlin.asset.configuration.logback;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.LayoutBase;
import com.merlin.asset.core.utils.DateTimeUtils;

/**
 * @author Minh-Luan H. Phan
 * Created on: 2021.05.30 08:48
 */
public class FileLogPattern extends LayoutBase<ILoggingEvent> {

    @Override
    public String doLayout(ILoggingEvent iLoggingEvent) {
        String loggingDate = DateTimeUtils.convertZonedDateTimeToFormat(
                DateTimeUtils.fromEpochMilli(iLoggingEvent.getTimeStamp(), "Asia/Ho_Chi_Minh"),
                "Asia/Ho_Chi_Minh",
                DateTimeUtils.CMIE_YYYY_MM_DD_HH_MM_SS
        );
        Level level = iLoggingEvent.getLevel();
        String levelStr = level.levelStr;

        if (level.equals(Level.ALL)) {
            levelStr = "ALL   ";
        }

        if (level.equals(Level.INFO)) {
            levelStr = "INFO  ";
        }

        if (level.equals(Level.WARN)) {
            levelStr = "WARN  ";
        }

        if (level.equals(Level.ERROR)) {
            levelStr = "ERROR ";
        }

        if (level.equals(Level.TRACE)) {
            levelStr = "TRACE ";
        }

        String sourceClassName = iLoggingEvent.getCallerData()[0].getClassName();
        String methodName = iLoggingEvent.getCallerData()[0].getMethodName();
        int lineNumber = iLoggingEvent.getCallerData()[0].getLineNumber();

        String tracePath = sourceClassName + "#" + methodName + ":" + lineNumber;

        String message = iLoggingEvent.getMessage();

        return loggingDate + " - " + levelStr + " - " + tracePath + ": " + message + "\n";
    }
}
