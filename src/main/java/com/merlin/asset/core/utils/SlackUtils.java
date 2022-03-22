package com.merlin.asset.core.utils;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

/**
 * @author Minh-Luan H. Phan
 * Created on: 2022.03.22 12:09
 */
public class SlackUtils {

    public static Map<String, Object> buildBody(String module, String author, String logId, String title, String message) {

        final int MAX_LENGTH = 2900;
        String sentMsg;
        if (message.length() > MAX_LENGTH) {
            sentMsg = message.substring(0, MAX_LENGTH);
            if (sentMsg.contains("```")) {
                sentMsg +="```";
            }
            sentMsg +="\n...";
        } else sentMsg = message;
        return MapUtils.ImmutableMap()
                .put("text", title)
                .put("blocks", Arrays.asList(
                        MapUtils.ImmutableMap()
                                .put("type", "header")
                                .put("text", MapUtils.ImmutableMap()
                                        .put("type", "plain_text")
                                        .put("text", module)
                                        .build())
                                .build(),
                        MapUtils.ImmutableMap()
                                .put("type", "context")
                                .put("elements", Collections.singletonList(
                                        MapUtils.ImmutableMap()
                                                .put("text", String.format("<@%s> | %s | %s", author, logId, title))
                                                .put("type", "mrkdwn")
                                                .build()
                                ))
                                .build(),
                        MapUtils.ImmutableMap()
                                .put("type", "divider")
                                .build(),
                        MapUtils.ImmutableMap()
                                .put("type", "section")
                                .put("text", MapUtils.ImmutableMap()
                                        .put("type", "mrkdwn")
                                        .put("text", sentMsg)
                                        .build())
                                .build(),
                        MapUtils.ImmutableMap()
                                .put("type", "divider")
                                .build(),
                        MapUtils.ImmutableMap()
                                .put("type", "divider")
                                .build()
                ))
                .build();

    }

    public static String buildSlackMessage(Object... logMessages) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < logMessages.length; i++) {
            Object msg = logMessages[i];
            if (i % 2 == 0) {
                if (i != 0) {
                    result.append("\n");
                }
                result.append(msg).append(": ");
            } else {
                result.append(msg);
            }
        }
        return result.toString();
    }
    public static String wrapCode(String msg) {
        return String.format("```%s```", msg);
    }

    public static String boldText(String msg) {
        return String.format("*%s*", msg);
    }


}
