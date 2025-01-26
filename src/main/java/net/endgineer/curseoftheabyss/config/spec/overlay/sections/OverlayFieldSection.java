package net.endgineer.curseoftheabyss.config.spec.overlay.sections;

import net.minecraftforge.common.ForgeConfigSpec;

public class OverlayFieldSection {
    public final int DEFAULT_XOFFSET = 0;
    public final int DEFAULT_YOFFSET = 0;
    public final ForgeConfigSpec.ConfigValue<Integer> XOFFSET;
    public final ForgeConfigSpec.ConfigValue<Integer> YOFFSET;

    public OverlayFieldSection(ForgeConfigSpec.Builder builder) {
        builder.push("FIELD");

        XOFFSET = builder.comment("Default: "+DEFAULT_XOFFSET).define("XOFFSET", DEFAULT_XOFFSET);
        YOFFSET = builder.comment("Default: "+DEFAULT_YOFFSET).define("YOFFSET", DEFAULT_YOFFSET);

        builder.pop();
    }
}
