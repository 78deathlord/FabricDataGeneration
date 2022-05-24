package me.wanderingsoul.fabricdatageneration.data.client.model;

import me.wanderingsoul.fabricdatageneration.data.IBuilder;
import me.wanderingsoul.fabricdatageneration.data.client.BlockModelSerializable;

import java.util.List;

public record LogBlockModel(BlockModelSerializable.Builder log, BlockModelSerializable.Builder logHorizontal) implements IModel<LogBlockModel> {
    @Override
    public LogBlockModel post(List<IBuilder> builders) {
        builders.add(log);
        builders.add(logHorizontal);

        return this;
    }
}
