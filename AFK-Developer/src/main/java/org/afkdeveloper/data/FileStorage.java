package org.afkdeveloper.data;

import java.nio.file.Files;
import java.nio.file.Path;

public class FileStorage {
    public static void writeString(Path path, String content) throws Exception {
        Files.createDirectories(path.getParent());
        Files.writeString(path, content);
    }
    public static String readString(Path path) throws Exception {
        if (!Files.exists(path)) return null;
        return Files.readString(path);
    }
}