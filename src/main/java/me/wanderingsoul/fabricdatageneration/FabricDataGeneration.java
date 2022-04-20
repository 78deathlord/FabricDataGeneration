package me.wanderingsoul.fabricdatageneration;

import me.wanderingsoul.fabricdatageneration.data.common.ShapedRecipeSerializable;
import me.wanderingsoul.fabricdatageneration.data.common.SmeltingRecipeSerializable;
import me.wanderingsoul.fabricdatageneration.data.common.SmithingRecipeSerializable;
import me.wanderingsoul.fabricdatageneration.event.DataGeneration;
import me.wanderingsoul.fabricdatageneration.registry.DataRegistries;
import net.fabricmc.api.ModInitializer;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

public class FabricDataGeneration implements ModInitializer {
    public static final String MOD_ID = "fabric_data_generation";
    private static final String GENERATE_DATA = System.getenv("generateData");
    private static final String RESOURCE_PATH = System.getenv("resourcePath");

    public static final ShapedRecipeSerializable OAK_DOOR_CUSTOM = DataRegistries.register(new ShapedRecipeSerializable.Builder(new Identifier(MOD_ID, "oak_door_custom"))
            .pattern("###")
            .pattern("###")
            .pattern("###")
            .defineKey('#', Items.OAK_PLANKS)
            .result(Items.OAK_DOOR)
            .count(6));

    public static final SmeltingRecipeSerializable LEATHER = DataRegistries.register(new SmeltingRecipeSerializable.Builder(new Identifier(MOD_ID, "leather"))
            .ingredient(Items.ROTTEN_FLESH)
            .result(Items.LEATHER)
            .xp(1f));

    public static final SmithingRecipeSerializable IRON_TO_DIAMOND_SWORD = DataRegistries.register(new SmithingRecipeSerializable.Builder(new Identifier(MOD_ID, "itds"))
            .base(Items.IRON_SWORD)
            .addition(Items.DIAMOND)
            .result(Items.DIAMOND_SWORD));

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
