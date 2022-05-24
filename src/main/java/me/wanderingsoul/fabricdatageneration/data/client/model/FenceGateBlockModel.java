package me.wanderingsoul.fabricdatageneration.data.client.model;

import me.wanderingsoul.fabricdatageneration.data.IBuilder;
import me.wanderingsoul.fabricdatageneration.data.client.BlockModelSerializable;

import java.util.List;

public record FenceGateBlockModel(BlockModelSerializable.Builder fenceGate, BlockModelSerializable.Builder fenceGateOpen, BlockModelSerializable.Builder fenceGateWall, BlockModelSerializable.Builder fenceGateWallOpen) implements IModel<FenceGateBlockModel> {
    @Override
    public FenceGateBlockModel post(List<IBuilder> builders) {
        builders.add(fenceGate);
        builders.add(fenceGateOpen);
        builders.add(fenceGateWall);
        builders.add(fenceGateWallOpen);

        return this;
    }
}
