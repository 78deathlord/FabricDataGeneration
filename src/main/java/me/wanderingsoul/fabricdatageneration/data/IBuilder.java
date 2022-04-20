package me.wanderingsoul.fabricdatageneration.data;

import net.minecraft.util.Identifier;

public interface IBuilder<T extends ISerializable> {
    public void save();

    public Identifier getId();

    public T getUnderlyingValue();
}
