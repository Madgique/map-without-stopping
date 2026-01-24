package com.madgique.map_without_stopping;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.player.Input;
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
        // Laissé pour compatibilité avec MinecraftMixin, mais la logique est dans updateInput()
    }

    public boolean isKeyPhysicallyPressed(KeyMapping keyMapping, long window) {
        InputConstants.Key boundKey = InputConstants.getKey(keyMapping.saveString());

        if (boundKey.getType() == InputConstants.Type.KEYSYM) {
            return GLFW.glfwGetKey(window, boundKey.getValue()) == GLFW.GLFW_PRESS;
        } else if (boundKey.getType() == InputConstants.Type.MOUSE) {
            return GLFW.glfwGetMouseButton(window, boundKey.getValue()) == GLFW.GLFW_PRESS;
        }

        return false;
    }

    private float calculateImpulse(boolean positive, boolean negative) {
        if (positive == negative) {
            return 0.0f;
        }
        return positive ? 1.0f : -1.0f;
    }

    public void updateInput(Input input) {
        if (!mapScreenOpen) return;

        Minecraft mc = Minecraft.getInstance();
        Options options = mc.options;
        long window = mc.getWindow().getWindow();

        boolean forward = isKeyPhysicallyPressed(options.keyUp, window);
        boolean backward = isKeyPhysicallyPressed(options.keyDown, window);
        boolean left = isKeyPhysicallyPressed(options.keyLeft, window);
        boolean right = isKeyPhysicallyPressed(options.keyRight, window);
        boolean jump = isKeyPhysicallyPressed(options.keyJump, window);
        boolean sneak = isKeyPhysicallyPressed(options.keyShift, window);

        input.forwardImpulse = calculateImpulse(forward, backward);
        input.leftImpulse = calculateImpulse(left, right);
        input.jumping = jump;
        input.shiftKeyDown = sneak;
    }
}
