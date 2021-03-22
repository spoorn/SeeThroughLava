package org.spoorn.seethroughlava.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.client.render.Camera;
import net.minecraft.fluid.FluidState;
import net.minecraft.tag.FluidTags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spoorn.seethroughlava.config.LavaConfig;
import org.spoorn.seethroughlava.config.ModConfig;
import org.spoorn.seethroughlava.config.WaterConfig;

@Mixin(BackgroundRenderer.class)
public class BackgroundRendererMixin {

    @Redirect(method = "applyFog", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;fogDensity(F)V"))
    private static void changeFogInWater(float density, Camera camera, BackgroundRenderer.FogType fogType,
        float viewDistance, boolean thickFog) {
        FluidState fluidState = camera.getSubmergedFluidState();
        WaterConfig waterConfig = ModConfig.get().waterConfig;

        // Default
        if (waterConfig.shouldOverrideWaterFogDensity && fluidState.isIn(FluidTags.WATER)) {
            RenderSystem.fogDensity((float) waterConfig.waterFogDensity);
        } else {
            RenderSystem.fogDensity(density);
        }
    }

    @Redirect(method = "applyFog", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;fogStart(F)V"))
    private static void changeFogStartInOther(float start, Camera camera, BackgroundRenderer.FogType fogType,
                                         float viewDistance, boolean thickFog) {
        FluidState fluidState = camera.getSubmergedFluidState();
        if (ModConfig.get().lavaConfig.shouldOverrideLavaFogDensity && fluidState.isIn(FluidTags.LAVA)) {
            RenderSystem.fogStart(0);
        } else {
            // Default
            RenderSystem.fogStart(start);
        }
    }

    @Redirect(method = "applyFog", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;fogEnd(F)V"))
    private static void changeFogEndInOther(float end, Camera camera, BackgroundRenderer.FogType fogType,
                                         float viewDistance, boolean thickFog) {
        FluidState fluidState = camera.getSubmergedFluidState();
        LavaConfig lavaConfig = ModConfig.get().lavaConfig;
        if (lavaConfig.shouldOverrideLavaFogDensity && fluidState.isIn(FluidTags.LAVA)) {
            if (ModConfig.get().lavaConfig.shouldCompletelySeeThroughLava) {
                // If this is lower than fogStart, then fog will be completely see through
                RenderSystem.fogEnd(-0.25F);
            } else {
                // User configurated factor to set fog end value
                float endVal = (float) ModConfig.get().lavaConfig.lavaSeeThroughFactor;
                endVal = endVal > 100 ? 100 : (endVal < 0 ? 0 : endVal);
                RenderSystem.fogEnd(endVal);
            }
        } else {
            // Default
            RenderSystem.fogEnd(end);
        }
    }
}
