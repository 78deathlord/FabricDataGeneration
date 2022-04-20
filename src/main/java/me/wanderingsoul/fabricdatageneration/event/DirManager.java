package me.wanderingsoul.fabricdatageneration.event;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class DirManager {
    public static void deleteDir(File dir) {
        try {
            FileUtils.deleteDirectory(dir);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void deleteSubDirs(File dir) {
        for (File file : Objects.requireNonNull(dir.listFiles())) {
            try {
                if (file.isDirectory()) FileUtils.deleteDirectory(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
