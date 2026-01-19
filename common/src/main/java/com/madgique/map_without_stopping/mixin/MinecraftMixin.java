package com.madgique.map_without_stopping.mixin;

import com.madgique.map_without_stopping.MapScreenDetector;
import com.madgique.map_without_stopping.MovementStateHolder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MinecraftMixin {

    @Inject(
        method = "setScreen",
        at = @At("HEAD")
    )
    private void beforeSetScreen(Screen screen, CallbackInfo ci) {
        if (MapScreenDetector.INSTANCE.isMapScreen(screen) && !MovementStateHolder.INSTANCE.isMapScreenOpen()) {
            MovementStateHolder.INSTANCE.onMapScreenOpened();
        }
    }

    @Inject(
        method = "setScreen",
        at = @At("TAIL")
    )
    private void afterSetScreen(Screen screen, CallbackInfo ci) {
        if (MovementStateHolder.INSTANCE.isMapScreenOpen() && !MapScreenDetector.INSTANCE.isMapScreen(screen)) {
            MovementStateHolder.INSTANCE.onMapScreenClosed();
        }
    }

    @Inject(
        method = "tick",
        at = @At("HEAD")
    )
    private void onTick(CallbackInfo ci) {
        MovementStateHolder.INSTANCE.tick();
    }
}
