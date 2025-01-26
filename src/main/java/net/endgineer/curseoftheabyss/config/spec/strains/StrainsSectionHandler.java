package net.endgineer.curseoftheabyss.config.spec.strains;

import net.endgineer.curseoftheabyss.config.spec.strains.sections.DeformationSection;
import net.endgineer.curseoftheabyss.config.spec.strains.sections.DeprivationSection;
import net.endgineer.curseoftheabyss.config.spec.strains.sections.HallucinationSection;
import net.endgineer.curseoftheabyss.config.spec.strains.sections.NumbnessSection;
import net.endgineer.curseoftheabyss.config.spec.strains.sections.ExhaustionSection;
import net.minecraftforge.common.ForgeConfigSpec;

public class StrainsSectionHandler {
    public final DeformationSection DEFORMATION;
    public final DeprivationSection DEPRIVATION;
    public final HallucinationSection HALLUCINATION;
    public final NumbnessSection NUMBNESS;
    public final ExhaustionSection EXHAUSTION;

    public StrainsSectionHandler(ForgeConfigSpec.Builder builder) {
        builder.push("STRAINS");

        DEFORMATION = new DeformationSection(builder);
        DEPRIVATION = new DeprivationSection(builder);
        HALLUCINATION = new HallucinationSection(builder);
        NUMBNESS = new NumbnessSection(builder);
        EXHAUSTION = new ExhaustionSection(builder);

        builder.pop();
    }
}
