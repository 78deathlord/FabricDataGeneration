package me.wanderingsoul.fabricdatageneration.registry;

import me.wanderingsoul.fabricdatageneration.FabricDataGeneration;
import me.wanderingsoul.fabricdatageneration.data.IBuilder;
import me.wanderingsoul.fabricdatageneration.data.ISerializable;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class DataRegistries {
    public static final Registry<IBuilder> BUILDERS = FabricRegistryBuilder.createSimple(IBuilder.class, new Identifier(FabricDataGeneration.MOD_ID, "builders")).buildAndRegister();

    public static <T extends ISerializable> T register(IBuilder<T> builder) {
        Registry.register(BUILDERS, builder.getId(), builder);

        return builder.getUnderlyingValue();
    }
}
