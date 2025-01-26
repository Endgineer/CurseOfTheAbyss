package net.endgineer.curseoftheabyss.config.spec.custom;

import net.endgineer.curseoftheabyss.config.spec.custom.sections.FinalitySection;
import net.endgineer.curseoftheabyss.config.spec.custom.sections.LossSection;
import net.minecraftforge.common.ForgeConfigSpec;

public class CustomSectionsHandler {
    public final FinalitySection FINALITY;
    public final LossSection LOSS;

    public CustomSectionsHandler(ForgeConfigSpec.Builder builder) {
        builder.push("CUSTOM");

        FINALITY = new FinalitySection(builder);
        LOSS = new LossSection(builder);

        builder.pop();
    }
}
