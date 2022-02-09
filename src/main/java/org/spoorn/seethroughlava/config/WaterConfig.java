package org.spoorn.seethroughlava.config;

import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

public class WaterConfig {

    @Comment("True if this mod should override the native fog density of water with below configured value [default = true]")
    public boolean shouldOverrideWaterFogDensity = true;

    @Comment("True if Water should have no fog at all.  False to control fog density on configurations below [default = false")
    public boolean shouldCompletelySeeThroughWater = false;

    @Comment("Factor for water fog see-through.  Higher values means you can see more [default = 60] [min = 0; max = 200]")
    public double waterSeeThroughFactor = 60.0;
}
