package me.wanderingsoul.fabricdatageneration.data.common;

import me.wanderingsoul.fabricdatageneration.EnvVariables;
import me.wanderingsoul.fabricdatageneration.data.IBuilder;
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

public class SmithingRecipeSerializable implements ISerializable {
    private Pair<IngredientType, Identifier> base;
    private Pair<IngredientType, Identifier> addition;
    private Identifier result;

    @Override
    public String serialize() {
        JsonObject obj = new JsonObject();

        obj.addProperty(new JsonProperty.StringProperty("type", "minecraft:smithing"));

        if (base.getLeft().equals(IngredientType.ITEM))
            obj.addProperty(new JsonProperty.ObjectProperty("base", new JsonObject().addProperty(new JsonProperty.StringProperty("item", base.getRight().toString()))));
        else
            obj.addProperty(new JsonProperty.ObjectProperty("base", new JsonObject().addProperty(new JsonProperty.StringProperty("tag", base.getRight().toString()))));

        if (addition.getLeft().equals(IngredientType.ITEM))
            obj.addProperty(new JsonProperty.ObjectProperty("addition", new JsonObject().addProperty(new JsonProperty.StringProperty("item", addition.getRight().toString()))));
        else
            obj.addProperty(new JsonProperty.ObjectProperty("addition", new JsonObject().addProperty(new JsonProperty.StringProperty("tag", addition.getRight().toString()))));

        obj.addProperty(new JsonProperty.ObjectProperty("result", new JsonObject().addProperty(new JsonProperty.StringProperty("item", result.toString()))));

        return obj.toString();
    }

    public static class Builder implements IBuilder<SmithingRecipeSerializable> {
        private final SmithingRecipeSerializable serializable = new SmithingRecipeSerializable();
        private final Identifier id;

        public Builder(Identifier id) {
            this.id = id;
        }

        public Builder base(IngredientType type, Identifier identifier) {
            serializable.base = new Pair<>(type, identifier);

            return this;
        }

        public Builder base(Item base) {
            serializable.base = new Pair<>(IngredientType.ITEM, Registry.ITEM.getId(base));

            return this;
        }

        public Builder base(TagKey<Item> base) {
            serializable.base = new Pair<>(IngredientType.TAG, base.id());

            return this;
        }

        public Builder addition(IngredientType type, Identifier identifier) {
            serializable.addition = new Pair<>(type, identifier);

            return this;
        }

        public Builder addition(Item base) {
            serializable.addition = new Pair<>(IngredientType.ITEM, Registry.ITEM.getId(base));

            return this;
        }

        public Builder addition(TagKey<Item> base) {
            serializable.addition = new Pair<>(IngredientType.TAG, base.id());

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
        public SmithingRecipeSerializable getUnderlyingValue() {
            return serializable;
        }
    }
}
