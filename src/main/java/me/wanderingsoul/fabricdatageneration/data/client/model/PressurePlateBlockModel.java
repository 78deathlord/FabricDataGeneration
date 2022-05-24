package me.wanderingsoul.fabricdatageneration.data.client.model;

import me.wanderingsoul.fabricdatageneration.data.IBuilder;
import me.wanderingsoul.fabricdatageneration.data.client.BlockModelSerializable;

import java.util.List;

public record PressurePlateBlockModel(BlockModelSerializable.Builder pressurePlate, BlockModelSerializable.Builder pressurePlateDown) implements IModel<PressurePlateBlockModel> {
    @Override
    public PressurePlateBlockModel post(List<IBuilder> builders) {
        builders.add(pressurePlate);
        builders.add(pressurePlateDown);

        return this;
    }
}
