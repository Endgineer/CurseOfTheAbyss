package net.endgineer.curseoftheabyss.config.variables.abyss;

import net.endgineer.curseoftheabyss.core.ModConfigs;

public class AbyssVariables {
    public final int SPAN = ModConfigs.ABYSS.ABYSS.SPAN.get();
    public final int LONGING = ModConfigs.ABYSS.ABYSS.LONGING.get();
    public final double INVERSE_SPAN = 1.0D / ModConfigs.ABYSS.ABYSS.SPAN.get();
    public final double LEVEL_DEPTH = ModConfigs.ABYSS.ABYSS.SPAN.get() / 7.0D;
}
