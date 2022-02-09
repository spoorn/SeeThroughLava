package org.spoorn.seethroughlava.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.CameraSubmersionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spoorn.seethroughlava.config.LavaConfig;
import org.spoorn.seethroughlava.config.ModConfig;
import org.spoorn.seethroughlava.config.WaterConfig;

/**
 * Note: Found these mixins aren't invoked if Sodium Extra mod is installed.
 * 
 * Set priority to 1001 to ensure these mixins apply after other mods.
 */
@Mixin(value = BackgroundRenderer.class, priority = 1001)
public class BackgroundRendererMixin {
    
    // Override fog at the TAIL to avoid conflicts with other mods such as Origins that did redirection which I used to do
    @Inject(method = "applyFog", at = @At(value = "TAIL"))
    private static void changeForInLava(Camera camera, BackgroundRenderer.FogType fogType, float viewDistance, boolean thickFog, CallbackInfo ci) {
        CameraSubmersionType cameraSubmersionType = camera.getSubmersionType();
        WaterConfig waterConfig = ModConfig.get().waterConfig;
        LavaConfig lavaConfig = ModConfig.get().lavaConfig;

        // Default
        if (waterConfig.shouldOverrideWaterFogDensity && cameraSubmersionType == CameraSubmersionType.WATER) {
            if (ModConfig.get().waterConfig.shouldCompletelySeeThroughWater) {
                RenderSystem.setShaderFogEnd(150F);
            } else {
                // User configured factor to set fog end value
                float endVal = (float) ModConfig.get().waterConfig.waterSeeThroughFactor;
                endVal = endVal > 200 ? 200 : (endVal < 1 ? 1 : endVal);
                RenderSystem.setShaderFogEnd(endVal);
            }
        }

        if (lavaConfig.shouldOverrideLavaFogDensity && cameraSubmersionType == CameraSubmersionType.LAVA) {
            RenderSystem.setShaderFogStart(0);

            if (ModConfig.get().lavaConfig.shouldCompletelySeeThroughLava) {
                RenderSystem.setShaderFogEnd(150F);
            } else {
                // User configured factor to set fog end value
                float endVal = (float) ModConfig.get().lavaConfig.lavaSeeThroughFactor;
                endVal = endVal > 200 ? 200 : (endVal < 1 ? 1 : endVal);
                RenderSystem.setShaderFogEnd(endVal);
            }
        }
    }
}
