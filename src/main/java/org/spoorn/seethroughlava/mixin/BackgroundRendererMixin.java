package org.spoorn.seethroughlava.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.CameraSubmersionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spoorn.seethroughlava.config.LavaConfig;
import org.spoorn.seethroughlava.config.ModConfig;
import org.spoorn.seethroughlava.config.WaterConfig;

/**
 * Note: Found these mixins aren't invoked if Sodium Extra mod is installed.
 */
@Mixin(BackgroundRenderer.class)
public class BackgroundRendererMixin {

    @Redirect(method = "applyFog", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;getUnderwaterVisibility()F"))
    private static float changeFogInWater(ClientPlayerEntity clientPlayerEntity, Camera camera, BackgroundRenderer.FogType fogType, float viewDistance, boolean thickFog) {
        CameraSubmersionType cameraSubmersionType = camera.getSubmersionType();
        WaterConfig waterConfig = ModConfig.get().waterConfig;

        // Default
        if (waterConfig.shouldOverrideWaterFogDensity && cameraSubmersionType == CameraSubmersionType.WATER) {
           return (float)waterConfig.underwaterVisibility;
        } else {
            return clientPlayerEntity.getUnderwaterVisibility();
        }
    }

    @Redirect(method = "applyFog", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;setShaderFogStart(F)V"))
    private static void changeFogStartInOther(float start, Camera camera, BackgroundRenderer.FogType fogType,
                                         float viewDistance, boolean thickFog) {
        CameraSubmersionType cameraSubmersionType = camera.getSubmersionType();
        LavaConfig lavaConfig = ModConfig.get().lavaConfig;

        if (lavaConfig.shouldOverrideLavaFogDensity && cameraSubmersionType == CameraSubmersionType.LAVA) {
            RenderSystem.setShaderFogStart(0);
        } else {
            // Default
            RenderSystem.setShaderFogStart(start);
        }
    }

    @Redirect(method = "applyFog", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;setShaderFogEnd(F)V"))
    private static void changeFogEndInOther(float end, Camera camera, BackgroundRenderer.FogType fogType,
                                         float viewDistance, boolean thickFog) {
        CameraSubmersionType cameraSubmersionType = camera.getSubmersionType();
        LavaConfig lavaConfig = ModConfig.get().lavaConfig;

        if (lavaConfig.shouldOverrideLavaFogDensity && cameraSubmersionType == CameraSubmersionType.LAVA) {
            if (ModConfig.get().lavaConfig.shouldCompletelySeeThroughLava) {
                RenderSystem.setShaderFogEnd(150F);
            } else {
                // User configured factor to set fog end value
                float endVal = (float) ModConfig.get().lavaConfig.lavaSeeThroughFactor;
                endVal = endVal > 200 ? 200 : (endVal < 1 ? 1 : endVal);
                RenderSystem.setShaderFogEnd(endVal);
            }
        } else {
            // Default
            RenderSystem.setShaderFogEnd(end);
        }
    }
}
