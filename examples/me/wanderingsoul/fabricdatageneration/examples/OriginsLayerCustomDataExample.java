package me.wanderingsoul.fabricdatageneration.examples;

import me.wanderingsoul.fabricdatageneration.data.FileUtil;
import me.wanderingsoul.fabricdatageneration.data.IBuilder;
import me.wanderingsoul.fabricdatageneration.data.IDataGenerator;
import me.wanderingsoul.fabricdatageneration.data.ISerializable;
import me.wanderingsoul.fabricdatageneration.data.json.JsonArray;
import me.wanderingsoul.fabricdatageneration.data.json.JsonObject;
import me.wanderingsoul.fabricdatageneration.data.json.JsonProperty;
import me.wanderingsoul.fabricdatageneration.data.json.NamelessJsonProperty;
import net.minecraft.util.Identifier;

import java.util.LinkedList;
import java.util.List;

public class OriginsLayerCustomDataExample implements ISerializable {
    //optionally create getters for these
    private boolean replace = false;
    private /* lists can be final */ final List<Identifier> origins = new LinkedList<>();
    private int order = 25;

    //serialize function, JsonObject class helps, JsonArray also helps, it's an array
    @Override
    public String serialize() {
        JsonObject obj = new JsonObject();

        obj.addProperty(new JsonProperty.BooleanProperty("replace", replace));

        JsonArray originsArr = new JsonArray();
        origins.forEach(identifier -> originsArr.addProperty(new NamelessJsonProperty.StringProperty(identifier.toString())));
        obj.addProperty(new JsonProperty.ArrayProperty("origins", originsArr));

        obj.addProperty(new JsonProperty.IntProperty("order", order));

        return obj.serialize();
    }

    //builder, must implement IBuilder<DataClass>
    public static class Builder implements IBuilder<OriginsLayerCustomDataExample> {
        private final OriginsLayerCustomDataExample serializable = new OriginsLayerCustomDataExample();
        private final Identifier id;
        private final IDataGenerator generator;

        //constructor
        public Builder(Identifier id, IDataGenerator generator) {
            this.id = id;
            this.generator = generator;
        }

        //function for editing the replace variable make sure to return "this"
        //make sure to create one of these functions for every json property you need
        public Builder replace(boolean replace) {
            serializable.replace = replace;

            return this;
        }

        //function for adding an origin to the origin list
        //optionally you can create a function to remove an origin from the list
        //make sure that the function returns "this" again
        public Builder origin(Identifier origin) {
            serializable.origins.add(origin);

            return this;
        }

        //you know the drill by now
        public Builder order(int order) {
            serializable.order = order;

            return this;
        }

        //file saving, just use FileUtil#saveDataToFile(String filePath, Identifier id, String data)
        @Override
        public void save() {
            FileUtil.saveDataToFile(getSavePath(), getId(), serializable.serialize());
        }

        //returns the file's path
        @Override
        public String getSavePath() {
            return generator.getResourceDirectory()+"/data/origins/origin_lays"+id.getPath()+".json";
        }

        //returns the json/builder's identifier
        @Override
        public Identifier getId() {
            return id;
        }

        //return the instance of your data class you've been modifying
        @Override
        public OriginsLayerCustomDataExample getUnderlyingValue() {
            return serializable;
        }
    }
}
