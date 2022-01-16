package org.spoorn.seethroughlava.config;

import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

public class WaterConfig {

    @Comment("True if this mod should override the native fog density of water with below configured value [default = false]")
    public boolean shouldOverrideWaterFogDensity = true;

    @Comment("Fog density for water [0.0 to 1.0; 0 means no fog, 1 is full fog density] [default = 0.01] [vanilla = 0.05]")
    public double waterFogDensity = 0.03;
}
