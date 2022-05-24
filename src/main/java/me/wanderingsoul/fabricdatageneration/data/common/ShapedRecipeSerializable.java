package me.wanderingsoul.fabricdatageneration.data.common;

import me.wanderingsoul.fabricdatageneration.EnvVariables;
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

public class ShapedRecipeSerializable implements ISerializable {
    private final String[] pattern = {"   ", "   ", "   "};
    private final List<Pair<Character, Pair<IngredientType, Identifier>>> keys = new LinkedList<>();
    private Identifier result;
    private int count = 1;

    @Override
    public String serialize() {
        JsonObject obj = new JsonObject();

        obj.addProperty(new JsonProperty.StringProperty("type", "minecraft:crafting_shaped"));
        obj.addProperty(new JsonProperty.ArrayProperty("pattern", new JsonArray().addProperties(new NamelessJsonProperty.StringProperty(pattern[0]), new NamelessJsonProperty.StringProperty(pattern[1]), new NamelessJsonProperty.StringProperty(pattern[2]))));

        JsonObject jsonKey = new JsonObject();
        keys.forEach(key -> {
            if (key.getRight().getLeft().equals(IngredientType.ITEM))
                jsonKey.addProperty(new JsonProperty.ObjectProperty(key.getLeft().toString(), new JsonObject().addProperty(new JsonProperty.StringProperty("item", key.getRight().getRight().toString()))));
            else
                jsonKey.addProperty(new JsonProperty.ObjectProperty(key.getLeft().toString(), new JsonObject().addProperty(new JsonProperty.StringProperty("tag", key.getRight().getRight().toString()))));
        });

        obj.addProperty(new JsonProperty.ObjectProperty("key", jsonKey));

        return obj.serialize();
    }

    public String[] getPattern() {
        return pattern;
    }

    public List<Pair<Character, Pair<IngredientType, Identifier>>> getKeys() {
        return List.copyOf(keys);
    }

    public Identifier getResult() {
        return result;
    }

    public int getCount() {
        return count;
    }

    public static class Builder implements IBuilder<ShapedRecipeSerializable> {
        private final ShapedRecipeSerializable serializable = new ShapedRecipeSerializable();
        private final Identifier id;
        private final IDataGenerator generator;
        private int row = 0;

        public Builder(Identifier id, IDataGenerator generator) {
            this.id = id;
            this.generator = generator;
        }

        public Builder pattern(String pattern) {
            serializable.pattern[row] = pattern;
            row++;

            return this;
        }

        public Builder pattern(String pattern, int row) {
            serializable.pattern[row] = pattern;
            this.row = row+1;

            return this;
        }

        public Builder defineKey(char key, Item item) {
            serializable.keys.add(new Pair<>(key, new Pair<>(IngredientType.ITEM, Registry.ITEM.getId(item.asItem()))));

            return this;
        }

        public Builder defineKey(char key, TagKey<Item> tag) {
            serializable.keys.add(new Pair<>(key, new Pair<>(IngredientType.TAG, tag.id())));

            return this;
        }

        public Builder defineKey(char key, IngredientType type, Identifier identifier) {
            serializable.keys.add(new Pair<>(key, new Pair<>(type, identifier)));

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

        public Builder count(int count) {
            serializable.count = count;

            return this;
        }

        @Override
        public void save() {
            try {
                String path = generator.getResourceDirectory()+"/data/"+getId().getNamespace()+"/recipes/";
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
            String path = generator.getResourceDirectory()+"/data/"+getId().getNamespace()+"/recipes/";
            return path+getId().getPath()+".json";
        }

        @Override
        public Identifier getId() {
            return id;
        }

        @Override
        public ShapedRecipeSerializable getUnderlyingValue() {
            return serializable;
        }
    }
}
