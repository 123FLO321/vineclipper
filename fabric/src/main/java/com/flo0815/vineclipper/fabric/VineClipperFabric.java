package com.flo0815.vineclipper.fabric;

import com.flo0815.vineclipper.VineClipper;
import net.fabricmc.api.ModInitializer;

public class VineClipperFabric implements ModInitializer {

    public VineClipperFabric() {
        System.out.println("VineclipperFabric.VineclipperFabric");
    }

    @Override
    public void onInitialize() {
        VineClipper.init();
    }
}