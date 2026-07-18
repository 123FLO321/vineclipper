package com.flo0815.vineclipper;

import net.minecraft.world.level.block.state.properties.BooleanProperty;
/*? if fabric {*/
import net.fabricmc.api.ModInitializer;
/*?}*/
/*? if forge {*/
/*import net.minecraftforge.fml.common.Mod;
*/
/*?}*/
/*? if neoforge {*/
/*import net.neoforged.fml.common.Mod;
*/
/*?}*/

/*? if fabric {*/
public class VineClipper implements ModInitializer {
/*?}*/
/*? if forgeLike {*/
/*@Mod(VineClipper.MOD_ID)
public class VineClipper {
*/
/*?}*/

    public static final String MOD_ID = "vineclipper";
    public static final BooleanProperty CLIPPED = BooleanProperty.create("clipped");

    /*? if fabric {*/
    @Override
    public void onInitialize() {
    }
    /*?}*/
}
