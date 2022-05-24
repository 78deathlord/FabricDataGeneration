package me.wanderingsoul.fabricdatageneration;

import java.util.List;

public final class EnvVariables {
    public static final Boolean GENERATE_DATA = Boolean.parseBoolean(System.getenv("generateData"));
    public static final String[] ENABLED_MODS = System.getenv("enabledMods").replaceAll("\\s", "").replaceAll("\\[", "").replaceAll("]", "").split(",");

    public static boolean isModEnabled(String modId) {
        return List.of(ENABLED_MODS).contains(modId);
    }
}
