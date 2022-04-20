package me.wanderingsoul.fabricdatageneration;

import me.wanderingsoul.fabricdatageneration.event.DataGeneration;
import me.wanderingsoul.fabricdatageneration.registry.DataRegistries;
import net.fabricmc.api.ModInitializer;

public class FabricDataGeneration implements ModInitializer {
    public static final String MOD_ID = "fabric_data_generation";
    private static final String GENERATE_DATA = System.getenv("generateData");
    private static final String RESOURCE_PATH = System.getenv("resourcePath");

    @Override
    public void onInitialize() {
        if (Boolean.parseBoolean(GENERATE_DATA)) {
            DataGeneration.EVENT.invoker().interact(DataRegistries.BUILDERS.stream().toList());
        }
    }

    public static String getResourcePath() {
        return RESOURCE_PATH;
    }
}
