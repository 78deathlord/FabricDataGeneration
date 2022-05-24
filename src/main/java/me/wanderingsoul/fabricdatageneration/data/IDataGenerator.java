package me.wanderingsoul.fabricdatageneration.data;

import net.minecraft.util.Identifier;

import java.util.List;

public interface IDataGenerator {
    String getModId();
    String getResourceDirectory();
    Identifier getId();
    List<IBuilder> getBuilders();
}
