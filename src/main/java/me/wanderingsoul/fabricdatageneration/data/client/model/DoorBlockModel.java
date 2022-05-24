package me.wanderingsoul.fabricdatageneration.data.client.model;

import me.wanderingsoul.fabricdatageneration.data.IBuilder;
import me.wanderingsoul.fabricdatageneration.data.client.BlockModelSerializable;

import java.util.List;

public record DoorBlockModel(BlockModelSerializable.Builder bottom, BlockModelSerializable.Builder bottomHinge, BlockModelSerializable.Builder top, BlockModelSerializable.Builder topHinge) implements IModel<DoorBlockModel> {
    @Override
    public DoorBlockModel post(List<IBuilder> builders) {
        builders.add(bottom);
        builders.add(bottomHinge);
        builders.add(top);
        builders.add(topHinge);

        return this;
    }
}
