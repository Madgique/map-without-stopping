package com.madgique.map_without_stopping.mixin;

import com.madgique.map_without_stopping.MovementStateHolder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.world.entity.vehicle.Boat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Boat.class)
public class BoatMixin {

    @Shadow
    private boolean inputLeft;

    @Shadow
    private boolean inputRight;

    @Shadow
    private boolean inputUp;

    @Shadow
    private boolean inputDown;

    @Inject(
        method = "setInput",
        at = @At("TAIL")
    )
    private void afterSetInput(boolean left, boolean right, boolean up, boolean down, CallbackInfo ci) {
        if (!MovementStateHolder.INSTANCE.isMapScreenOpen()) {
            return;
        }

        Minecraft mc = Minecraft.getInstance();
        Options options = mc.options;
        long window = mc.getWindow().getWindow();

        this.inputLeft = MovementStateHolder.INSTANCE.isKeyPhysicallyPressed(options.keyLeft, window);
        this.inputRight = MovementStateHolder.INSTANCE.isKeyPhysicallyPressed(options.keyRight, window);
        this.inputUp = MovementStateHolder.INSTANCE.isKeyPhysicallyPressed(options.keyUp, window);
        this.inputDown = MovementStateHolder.INSTANCE.isKeyPhysicallyPressed(options.keyDown, window);
    }
}
