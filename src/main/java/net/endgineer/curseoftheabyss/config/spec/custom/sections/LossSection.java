package net.endgineer.curseoftheabyss.config.spec.custom.sections;

import net.minecraftforge.common.ForgeConfigSpec;

public class LossSection {
    public final boolean DEFAULT_FULL = true;
    public final ForgeConfigSpec.ConfigValue<Boolean> FULL;

    public final boolean DEFAULT_PARTIAL = true;
    public final ForgeConfigSpec.ConfigValue<Boolean> PARTIAL;

    public LossSection(ForgeConfigSpec.Builder builder) {
        builder.push("LOSS");

        FULL = builder.comment(
            "Should there be a chance that messages are completely lost when sent between those on the surface and those in the Abyss?\n"+
            "Values: { false, true }\n"+
            "Default: "+DEFAULT_FULL)
            .define("FULL", DEFAULT_FULL);

        PARTIAL = builder.comment(
            "Should derangement and the time distortion of the Abyss affect the intelligibility of the contents of messages?\n"+
            "Values: { false, true }\n"+
            "Default: "+DEFAULT_PARTIAL)
            .define("PARTIAL", DEFAULT_PARTIAL);
        
        builder.pop();
    }
}
