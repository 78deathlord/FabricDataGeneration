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

public class StonecuttingRecipeSerializable implements ISerializable {
    private Pair<IngredientType, Identifier> ingredient;
    private Identifier result;
    private int count;

    @Override
    public String serialize() {
        String str =
                """
                {
                    "type": "minecraft:stonecutting",
                    "ingredient": {
                        "%1$s": "%2$s"
                    },
                    "result": %3$s,
                    "count" %4$s
                }
                """;

        if (ingredient.getLeft().equals(IngredientType.ITEM)) {
            return String.format(str, IngredientType.ITEM, ingredient.getRight().toString(), result.toString(), count);
        } else {
            return String.format(str, IngredientType.TAG, ingredient.getRight().toString(), result.toString(), count);
        }
    }

    public static class Builder implements IBuilder<StonecuttingRecipeSerializable> {
        private final StonecuttingRecipeSerializable serializable = new StonecuttingRecipeSerializable();
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
        public StonecuttingRecipeSerializable getUnderlyingValue() {
            return serializable;
        }
    }
}
