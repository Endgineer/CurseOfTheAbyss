package net.endgineer.curseoftheabyss.config.spec.abyss.sections;

import net.minecraftforge.common.ForgeConfigSpec;

public class AbyssSection {
    public final int DEFAULT_SPAN = 64;
    public final int MINIMUM_SPAN = 64;
    public final int MAXIMUM_SPAN = 2016;
    public final ForgeConfigSpec.ConfigValue<Integer> SPAN;

    public final int DEFAULT_LONGING = 10;
    public final int MINIMUM_LONGING = 0;
    public final int MAXIMUM_LONGING = 2016;
    public final ForgeConfigSpec.ConfigValue<Integer> LONGING;

    public AbyssSection(ForgeConfigSpec.Builder builder) {
        builder.push("ABYSS");

        SPAN = builder.comment(
            "The size of the Abyss. The Abyss will always start below y = 0 and end at the negative of this value.\n"+
            "Values: { n | "+MINIMUM_SPAN+" <= n <= "+MAXIMUM_SPAN+" ∧ n % 16 == 0 }\n"+
            "Default: "+DEFAULT_SPAN)
            .define("SPAN", DEFAULT_SPAN, value -> value != null && (Integer) value >= MINIMUM_SPAN && (Integer) value <= MAXIMUM_SPAN && (Integer) value % 16 == 0);
        
        LONGING = builder.comment(
            "The approximate amount of distance in blocks that a delver can ascend before being hit by the strains of ascension.\n"+
            "Values: { n | "+MINIMUM_LONGING+" <= n <= "+MAXIMUM_LONGING+" }\n"+
            "Default: "+DEFAULT_LONGING)
            .define("LONGING", DEFAULT_LONGING, value -> value != null && (Integer) value >= MINIMUM_LONGING && (Integer) value <= MAXIMUM_LONGING);
        
        builder.pop();
    }
}
