package me.wanderingsoul.fabricdatageneration.data.common;

import me.wanderingsoul.fabricdatageneration.EnvVariables;
import me.wanderingsoul.fabricdatageneration.data.FileUtil;
import me.wanderingsoul.fabricdatageneration.data.IBuilder;
import me.wanderingsoul.fabricdatageneration.data.IDataGenerator;
import me.wanderingsoul.fabricdatageneration.data.ISerializable;
import me.wanderingsoul.fabricdatageneration.data.json.JsonArray;
import me.wanderingsoul.fabricdatageneration.data.json.JsonObject;
import me.wanderingsoul.fabricdatageneration.data.json.JsonProperty;
import me.wanderingsoul.fabricdatageneration.data.json.NamelessJsonProperty;
import net.minecraft.item.Item;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.util.registry.Registry;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class ShapelessRecipeSerializable implements ISerializable {
    private final List<Pair<IngredientType, Identifier>> ingredients = new LinkedList<>();
    private Identifier result;
    private int count = 1;

    @Override
    public String serialize() {
        JsonObject obj = new JsonObject();
        obj.addProperty(new JsonProperty.StringProperty("type", "minecraft:crafting_shapeless"));

        JsonArray ingredientsJsonArray = new JsonArray();
        ingredients.forEach(ingredient -> {
            if (ingredient.getLeft().equals(IngredientType.ITEM))
                ingredientsJsonArray.addProperty(new NamelessJsonProperty.ObjectProperty(new JsonObject().addProperty(new JsonProperty.StringProperty("item", ingredient.getRight().toString()))));
            else
                ingredientsJsonArray.addProperty(new NamelessJsonProperty.ObjectProperty(new JsonObject().addProperty(new JsonProperty.StringProperty("tag", ingredient.getRight().toString()))));
        });

        obj.addProperty(new JsonProperty.ArrayProperty("ingredients", ingredientsJsonArray));

        obj.addProperty(new JsonProperty.StringProperty("result",  result.toString()));
        obj.addProperty(new JsonProperty.IntProperty("count", count));

        return obj.serialize();
    }

    public List<Pair<IngredientType, Identifier>> getIngredients() {
        return List.copyOf(ingredients);
    }

    public Identifier getResult() {
        return result;
    }

    public int getCount() {
        return count;
    }

    public static class Builder implements IBuilder<ShapelessRecipeSerializable> {
        private final ShapelessRecipeSerializable serializable = new ShapelessRecipeSerializable();
        private final Identifier id;
        private final IDataGenerator generator;

        public Builder(Identifier id, IDataGenerator generator) {
            this.id = id;
            this.generator = generator;
        }

        public Builder ingredient(IngredientType type, Identifier ingredient) {
            serializable.ingredients.add(new Pair<>(type, ingredient));

            return this;
        }

        public Builder ingredient(Item ingredient) {
            serializable.ingredients.add(new Pair<>(IngredientType.ITEM, Registry.ITEM.getId(ingredient)));

            return this;
        }

        public Builder ingredient(TagKey<Item> ingredient) {
            serializable.ingredients.add(new Pair<>(IngredientType.TAG, ingredient.id()));

            return this;
        }

        public Builder result(Identifier result) {
            serializable.result = result;

            return this;
        }

        public Builder result(Item result) {
            serializable.result = Registry.ITEM.getId(result);

            return this;
        }

        public Builder count(int count) {
            serializable.count = count;

            return this;
        }

        @Override
        public void save() {
            FileUtil.saveDataToFile(getSavePath(), getId(), serializable.serialize());
        }

        @Override
        public String getSavePath() {
            String path = generator.getResourceDirectory()+"/data/"+getId().getNamespace()+"/recipes/";
            return path+getId().getPath()+".json";
        }

        @Override
        public Identifier getId() {
            return id;
        }

        @Override
        public ShapelessRecipeSerializable getUnderlyingValue() {
            return serializable;
        }
    }
}
