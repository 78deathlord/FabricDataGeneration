package me.wanderingsoul.fabricdatageneration.event;

import me.wanderingsoul.fabricdatageneration.EnvVariables;
import me.wanderingsoul.fabricdatageneration.data.IBuilder;
import me.wanderingsoul.fabricdatageneration.exception.DataGenerationFinishedException;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.util.ActionResult;

import java.io.File;
import java.util.List;

public interface DataGeneration {
    public static final Event<DataGeneration> EVENT = EventFactory.createArrayBacked(DataGeneration.class, (listeners) -> (builders) -> {
        if (EnvVariables.ENABLED_MODS[0].equals("no_enabled_mods")) throw new DataGenerationFinishedException();

        for (DataGeneration listener : listeners) {
            ActionResult result = listener.interact(builders);

            if (result != ActionResult.PASS) {
                return result;
            }
        }

        DirManager.deleteSubDirs(new File(EnvVariables.RESOURCE_PATH));

        builders.forEach((builder -> {
            if (EnvVariables.isModEnabled(builder.getId().getNamespace())) {
                builder.save();
            }
        }));

        throw new DataGenerationFinishedException();
    });

    ActionResult interact(List<IBuilder> builders);
}
