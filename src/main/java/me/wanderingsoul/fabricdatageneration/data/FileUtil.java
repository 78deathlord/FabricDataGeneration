package me.wanderingsoul.fabricdatageneration.data;

import net.minecraft.util.Identifier;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public final class FileUtil {
    public static void saveDataToFile(String filePath, Identifier id, String data) {
        try {
            File directory = new File(filePath.split("/" + id.getPath())[0]);
            directory.mkdirs();
            File dataFile = new File(filePath);
            dataFile.createNewFile();
            FileWriter writer = new FileWriter(dataFile);
            writer.write(data);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
