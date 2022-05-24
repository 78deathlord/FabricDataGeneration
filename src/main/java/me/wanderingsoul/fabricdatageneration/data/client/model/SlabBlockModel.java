package me.wanderingsoul.fabricdatageneration.data.client.model;

import me.wanderingsoul.fabricdatageneration.data.IBuilder;
import me.wanderingsoul.fabricdatageneration.data.client.BlockModelSerializable;

import java.util.List;

public record SlabBlockModel(BlockModelSerializable.Builder slab, BlockModelSerializable.Builder slabTop) implements IModel<SlabBlockModel> {
    @Override
    public SlabBlockModel post(List<IBuilder> builders) {
        builders.add(slab);
        builders.add(slabTop);

        return this;
    }
}
