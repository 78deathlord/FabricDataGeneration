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
import java.util.LinkedList;
import java.util.List;

public class ShapedRecipeSerializable implements ISerializable {
    private final String[] pattern = {"   ", "   ", "   "};
    private final List<Pair<Character, Pair<IngredientType, Identifier>>> keys = new LinkedList<>();
    private Identifier result;
    private int count = 1;

    @Override
    public String serialize() {
        StringBuilder builder = new StringBuilder();

        builder.append("{\n\t \"type\":\"minecraft:crafting_shaped\",\n\t\"pattern\": [\n\t\t")
                .append("\"").append(pattern[0]).append("\",\n\t\t")
                .append("\"").append(pattern[1]).append("\",\n\t\t")
                .append("\"").append(pattern[2]).append("\"\n\t],\n\t");

        builder.append("\"key\": {\n\t\t");
        keys.forEach(key -> {
            builder.append("\"").append(key.getLeft()).append("\": {\n\t\t\t");
            if (key.getRight().getLeft() == IngredientType.ITEM) builder.append("\"item\": \"").append(key.getRight().getRight().toString()).append("\"\n\t\t}\n\t},\n\t\t");
            else builder.append("\"tag\": \"").append(key.getRight().getRight().toString()).append("\"\n\t},\n\t\t");
        });

        builder.setLength(builder.length()-7);

        builder.append("\n\t},\n\t\"result\": {\n\t\t\"item\": \"").append(result.toString()).append("\",\n\t\t").append("\"count\": ").append(count).append("\n\t}\n}");

        return builder.toString();
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
        private int row = 0;

        public Builder(Identifier id) {
            this.id = id;
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
        public ShapedRecipeSerializable getUnderlyingValue() {
            return serializable;
        }
    }
}
