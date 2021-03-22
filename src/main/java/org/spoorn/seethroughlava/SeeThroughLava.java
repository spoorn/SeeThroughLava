package org.spoorn.seethroughlava;

import lombok.extern.log4j.Log4j2;
import net.fabricmc.api.ModInitializer;
import org.spoorn.seethroughlava.config.ModConfig;

@Log4j2
public class SeeThroughLava implements ModInitializer {

    public static final String MODID = "seethroughlava";

    @Override
    public void onInitialize() {
        log.info("Hello from SeeThroughLava!");
        ModConfig.init();
    }
}
