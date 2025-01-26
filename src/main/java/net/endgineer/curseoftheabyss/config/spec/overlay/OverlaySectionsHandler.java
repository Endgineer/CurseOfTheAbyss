package net.endgineer.curseoftheabyss.config.spec.overlay;

import net.endgineer.curseoftheabyss.config.spec.overlay.sections.OverlayFieldSection;
import net.minecraftforge.common.ForgeConfigSpec;

public class OverlaySectionsHandler {
    public final OverlayFieldSection FIELD;

    public OverlaySectionsHandler(ForgeConfigSpec.Builder builder) {
        builder.push("OVERLAY");

        FIELD = new OverlayFieldSection(builder);

        builder.pop();
    }
}
