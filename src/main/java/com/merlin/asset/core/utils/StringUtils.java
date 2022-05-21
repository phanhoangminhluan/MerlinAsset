package com.merlin.asset.core.utils;

import java.text.Normalizer;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

/**
 * @author Minh-Luan H. Phan
 * Created on: 2022.05.11 00:19
 */
public class StringUtils {

    public static String deAccent(String str) {
        String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(nfdNormalizedString).replaceAll("").replace("đ", "d");
    }

    public static String makeCamelCase(String str) {
        String deAccentedString = deAccent(str);
        List<String> arr = Arrays.asList(deAccentedString.split(" "));
        for (int i = 0; i < arr.size(); i++) {
            if (i == 0) {
                arr.set(0, arr.get(0).toLowerCase(Locale.ROOT));
            } else {
                String part = arr.get(i);
                if (part.length() > 0) {
                    String newPart = part.substring(0, 1).toUpperCase() + part.substring(1);
                    arr.set(i, newPart);
                }
            }
        }
        return String.join("", arr);
    }
}
