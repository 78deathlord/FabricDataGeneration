package me.wanderingsoul.fabricdatageneration.data.common;

import me.wanderingsoul.fabricdatageneration.EnvVariables;
import me.wanderingsoul.fabricdatageneration.FabricDataGeneration;
import me.wanderingsoul.fabricdatageneration.data.IBuilder;
import me.wanderingsoul.fabricdatageneration.data.ISerializable;
import net.minecraft.item.Item;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class ShapelessRecipeSerializable implements ISerializable {
    private String group;
    private final List<Pair<IngredientType, Identifier>> ingredients = new LinkedList<>();
    private Identifier result;
    private int count = 1;

    @Override
    public String serialize() {
        StringBuilder builder = new StringBuilder();

        builder.append("{\n\t\"type\": \"minecraft:crafting_shapeless\",\n\t\"group\": \"").append(group).append("\",\n\t");

        builder.append("\"ingredients\": [\n\t\t{\n\t\t\t");
        ingredients.forEach(ingredient -> {
            if (ingredient.getLeft() == IngredientType.ITEM) builder.append("\"item\": \"").append(ingredient.getRight().toString()).append("\",\n\t\t\t");
            else builder.append("\"tag\": \"").append(ingredient.getRight().toString()).append("\",\n\t\t\t");
        });

        builder.setLength(builder.length()-5);

        builder.append("\n\t\t}\n\t],\n\t");

        builder.append("\"result\": {\n\t\t\"item\": \"").append(result.toString()).append("\",\n\t\t\"count\": ").append(count).append("\n\t}\n}");

        LogManager.getLogger().info(builder.toString());

        return builder.toString();
    }

    public String getGroup() {
        return group;
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

        public Builder(Identifier id) {
            this.id = id;
        }

        public Builder group(String group) {
            serializable.group = group;

            return this;
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
        public Identifier getId() {
            return id;
        }

        @Override
        public ShapelessRecipeSerializable getUnderlyingValue() {
            return serializable;
        }
    }
}
