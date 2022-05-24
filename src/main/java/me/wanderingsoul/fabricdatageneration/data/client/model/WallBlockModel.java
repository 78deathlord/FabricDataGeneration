package me.wanderingsoul.fabricdatageneration.data.client.model;

import me.wanderingsoul.fabricdatageneration.data.IBuilder;
import me.wanderingsoul.fabricdatageneration.data.client.BlockModelSerializable;

import java.util.List;

public record WallBlockModel(BlockModelSerializable.Builder wallInventory, BlockModelSerializable.Builder wallPost, BlockModelSerializable.Builder wallSide, BlockModelSerializable.Builder wallSideTall) implements IModel<WallBlockModel> {
    @Override
    public WallBlockModel post(List<IBuilder> builders) {
        builders.add(wallInventory);
        builders.add(wallPost);
        builders.add(wallSide);
        builders.add(wallSideTall);

        return this;
    }
}
