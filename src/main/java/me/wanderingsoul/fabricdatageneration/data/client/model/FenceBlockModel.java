package me.wanderingsoul.fabricdatageneration.data.client.model;

import me.wanderingsoul.fabricdatageneration.data.IBuilder;
import me.wanderingsoul.fabricdatageneration.data.client.BlockModelSerializable;

import java.util.List;

public record FenceBlockModel(BlockModelSerializable.Builder fenceInventory, BlockModelSerializable.Builder fencePost, BlockModelSerializable.Builder fenceSide) implements IModel<FenceBlockModel> {
    @Override
    public FenceBlockModel post(List<IBuilder> builders) {
        builders.add(fenceInventory);
        builders.add(fencePost);
        builders.add(fenceSide);

        return this;
    }
}
