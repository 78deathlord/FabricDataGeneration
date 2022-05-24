package me.wanderingsoul.fabricdatageneration.event;

import me.wanderingsoul.fabricdatageneration.EnvVariables;
import me.wanderingsoul.fabricdatageneration.FabricDataGeneration;
import me.wanderingsoul.fabricdatageneration.data.IDataGenerator;
import me.wanderingsoul.fabricdatageneration.exception.DataGenerationFinishedException;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.util.ActionResult;

import java.io.*;
import java.util.List;

public interface DataGeneration {
    public static final Event<DataGeneration> EVENT = EventFactory.createArrayBacked(DataGeneration.class, (listeners) -> (generators) -> {
        for (DataGeneration listener : listeners) {
            ActionResult result = listener.interact(generators);

            if (result != ActionResult.PASS) {
                return result;
            }
        }

        generators.forEach(generator -> {
            if (EnvVariables.isModEnabled(generator.getModId())) {
                File cacheRoot = new File(generator.getResourceDirectory() + "\\cache");
                File cacheFile = new File(cacheRoot + "\\cache.txt");

                if (!cacheRoot.exists()) {
                    cacheRoot.mkdirs();
                    try {
                        cacheFile.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if (cacheFile.exists()) {
                    try {
                        String cache = readFromInputStream(new FileInputStream(cacheFile));

                        String[] files = cache.split(";");

                        for (int i = 0; i <= files.length - 2; i++) {


                            File f = new File(files[i]);

                            f.delete();

                            FabricDataGeneration.LOGGER.info("Deleted data file: " + f);
                        }

                        cacheFile.delete();
                        cacheFile.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                StringBuilder str = new StringBuilder();

                generator.getBuilders().forEach((builder -> {
                    builder.save();
                    FabricDataGeneration.LOGGER.info("Generated data file: " + builder.getSavePath());

                    str.append(builder.getSavePath()).append(";");
                }));

                try {
                    FileWriter cacheWriter = new FileWriter(cacheFile);
                    cacheWriter.write(str.toString());
                    cacheWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        throw new DataGenerationFinishedException();
    });

    ActionResult interact(List<IDataGenerator> generators);

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
}