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
            if (property instanceof JsonProperty.ObjectProperty) {
                JsonObject obj = ((JsonProperty.ObjectProperty)property).getValue();

                builder.append(str(property.getName())).append(":{");

                obj.getProperties().forEach(objProperty -> {
                    builder.append(objProperty.serialize()).append(",");
                });

                builder.setLength(builder.length()-1);

                builder.append("},");
            } else if (property instanceof JsonProperty.ArrayProperty) {
                JsonArray array = ((JsonProperty.ArrayProperty)property).getValue();

                builder.append(str(property.getName())).append(":[");

                array.getProperties().forEach(arrayProperty -> {
                    builder.append(arrayProperty.serialize()).append(",");
                });

                builder.setLength(builder.length()-1);

                builder.append("],");
            } else {
                builder.append(property.serialize()).append(",");
            }
        });

        builder.setLength(builder.length()-1);
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
