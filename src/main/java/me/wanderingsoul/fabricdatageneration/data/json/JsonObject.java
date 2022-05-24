package me.wanderingsoul.fabricdatageneration.data.json;

import me.wanderingsoul.fabricdatageneration.data.ISerializable;

import java.util.LinkedList;
import java.util.List;

public class JsonObject implements ISerializable {
    private final List<JsonProperty> properties = new LinkedList<>();

    @Override
    public String serialize() {
        StringBuilder builder = new StringBuilder();

        builder.append("{");

        properties.forEach(property -> {
            builder.append(property.serialize()).append(",");
        });

        if (properties.size() >= 1) builder.setLength(builder.length()-1);
        builder.append("}");

        return builder.toString();
    }

    private String str(String str) {
        return "\""+str+"\"";
    }

    public JsonObject addProperty(JsonProperty property) {
        properties.add(property);

        return this;
    }

    public List<JsonProperty> getProperties() {
        return properties;
    }

    @Override
    public String toString() {
        return serialize();
    }
}
