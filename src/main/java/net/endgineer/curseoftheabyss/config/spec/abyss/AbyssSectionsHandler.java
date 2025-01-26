package net.endgineer.curseoftheabyss.config.spec.abyss;

import net.endgineer.curseoftheabyss.config.spec.abyss.sections.AbyssSection;
import net.endgineer.curseoftheabyss.config.spec.abyss.sections.DistortionSection;
import net.endgineer.curseoftheabyss.config.spec.abyss.sections.FieldSection;
import net.minecraftforge.common.ForgeConfigSpec;

public class AbyssSectionsHandler {
    public final AbyssSection ABYSS;
    public final FieldSection FIELD;
    public final DistortionSection DISTORTION;

    public AbyssSectionsHandler(ForgeConfigSpec.Builder builder) {
        builder.push("ABYSS");

        ABYSS = new AbyssSection(builder);
        FIELD = new FieldSection(builder);
        DISTORTION = new DistortionSection(builder);

        builder.pop();
    }
}
