package me.wanderingsoul.fabricdatageneration.event;

import me.wanderingsoul.fabricdatageneration.EnvVariables;
import me.wanderingsoul.fabricdatageneration.FabricDataGeneration;
import me.wanderingsoul.fabricdatageneration.data.IBuilder;
import me.wanderingsoul.fabricdatageneration.exception.DataGenerationFinishedException;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.util.ActionResult;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
                Scanner cacheReader = new Scanner(cacheFile);
                while (cacheReader.hasNextLine()) {
                    File generatedDataFile = new File(cacheReader.next());
                    generatedDataFile.delete();

                    FabricDataGeneration.LOGGER.info("deleted old data file: "+generatedDataFile);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        builders.forEach((builder -> {
            if (EnvVariables.isModEnabled(builder.getId().getNamespace())) {
                builder.save();

                try {
                    FileWriter cacheWriter = new FileWriter(cacheFile);

                    cacheWriter.write("");
                    cacheWriter.flush();

                    if (builders.indexOf(builder) == builders.size()-1) {
                        cacheWriter.append(builder.getSavePath());
                    } else {
                        cacheWriter.append(builder.getSavePath()).append("\n");
                    }

                    cacheWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }));

        throw new DataGenerationFinishedException();
    });

    ActionResult interact(List<IBuilder> builders);
}
