package com.merlin.asset.core.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Minh-Luan H. Phan
 * Created on: 2022.04.20 23:31
 */
public class FileUtils {

    public static String readContent(String filePath) {
        AtomicReference<String> val = new AtomicReference<>("");
        try {
             Files.lines(Paths.get(filePath)).reduce((s, s2) -> s + s2).ifPresent(val::set);
        } catch (IOException ignore) {

        }
        return val.get();
    }

}
