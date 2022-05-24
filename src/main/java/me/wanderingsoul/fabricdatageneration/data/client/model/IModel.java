package me.wanderingsoul.fabricdatageneration.data.client.model;

import me.wanderingsoul.fabricdatageneration.data.IBuilder;

import java.util.List;

public interface IModel<T extends IModel<T>> {
    T post(List<IBuilder> builders);
}
