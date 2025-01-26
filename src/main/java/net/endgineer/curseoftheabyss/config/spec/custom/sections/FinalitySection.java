package net.endgineer.curseoftheabyss.config.spec.custom.sections;

import net.minecraftforge.common.ForgeConfigSpec;

public class FinalitySection {
    public final int DEFAULT_LAYER = 0;
    public final int MINIMUM_LAYER = 0;
    public final int MAXIMUM_LAYER = 7;
    public final ForgeConfigSpec.ConfigValue<Integer> LAYER;

    public FinalitySection(ForgeConfigSpec.Builder builder) {
        builder.push("FINALITY");

        LAYER = builder.comment(
            "Should the Abyss have a sense of finality? Death beyond this layer results in the permanent loss of all items on the player.\n"+
            "Values: [ "+MINIMUM_LAYER+", "+MAXIMUM_LAYER+" ]\n"+
            "Default: "+DEFAULT_LAYER)
            .define("LAYER", DEFAULT_LAYER);
        
        builder.pop();
    }
}
