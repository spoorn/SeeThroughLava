package org.spoorn.seethroughlava.config;

import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

public class WaterConfig {

    @Comment("True if this mod should override the native fog density of water with below configured value [default = true]")
    public boolean shouldOverrideWaterFogDensity = true;

    @Comment("Underwater visibility [0.0 to 1.0; 1.0 means no fog, 0 is full fog density] [default = 0.9]")
    public double underwaterVisibility = 0.9;
}
