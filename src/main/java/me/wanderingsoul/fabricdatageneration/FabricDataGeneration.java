package me.wanderingsoul.fabricdatageneration;

import me.wanderingsoul.fabricdatageneration.event.DataGeneration;
import me.wanderingsoul.fabricdatageneration.registry.DataRegistries;
import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FabricDataGeneration implements ModInitializer {
    public static final String MOD_ID = "fabric_data_generation";
    public static final Logger LOGGER = LogManager.getLogger("FDG");

    @Override
    public void onInitialize() {
        if (EnvVariables.GENERATE_DATA) {
            DataGeneration.EVENT.invoker().interact(DataRegistries.BUILDERS.stream().toList());
        }
    }
}
