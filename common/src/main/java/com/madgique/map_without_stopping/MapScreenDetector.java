package com.madgique.map_without_stopping;

import net.minecraft.client.gui.screens.Screen;
import java.util.List;

public class MapScreenDetector {

    public static final MapScreenDetector INSTANCE = new MapScreenDetector();

    private static final List<String> ALLOWED_PACKAGES = List.of(
        "journeymap.",
        "xaero.map."
    );

    private MapScreenDetector() {}

    public boolean isMapScreen(Screen screen) {
        if (screen == null) return false;

        String className = screen.getClass().getName();
        return ALLOWED_PACKAGES.stream().anyMatch(className::startsWith);
    }
}
