package me.wanderingsoul.fabricdatageneration;

import me.wanderingsoul.fabricdatageneration.data.IDataGenerator;
import me.wanderingsoul.fabricdatageneration.event.DataGeneration;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FabricDataGeneration implements ModInitializer {
    public static final Registry<IDataGenerator> GENERATORS = FabricRegistryBuilder.createSimple(IDataGenerator.class, new Identifier(FabricDataGeneration.MOD_ID, "generators")).buildAndRegister();

    public static final String MOD_ID = "fabric_data_generation";
    public static final Logger LOGGER = LogManager.getLogger("FDG");

    @Override
    public void onInitialize() {
        if (EnvVariables.GENERATE_DATA) {
            DataGeneration.EVENT.invoker().interact(GENERATORS.stream().toList());
        }
    }

    public static <T extends IDataGenerator> T registerGenerator(T generator) {
        return Registry.register(GENERATORS, generator.getId(), generator);
    }
}
