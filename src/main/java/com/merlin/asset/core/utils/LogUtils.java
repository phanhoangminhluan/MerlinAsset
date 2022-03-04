package com.merlin.asset.core.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

public class LogUtils {

    public static String getStackTrace(Exception exception) {
        StringWriter stringWriter = new StringWriter();
        exception.printStackTrace(new PrintWriter(stringWriter));
        return stringWriter.toString();
    }

    public static String buildLogMsg(Object... logMessages) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < logMessages.length; i++) {
            Object msg = logMessages[i];
            if (i % 2 == 0) {
                if (i != 0) {
                    result.append(", ");
                }
                result.append(msg).append(": ");
            } else {
                result.append(msg);
            }
        }
        return result.toString();
    }
}
