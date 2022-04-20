package me.wanderingsoul.fabricdatageneration.data.common;

import me.wanderingsoul.fabricdatageneration.FabricDataGeneration;
import me.wanderingsoul.fabricdatageneration.data.IBuilder;
import me.wanderingsoul.fabricdatageneration.data.ISerializable;
import net.minecraft.item.Item;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.util.registry.Registry;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SmeltingRecipeSerializable implements ISerializable {
    private RecipeType recipeType = RecipeType.SMELTING;
    private Pair<IngredientType, Identifier> ingredient;
    private Identifier result;
    private float xp;
    private int cookingTime = 200;

    @Override
    public String serialize() {
        StringBuilder builder = new StringBuilder();

        builder.append("{\n\t\"type\": ").append(recipeType.toString()).append(",\n\t");
        if (ingredient.getLeft() == IngredientType.ITEM) builder.append("\"ingredient\": {\n\t\t\"item\": \"").append(ingredient.getRight().toString()).append("\"\n\t},\n\t");;
        if (ingredient.getLeft() == IngredientType.TAG) builder.append("\"ingredient\": {\n\t\t\"tag\": \"").append(ingredient.getRight().toString()).append("\"\n\t},\n\t");
        builder.append("\"result\": \"").append(result.toString()).append("\",\n\t");
        builder.append("\"experience\": ").append(xp).append(",\n\t");
        builder.append("\"cookingtime\": ").append(cookingTime).append("\n}");

        return builder.toString();
    }

    public Pair<IngredientType, Identifier> getIngredient() {
        return ingredient;
    }

    public Identifier getResult() {
        return result;
    }

    public float getXp() {
        return xp;
    }

    public int getCookingTime() {
        return cookingTime;
    }

    public static enum RecipeType {
        SMELTING, BLASTING, SMOKING;

        @Override
        public String toString() {
            if (this == SMOKING) return "\"minecraft:smoking\"";
            if (this == BLASTING) return "\"minecraft:blasting\"";
            if (this == SMELTING) return "\"minecraft:smelting\"";

            return super.toString();
        }
    }

    public static class Builder implements IBuilder<SmeltingRecipeSerializable> {
        private final SmeltingRecipeSerializable serializable = new SmeltingRecipeSerializable();
        private final Identifier id;

        public Builder(Identifier id) {
            this.id = id;
        }

        public Builder recipeType(RecipeType recipeType) {
            serializable.recipeType = recipeType;

            return this;
        }

        public Builder ingredient(IngredientType type, Identifier identifier) {
            serializable.ingredient = new Pair<>(type, identifier);

            return this;
        }

        public Builder ingredient(Item ingredient) {
            serializable.ingredient = new Pair<>(IngredientType.ITEM, Registry.ITEM.getId(ingredient));

            return this;
        }

        public Builder ingredient(TagKey<Item> ingredient) {
            serializable.ingredient = new Pair<>(IngredientType.TAG, ingredient.id());

            return this;
        }

        public Builder result(Identifier result) {
            serializable.result = result;

            return this;
        }

        public Builder result(Item item) {
            serializable.result = Registry.ITEM.getId(item);

            return this;
        }

        public Builder xp(float xp) {
            serializable.xp = xp;

            return this;
        }

        public Builder cookingTime(int cookingTime) {
            serializable.cookingTime = cookingTime;

            return this;
        }

        @Override
        public void save() {
            try {
                String path = FabricDataGeneration.getResourcePath()+"/data/"+getId().getNamespace()+"/recipes/";
                File pathFile = new File(path);
                pathFile.mkdirs();
                File json = new File(path+getId().getPath()+".json");

                if (!json.exists()) json.createNewFile();

                FileWriter writer = new FileWriter(json);

                writer.write(serializable.serialize());

                writer.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        @Override
        public Identifier getId() {
            return id;
        }

        @Override
        public SmeltingRecipeSerializable getUnderlyingValue() {
            return serializable;
        }
    }
}
