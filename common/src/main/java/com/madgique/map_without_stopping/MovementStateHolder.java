package com.madgique.map_without_stopping;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import org.lwjgl.glfw.GLFW;

public class MovementStateHolder {

    public static final MovementStateHolder INSTANCE = new MovementStateHolder();

    private boolean mapScreenOpen = false;

    private MovementStateHolder() {}

    public void onMapScreenOpened() {
        mapScreenOpen = true;
    }

    public void onMapScreenClosed() {
        mapScreenOpen = false;
    }

    public boolean isMapScreenOpen() {
        return mapScreenOpen;
    }

    public void tick() {
        if (!mapScreenOpen) return;

        Minecraft mc = Minecraft.getInstance();
        Options options = mc.options;
        long window = mc.getWindow().getWindow();

        updateKeyFromPhysicalState(options.keyUp, window);
        updateKeyFromPhysicalState(options.keyDown, window);
        updateKeyFromPhysicalState(options.keyLeft, window);
        updateKeyFromPhysicalState(options.keyRight, window);
        updateKeyFromPhysicalState(options.keyJump, window);
        updateKeyFromPhysicalState(options.keyShift, window);
        updateKeyFromPhysicalState(options.keySprint, window);
    }

    private void updateKeyFromPhysicalState(KeyMapping keyMapping, long window) {
        InputConstants.Key boundKey = InputConstants.getKey(keyMapping.saveString());
        boolean physicallyPressed;

        if (boundKey.getType() == InputConstants.Type.KEYSYM) {
            physicallyPressed = GLFW.glfwGetKey(window, boundKey.getValue()) == GLFW.GLFW_PRESS;
        } else if (boundKey.getType() == InputConstants.Type.MOUSE) {
            physicallyPressed = GLFW.glfwGetMouseButton(window, boundKey.getValue()) == GLFW.GLFW_PRESS;
        } else {
            return;
        }

        keyMapping.setDown(physicallyPressed);
    }
}
