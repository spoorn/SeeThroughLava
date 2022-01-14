package org.spoorn.seethroughlava.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameOverlayRenderer;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spoorn.seethroughlava.config.LavaConfig;
import org.spoorn.seethroughlava.config.ModConfig;

@Mixin(InGameOverlayRenderer.class)
public class InGameOverlayRendererMixin {

    @Inject(method = "renderFireOverlay", at = @At(value = "HEAD"), cancellable = true)
    private static void removeFireOverlay(MinecraftClient client, MatrixStack matrices, CallbackInfo ci) {
        LavaConfig lavaConfig = ModConfig.get().lavaConfig;
        if (client.player != null
                && ((lavaConfig.removeFireScreenEffectInLava && client.player.isInLava())
                    || (lavaConfig.removeFireScreenEffectIfOnFire && client.player.isOnFire()))) {
            ci.cancel();
        }
    }
}
