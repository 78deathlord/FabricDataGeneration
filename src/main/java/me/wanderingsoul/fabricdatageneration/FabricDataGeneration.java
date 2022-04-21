package me.wanderingsoul.fabricdatageneration;

import me.wanderingsoul.fabricdatageneration.event.DataGeneration;
import me.wanderingsoul.fabricdatageneration.registry.DataRegistries;
import net.fabricmc.api.ModInitializer;

public class FabricDataGeneration implements ModInitializer {
    public static final String MOD_ID = "fabric_data_generation";

    @Override
    public void onInitialize() {
        if (EnvVariables.GENERATE_DATA) {
            DataGeneration.EVENT.invoker().interact(DataRegistries.BUILDERS.stream().toList());
        }
    }
}
