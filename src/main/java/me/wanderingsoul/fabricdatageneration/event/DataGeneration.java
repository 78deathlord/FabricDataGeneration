package me.wanderingsoul.fabricdatageneration.event;

import me.wanderingsoul.fabricdatageneration.FabricDataGeneration;
import me.wanderingsoul.fabricdatageneration.data.IBuilder;
import me.wanderingsoul.fabricdatageneration.data.ISerializable;
import me.wanderingsoul.fabricdatageneration.exception.DataGenerationFinishedException;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.util.ActionResult;

import java.io.File;
import java.util.List;

public interface DataGeneration {
    public static final Event<DataGeneration> EVENT = EventFactory.createArrayBacked(DataGeneration.class, (listeners) -> (builders) -> {
        for (DataGeneration listener : listeners) {
            ActionResult result = listener.interact(builders);

            if (result != ActionResult.PASS) {
                return result;
            }
        }

        DirManager.deleteSubDirs(new File(FabricDataGeneration.getResourcePath()));

        builders.forEach(IBuilder::save);

        throw new DataGenerationFinishedException();
    });

    ActionResult interact(List<IBuilder> builders);
}
