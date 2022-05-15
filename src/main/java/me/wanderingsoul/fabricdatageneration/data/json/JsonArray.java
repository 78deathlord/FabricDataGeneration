package me.wanderingsoul.fabricdatageneration.data.json;

import me.wanderingsoul.fabricdatageneration.data.ISerializable;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class JsonArray implements ISerializable {
    private final List<NamelessJsonProperty> properties = new LinkedList<>();

    @Override
    public String serialize() {
        StringBuilder builder = new StringBuilder();

        builder.append("[");

        properties.forEach(property -> {
            builder.append(property.serialize()).append(",");
        });

        builder.setLength(builder.length()-3);

        builder.append("]");

        return builder.toString();
    }

    public JsonArray addProperty(NamelessJsonProperty property) {
        properties.add(property);

        return this;
    }

    public JsonArray addProperties(NamelessJsonProperty... properties) {
        this.properties.addAll(Arrays.asList(properties));

        return this;
    }

    public List<NamelessJsonProperty> getProperties() {
        return properties;
    }
}
