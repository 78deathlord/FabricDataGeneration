package me.wanderingsoul.fabricdatageneration.data.common;

import me.wanderingsoul.fabricdatageneration.EnvVariables;
import me.wanderingsoul.fabricdatageneration.data.FileUtil;
import me.wanderingsoul.fabricdatageneration.data.IBuilder;
import me.wanderingsoul.fabricdatageneration.data.IDataGenerator;
import me.wanderingsoul.fabricdatageneration.data.ISerializable;
import me.wanderingsoul.fabricdatageneration.data.json.JsonObject;
import me.wanderingsoul.fabricdatageneration.data.json.JsonProperty;
import net.minecraft.item.Item;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.util.registry.Registry;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CampfireCookingRecipeSerializable implements ISerializable {
    private Pair<IngredientType, Identifier> ingredient;
    private Identifier result;
    private double experience;
    private int cookingTime = 200;

    @Override
    public String serialize() {
        JsonObject obj = new JsonObject();
        obj.addProperty(new JsonProperty.StringProperty("type", "minecraft:campfire_cooking"));

        if (ingredient.getLeft().equals(IngredientType.ITEM))
            obj.addProperty(new JsonProperty.ObjectProperty("ingredient", new JsonObject().addProperty(new JsonProperty.StringProperty("item", ingredient.getRight().toString()))));
        else
            obj.addProperty(new JsonProperty.ObjectProperty("ingredient", new JsonObject().addProperty(new JsonProperty.StringProperty("tag", ingredient.getRight().toString()))));

        obj.addProperty(new JsonProperty.StringProperty("result", result.toString()));
        obj.addProperty(new JsonProperty.DoubleProperty("experience", experience));
        obj.addProperty(new JsonProperty.IntProperty("cookingtime", cookingTime));

        return obj.serialize();
    }

    public Pair<IngredientType, Identifier> getIngredient() {
        return ingredient;
    }

    public Identifier getResult() {
        return result;
    }

    public double getExperience() {
        return experience;
    }

    public int getCookingTime() {
        return cookingTime;
    }

    public static class Builder implements IBuilder<CampfireCookingRecipeSerializable> {
        private final CampfireCookingRecipeSerializable serializable = new CampfireCookingRecipeSerializable();
        private final Identifier id;
        private final IDataGenerator generator;

        public Builder(Identifier id, IDataGenerator generator) {
            this.id = id;
            this.generator = generator;
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
        public CampfireCookingRecipeSerializable getUnderlyingValue() {
            return serializable;
        }
    }
}
