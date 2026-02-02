package com.madgique.map_without_stopping.mixin;

import com.madgique.map_without_stopping.MovementStateHolder;
import net.minecraft.client.player.KeyboardInput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(KeyboardInput.class)
public class KeyboardInputMixin {

    @Inject(
        method = "tick",
        at = @At("TAIL")
    )
    private void afterTick(boolean slowDown, CallbackInfo ci) {
        if (MovementStateHolder.INSTANCE.isMapScreenOpen()) {
            MovementStateHolder.INSTANCE.updateInput((KeyboardInput) (Object) this);
        }
    }
}
