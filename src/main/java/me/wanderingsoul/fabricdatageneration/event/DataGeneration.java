package me.wanderingsoul.fabricdatageneration.event;

import me.wanderingsoul.fabricdatageneration.EnvVariables;
import me.wanderingsoul.fabricdatageneration.FabricDataGeneration;
import me.wanderingsoul.fabricdatageneration.data.IBuilder;
import me.wanderingsoul.fabricdatageneration.exception.DataGenerationFinishedException;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.util.ActionResult;

import java.io.*;
import java.util.List;
import java.util.Scanner;

public interface DataGeneration {
    public static final Event<DataGeneration> EVENT = EventFactory.createArrayBacked(DataGeneration.class, (listeners) -> (builders) -> {
        if (EnvVariables.ENABLED_MODS[0].equals("no_enabled_mods")) throw new DataGenerationFinishedException();

        for (DataGeneration listener : listeners) {
            ActionResult result = listener.interact(builders);

            if (result != ActionResult.PASS) {
                return result;
            }
        }

        File cacheRoot = new File(EnvVariables.RESOURCE_PATH+"\\cache");
        File cacheFile = new File(cacheRoot+"\\cache.txt");

        if (!cacheRoot.exists()) {
            cacheRoot.mkdirs();
            try {
                cacheFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (cacheFile.exists()){
            try {
                String cache = readFromInputStream(new FileInputStream(cacheFile));

                String[] files = cache.split(";");

                for (int i = 0; i <= files.length-2; i++) {


                    File f = new File(files[i]);

                    f.delete();

                    FabricDataGeneration.LOGGER.info("Deleted data file: "+f);
                }

                cacheFile.delete();
                cacheFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        StringBuilder str = new StringBuilder();

        builders.forEach((builder -> {
            if (EnvVariables.isModEnabled(builder.getId().getNamespace())) {
                builder.save();
                FabricDataGeneration.LOGGER.info("Generated data file: "+builder.getSavePath());

                str.append(builder.getSavePath()).append(";");
            }
        }));

        try {
            FileWriter cacheWriter = new FileWriter(cacheFile);
            cacheWriter.write(str.toString());
            cacheWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        throw new DataGenerationFinishedException();
    });

    private static String readFromInputStream(InputStream inputStream) {
        StringBuilder resultStringBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                resultStringBuilder.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultStringBuilder.toString();
    }

    ActionResult interact(List<IBuilder> builders);
}
