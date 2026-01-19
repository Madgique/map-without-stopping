package com.madgique.map_without_stopping.mixin;

import com.madgique.map_without_stopping.MovementStateHolder;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.KeyboardHandler;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(KeyboardHandler.class)
public class KeyboardHandlerMixin {

    @Shadow
    @Final
    private Minecraft minecraft;

    @Inject(
        method = "keyPress",
        at = @At("HEAD")
    )
    private void onKeyPress(long window, int key, int scancode, int action, int modifiers, CallbackInfo ci) {
        if (!MovementStateHolder.INSTANCE.isMapScreenOpen()) {
            return;
        }

        boolean pressed = action != 0;

        updateKeyIfMatches(minecraft.options.keyUp, key, scancode, pressed);
        updateKeyIfMatches(minecraft.options.keyDown, key, scancode, pressed);
        updateKeyIfMatches(minecraft.options.keyLeft, key, scancode, pressed);
        updateKeyIfMatches(minecraft.options.keyRight, key, scancode, pressed);
        updateKeyIfMatches(minecraft.options.keyJump, key, scancode, pressed);
        updateKeyIfMatches(minecraft.options.keyShift, key, scancode, pressed);
        updateKeyIfMatches(minecraft.options.keySprint, key, scancode, pressed);
    }

    private void updateKeyIfMatches(KeyMapping keyMapping, int key, int scancode, boolean pressed) {
        if (keyMapping.matches(key, scancode)) {
            keyMapping.setDown(pressed);
        }
    }
}
