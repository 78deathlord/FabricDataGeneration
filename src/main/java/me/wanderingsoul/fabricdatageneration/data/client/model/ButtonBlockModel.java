package me.wanderingsoul.fabricdatageneration.data.client.model;

import me.wanderingsoul.fabricdatageneration.data.IBuilder;
import me.wanderingsoul.fabricdatageneration.data.client.BlockModelSerializable;

import java.util.List;

public record ButtonBlockModel(BlockModelSerializable.Builder button, BlockModelSerializable.Builder buttonInventory, BlockModelSerializable.Builder buttonPressed) implements IModel<ButtonBlockModel> {
    @Override
    public ButtonBlockModel post(List<IBuilder> builders) {
        builders.add(button);
        builders.add(buttonInventory);
        builders.add(buttonPressed);

        return this;
    }
}
