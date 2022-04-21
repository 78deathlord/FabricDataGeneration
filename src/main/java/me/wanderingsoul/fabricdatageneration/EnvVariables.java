package me.wanderingsoul.fabricdatageneration;

import java.util.Arrays;

public final class EnvVariables {
    public static final Boolean GENERATE_DATA= Boolean.parseBoolean(System.getenv("generateData"));
    public static final String RESOURCE_PATH = System.getenv("resourcePath");
    public static final String[] ENABLED_MODS;

    static {
        if (System.getenv("enabledMods") != null) ENABLED_MODS = System.getenv("enabledMods").replaceAll("\\s", "").split(",");
        else ENABLED_MODS = new String[] {"no_enabled_mods"};
    }

    public static Boolean isModEnabled(String modId) {
        return Arrays.asList(ENABLED_MODS).contains(modId);
    }
}
