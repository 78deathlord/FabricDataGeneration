package me.wanderingsoul.fabricdatageneration.data.common;

import me.wanderingsoul.fabricdatageneration.EnvVariables;
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

public class SmokingRecipeSerializable implements ISerializable {
    private Pair<IngredientType, Identifier> ingredient;
    private Identifier result;
    private double experience;
    private int cookingTime = 200;

    @Override
    public String serialize() {
        String str =
                """
                {
                    "type": "minecraft:smoking",
                    "ingredient": {
                        "%1$s": "%2$s"
                    },
                    "result": "%3$s",
                    "experience": %4$s,
                    "cookingtime": %5$s
                }
                """;

        if (ingredient.getLeft().equals(IngredientType.ITEM)) {
            return String.format(str, IngredientType.ITEM, ingredient.getLeft().toString(), result.toString(), experience, cookingTime);
        } else {
            return String.format(str, IngredientType.TAG, ingredient.getLeft().toString(), result.toString(), experience, cookingTime);
        }
    }

    public static class Builder implements IBuilder<SmokingRecipeSerializable> {
        private final SmokingRecipeSerializable serializable = new SmokingRecipeSerializable();
        private final Identifier id;

        public Builder(Identifier id) {
            this.id = id;
        }

        public Builder ingredient(Item ingredient) {
            serializable.ingredient = new Pair<>(IngredientType.ITEM, Registry.ITEM.getId(ingredient));

            return this;
        }

        public Builder ingredient(TagKey<Item> ingredient) {
            serializable.ingredient = new Pair<>(IngredientType.TAG, ingredient.id());

            return this;
        }

        public Builder ingredient(IngredientType type, Identifier ingredient) {
            serializable.ingredient = new Pair<>(type, ingredient);

            return this;
        }

        public Builder result(Item result) {
            serializable.result = Registry.ITEM.getId(result);

            return this;
        }

        public Builder result(Identifier result) {
            serializable.result = result;

            return this;
        }

        public Builder experience(double experience) {
            serializable.experience = experience;

            return this;
        }

        public Builder cookingTime(int cookingTime) {
            serializable.cookingTime = cookingTime;

            return this;
        }

        @Override
        public void save() {
            try {
                String path = EnvVariables.RESOURCE_PATH+"/data/"+getId().getNamespace()+"/recipes/";
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
        public String getSavePath() {
            String path = EnvVariables.RESOURCE_PATH+"/data/"+getId().getNamespace()+"/recipes/";
            return path+getId().getPath()+".json";
        }

        @Override
        public Identifier getId() {
            return id;
        }

        @Override
        public SmokingRecipeSerializable getUnderlyingValue() {
            return serializable;
        }
    }
}
