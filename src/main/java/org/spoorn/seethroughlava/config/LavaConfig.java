package org.spoorn.seethroughlava.config;

import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

public class LavaConfig {

    @Comment("True if this mod should override the native fog density of lava with below configured values [default = true]")
    public boolean shouldOverrideLavaFogDensity = true;

    @Comment("True if Lava should have no fog at all.  False to control fog density on configurations below [default = false")
    public boolean shouldCompletelySeeThroughLava = false;

    @Comment("Factor for lava fog see-through.  Higher values means you can see more [default = 60] [min = 0; max = 200]")
    public double lavaSeeThroughFactor = 60.0;

    @Comment("True to remove the fire particle screen effect when IN LAVA ONLY [default = false]")
    public boolean removeFireScreenEffectInLava = false;

    @Comment("True to remove the fire particle screen effect when on fire (including in lava) [default = false]")
    public boolean removeFireScreenEffectIfOnFire = false;
}
