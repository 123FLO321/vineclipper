package com.flo0815.vineclipper.forge;

import com.flo0815.vineclipper.VineClipper;
import dev.architectury.platform.forge.EventBuses;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(VineClipper.MOD_ID)
public class VineClipperForge {
    public VineClipperForge() {
		// Submit our event bus to let architectury register our content on the right time
        EventBuses.registerModEventBus(VineClipper.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        VineClipper.init();
    }
}