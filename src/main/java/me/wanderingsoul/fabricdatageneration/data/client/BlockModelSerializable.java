package me.wanderingsoul.fabricdatageneration.data.client;

import me.wanderingsoul.fabricdatageneration.data.*;
import me.wanderingsoul.fabricdatageneration.data.client.model.*;
import me.wanderingsoul.fabricdatageneration.data.json.JsonArray;
import me.wanderingsoul.fabricdatageneration.data.json.JsonObject;
import me.wanderingsoul.fabricdatageneration.data.json.JsonProperty;
import me.wanderingsoul.fabricdatageneration.data.json.NamelessJsonProperty;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3f;
import net.minecraft.util.math.Vector4f;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class BlockModelSerializable implements ISerializable {
    private Identifier parent;
    private JsonObject textures;
    private List<ModelElement> elements;

    @Override
    public String serialize() {
        JsonObject obj = new JsonObject();
        if (parent != null) obj.addProperty(new JsonProperty.StringProperty("parent", parent.toString()));
        if (textures != null) obj.addProperty(new JsonProperty.ObjectProperty("textures", textures));

        if (elements != null) {
            JsonArray elementsArr = new JsonArray();
            elements.forEach(elementsArr::addProperty);

            obj.addProperty(new JsonProperty.ArrayProperty("elements", elementsArr));
        }

        return obj.serialize();
    }

    public static class ModelElement implements NamelessJsonProperty {
        private final Vec3f from;
        private final Vec3f to;
        private final int color;
        private final List<Face> faces = new LinkedList<>();

        public ModelElement(Vec3f from, Vec3f to, int color) {
            this.from = from;
            this.to = to;
            this.color = color;
        }

        public ModelElement(Vec3f from, Vec3f to, int color, Face... faces) {
            this.from = from;
            this.to = to;
            this.color = color;
            this.faces.addAll(List.of(faces));
        }

        @Override
        public String serialize() {
            JsonObject obj = new JsonObject();

            JsonArray fromArr = new JsonArray();
            fromArr.addProperty(new FloatProperty(from.getX()));
            fromArr.addProperty(new FloatProperty(from.getY()));
            fromArr.addProperty(new FloatProperty(from.getZ()));

            JsonArray toArr = new JsonArray();
            toArr.addProperty(new FloatProperty(to.getX()));
            toArr.addProperty(new FloatProperty(to.getY()));
            toArr.addProperty(new FloatProperty(to.getZ()));

            obj.addProperty(new JsonProperty.ArrayProperty("from", fromArr));
            obj.addProperty(new JsonProperty.ArrayProperty("to", toArr));
            obj.addProperty(new JsonProperty.IntProperty("color", color));

            JsonObject facesObj = new JsonObject();
            faces.forEach(facesObj::addProperty);

            obj.addProperty(new JsonProperty.ObjectProperty("faces", facesObj));

            return obj.serialize();
        }

        public ModelElement face(Face face) {
            faces.add(face);

            return this;
        }

        public static class Face implements JsonProperty {
            private final String name;

            private final Vector4f uv;
            private final String texture;
            private String cullface;

            public Face(String name, Vector4f uv, String texture) {
                this.name = name;
                this.uv = uv;
                this.texture = texture;
            }

            @Override
            public String serialize() {
                JsonObject obj = new JsonObject();

                JsonArray uvArr = new JsonArray();
                uvArr.addProperty(new NamelessJsonProperty.FloatProperty(uv.getX()));
                uvArr.addProperty(new NamelessJsonProperty.FloatProperty(uv.getY()));
                uvArr.addProperty(new NamelessJsonProperty.FloatProperty(uv.getZ()));
                uvArr.addProperty(new NamelessJsonProperty.FloatProperty(uv.getW()));

                obj.addProperty(new ArrayProperty("uv", uvArr));

                obj.addProperty(new StringProperty("texture", texture));

                if (cullface != null) obj.addProperty(new StringProperty("cullface", cullface));

                return new ObjectProperty(name, obj).serialize();
            }

            public Face cullFace(String cullface) {
                this.cullface = cullface;

                return this;
            }

            private String str(String str) {
                return "\""+str+"\"";
            }

            @Override
            public String getName() {
                return name;
            }

            public Vector4f getUV() {
                return uv;
            }

            public String getTexture() {
                return texture;
            }

            public String getCullface() {
                return cullface;
            }
        }
    }

    public static class Builder implements IBuilder<BlockModelSerializable> {
        private final BlockModelSerializable serializable = new BlockModelSerializable();
        private final Identifier id;
        private final IDataGenerator generator;

        public Builder(Identifier id, IDataGenerator generator) {
            this.id = id;
            this.generator = generator;
        }

        public static ButtonBlockModel button(String modId, IDataGenerator generator, String materialName, Texture... textures) {
            return new ButtonBlockModel(
                    button(new Identifier(modId, materialName+"_button"), generator, textures),
                    buttonInventory(new Identifier(modId, materialName+"_button_inventory"), generator, textures),
                    buttonPressed(new Identifier(modId, materialName), generator, textures)
            );
        }

        public static Builder button(Identifier id, IDataGenerator generator, Texture... textures) {
            Builder builder = new Builder(id, generator).parent(new Identifier("minecraft", "block/button"));

            for (Texture texture : textures) builder.texture(texture);

            return builder;
        }

        public static Builder buttonInventory(Identifier id, IDataGenerator generator, Texture... textures) {
            Builder builder = new Builder(id, generator).parent(new Identifier("minecraft", "block/button_inventory"));

            for (Texture texture : textures) builder.texture(texture);

            return builder;
        }

        public static Builder buttonPressed(Identifier id, IDataGenerator generator, Texture... textures) {
            Builder builder = new Builder(id, generator).parent(new Identifier("minecraft", "block/button_press"));

            for (Texture texture : textures) builder.texture(texture);

            return builder;
        }

        public static DoorBlockModel door(String modId, IDataGenerator generator, String materialName, Texture... textures) {
            return new DoorBlockModel(
                    doorBottom(new Identifier(modId, materialName+"_door_bottom"), generator, textures),
                    doorBottomHinge(new Identifier(modId, materialName+"_door_bottom_hinge"), generator, textures),
                    doorTop(new Identifier(modId, materialName+"_door_top"), generator, textures),
                    doorTopHinge(new Identifier(modId, materialName+"_door_top_hinge"), generator, textures)
            );
        }

        public static Builder doorBottom(Identifier id, IDataGenerator generator, Texture... textures) {
            Builder builder = new Builder(id, generator).parent(new Identifier("minecraft", "block/door_bottom"));

            for (Texture texture : textures) builder.texture(texture);

            return builder;
        }

        public static Builder doorBottomHinge(Identifier id, IDataGenerator generator, Texture... textures) {
            Builder builder = new Builder(id, generator).parent(new Identifier("minecraft", "block/door_bottom_rh"));

            for (Texture texture : textures) builder.texture(texture);

            return builder;
        }

        public static Builder doorTop(Identifier id, IDataGenerator generator, Texture... textures) {
            Builder builder = new Builder(id, generator).parent(new Identifier("minecraft", "block/door_top"));

            for (Texture texture : textures) builder.texture(texture);

            return builder;
        }

        public static Builder doorTopHinge(Identifier id, IDataGenerator generator, Texture... textures) {
            Builder builder = new Builder(id, generator).parent(new Identifier("minecraft", "block/door_top_rh"));

            for (Texture texture : textures) builder.texture(texture);

            return builder;
        }

        public static FenceGateBlockModel fenceGate(String modId, IDataGenerator generator, String materialName, Texture... textures) {
            return new FenceGateBlockModel(
                    fenceGate(new Identifier(modId, materialName+"_fence_gate"), generator, textures),
                    fenceGateOpen(new Identifier(modId, materialName+"_fence_gate_open"), generator, textures),
                    fenceGateWall(new Identifier(modId, materialName+"_fence_gate_wall"), generator, textures),
                    fenceGateWallOpen(new Identifier(modId, materialName+"_fence_gate_wall_open"), generator, textures)
            );
        }

        public static Builder fenceGate(Identifier id, IDataGenerator generator, Texture... textures) {
            Builder builder = new Builder(id, generator).parent(new Identifier("minecraft", "block/template_fence_gate"));

            for (Texture texture : textures) builder.texture(texture);

            return builder;
        }

        public static Builder fenceGateOpen(Identifier id, IDataGenerator generator, Texture... textures) {
            Builder builder = new Builder(id, generator).parent(new Identifier("minecraft", "block/template_fence_gate_open"));

            for (Texture texture : textures) builder.texture(texture);

            return builder;
        }

        public static Builder fenceGateWall(Identifier id, IDataGenerator generator, Texture... textures) {
            Builder builder = new Builder(id, generator).parent(new Identifier("minecraft", "block/template_fence_gate_wall"));

            for (Texture texture : textures) builder.texture(texture);

            return builder;
        }

        public static Builder fenceGateWallOpen(Identifier id, IDataGenerator generator, Texture... textures) {
            Builder builder = new Builder(id, generator).parent(new Identifier("minecraft", "block/template_fence_gate_wall_open"));

            for (Texture texture : textures) builder.texture(texture);

            return builder;
        }

        public static FenceBlockModel fence(String modId, IDataGenerator generator, String materialName, Texture... textures) {
            return new FenceBlockModel(
                    fenceInventory(new Identifier(modId, materialName+"fence_inventory"), generator, textures),
                    fencePost(new Identifier(modId, materialName+"fence_post"), generator, textures),
                    fenceSide(new Identifier(modId, materialName+"fence_side"), generator, textures)

            );
        }

        public static Builder fenceInventory(Identifier id, IDataGenerator generator, Texture... textures) {
            Builder builder = new Builder(id, generator).parent(new Identifier("minecraft", "block/fence_inventory"));

            for (Texture texture : textures) builder.texture(texture);

            return builder;
        }

        public static Builder fencePost(Identifier id, IDataGenerator generator, Texture... textures) {
            Builder builder = new Builder(id, generator).parent(new Identifier("minecraft", "block/fence_post"));

            for (Texture texture : textures) builder.texture(texture);

            return builder;
        }

        public static Builder fenceSide(Identifier id, IDataGenerator generator, Texture... textures) {
            Builder builder = new Builder(id, generator).parent(new Identifier("minecraft", "block/fence_side"));

            for (Texture texture : textures) builder.texture(texture);

            return builder;
        }

        public static Builder leaves(Identifier id, IDataGenerator generator, Texture leavesTexture) {
            return new Builder(id, generator).parent(new Identifier("minecraft", "block/leaves")).texture(leavesTexture);
        }

        public static LogBlockModel log(String modId, String logName, IDataGenerator generator, Texture end, Texture side) {
            return new LogBlockModel(
                    cubeColumn(new Identifier(modId, logName), generator, end, side),
                    cubeColumnHorizontal(new Identifier(modId, logName+"_horizontal"), generator, end, side)
            );
        }

        public static Builder wood(Identifier id, IDataGenerator generator, Identifier logTexture) {
            return cubeColumn(id, generator, new Texture("end", logTexture), new Texture("side", logTexture));
        }

        public static Builder cubeColumn(Identifier id, IDataGenerator generator, Texture end, Texture side) {
            return new Builder(id, generator).parent(new Identifier("minecraft", "block/cube_column")).texture(end).texture(side);
        }

        public static Builder cubeColumnHorizontal(Identifier id, IDataGenerator generator, Texture end, Texture side) {
            return new Builder(id, generator).parent(new Identifier("minecraft", "block/cube_column_horizontal")).texture(end).texture(side);
        }

        public static Builder planks(Identifier id, IDataGenerator generator, Texture planksTexture) {
            return cubeAll(id, generator, planksTexture);
        }

        public static Builder cubeAll(Identifier id, IDataGenerator generator, Texture all) {
            return new Builder(id, generator).parent(new Identifier("minecraft", "block/cube_all")).texture(all);
        }

        public static PressurePlateBlockModel pressurePlate(String modId, String pressurePlateName, IDataGenerator generator, Texture texture) {
            return new PressurePlateBlockModel(
                    pressurePlate(new Identifier(modId, pressurePlateName), generator, texture),
                    pressurePlateDown(new Identifier(modId, pressurePlateName+"_down"), generator, texture)
            );
        }

        public static Builder pressurePlate(Identifier id, IDataGenerator generator, Texture texture) {
            return new Builder(id, generator).parent(new Identifier("minecraft", "block/pressure_plate_up")).texture(texture);
        }

        public static Builder pressurePlateDown(Identifier id, IDataGenerator generator, Texture texture) {
            return new Builder(id, generator).parent(new Identifier("minecraft", "block/pressure_plate_down")).texture(texture);
        }

        public static Builder sapling(Identifier id, IDataGenerator generator, Texture cross) {
            return cross(id, generator, cross);
        }

        public static Builder cross(Identifier id, IDataGenerator generator, Texture cross) {
            return new Builder(id, generator).parent(new Identifier("minecraft", "block/cross")).texture(cross);
        }

        public static Builder sign(Identifier id, IDataGenerator generator, Texture particle) {
            return new Builder(id, generator).texture(particle);
        }

        public static SlabBlockModel slab(String modId, String slabName, IDataGenerator generator, Texture bottom, Texture top, Texture side) {
            return new SlabBlockModel(
                    slab(new Identifier(modId, slabName), generator, bottom, top, side),
                    slabTop(new Identifier(modId, slabName+"_top"), generator, bottom, top, side)
            );
        }

        public static Builder slab(Identifier id, IDataGenerator generator, Texture bottom, Texture top, Texture side) {
            return new Builder(id, generator).parent(new Identifier("minecraft", "block/slab")).texture(bottom).texture(top).texture(side);
        }

        public static Builder slabTop(Identifier id, IDataGenerator generator, Texture bottom, Texture top, Texture side) {
            return new Builder(id, generator).parent(new Identifier("minecraft", "block/slab_top")).texture(bottom).texture(top).texture(side);
        }

        public static Builder rail(Identifier id, IDataGenerator generator, Texture rail) {
            return new Builder(id, generator).parent(new Identifier("minecraft", "block/rail_flat")).texture(rail);
        }

        public static Builder railRaisedNE(Identifier id, IDataGenerator generator, Texture rail) {
            return new Builder(id, generator).parent(new Identifier("minecraft", "block/template_rail_raised_ne")).texture(rail);
        }

        public static Builder railRaisedSW(Identifier id, IDataGenerator generator, Texture rail) {
            return new Builder(id, generator).parent(new Identifier("minecraft", "block/template_rail_raised_sw")).texture(rail);
        }

        public static Builder stemFruit(Identifier id, IDataGenerator generator, Texture stem, Texture upperstem) {
            return new Builder(id, generator).parent(new Identifier("minecraft", "block/stem_fruit")).texture(stem).texture(upperstem);
        }

        public static Builder crop(Identifier id, IDataGenerator generator, Texture crop) {
            return new Builder(id, generator).parent(new Identifier("minecraft", "block/crop")).texture(crop);
        }

        public static WallBlockModel wall(String modId, String wallName, IDataGenerator generator, Texture wall) {
            return new WallBlockModel(
                    wallInventory(new Identifier(modId, wallName+"_inventory"), generator, wall),
                    wallPost(new Identifier(modId, wallName+"_post"), generator, wall),
                    wallSide(new Identifier(modId, wallName+"_side"), generator, wall),
                    wallSideTall(new Identifier(modId, wallName+"_side_tall"), generator, wall)
            );
        }

        public static Builder wallInventory(Identifier id, IDataGenerator generator, Texture wall) {
            return new Builder(id, generator).parent(new Identifier("minecraft", "block/wall_inventory")).texture(wall);
        }

        public static Builder wallPost(Identifier id, IDataGenerator generator, Texture wall) {
            return new Builder(id, generator).parent(new Identifier("minecraft", "block/template_wall_post")).texture(wall);
        }

        public static Builder wallSide(Identifier id, IDataGenerator generator, Texture wall) {
            return new Builder(id, generator).parent(new Identifier("minecraft", "block/template_wall_side")).texture(wall);
        }

        public static Builder wallSideTall(Identifier id, IDataGenerator generator, Texture wall) {
            return new Builder(id, generator).parent(new Identifier("minecraft", "block/template_wall_side_tall")).texture(wall);
        }

        public Builder parent(Identifier parent) {
            serializable.parent = parent;

            return this;
        }

        public Builder texture(Texture texture) {
            if (serializable.textures == null) serializable.textures = new JsonObject();

            serializable.textures.addProperty(new JsonProperty.StringProperty(texture.name(), texture.id().toString()));

            return this;
        }

        public Builder element(ModelElement element) {
            if (serializable.elements == null) serializable.elements = new LinkedList<>();

            serializable.elements.add(element);

            return this;
        }

        @Override
        public void save() {
            FileUtil.saveDataToFile(getSavePath(), getId(), serializable.serialize());
        }

        @Override
        public String getSavePath() {
            return generator.getResourceDirectory()+"/assets/"+id.getNamespace()+"/models/block/"+id.getPath()+".json";
        }

        @Override
        public Identifier getId() {
            return id;
        }

        @Override
        public BlockModelSerializable getUnderlyingValue() {
            return serializable;
        }
    }
}
