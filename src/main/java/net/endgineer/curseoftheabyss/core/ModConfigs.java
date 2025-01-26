package net.endgineer.curseoftheabyss.core;

import net.endgineer.curseoftheabyss.CurseOfTheAbyss;
import net.endgineer.curseoftheabyss.config.spec.abyss.AbyssSectionsHandler;
import net.endgineer.curseoftheabyss.config.spec.custom.CustomSectionsHandler;
import net.endgineer.curseoftheabyss.config.spec.overlay.OverlaySectionsHandler;
import net.endgineer.curseoftheabyss.config.spec.strains.StrainsSectionHandler;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = CurseOfTheAbyss.MODID)
public class ModConfigs {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    public static final OverlaySectionsHandler OVERLAY = new OverlaySectionsHandler(BUILDER);
    public static final AbyssSectionsHandler ABYSS = new AbyssSectionsHandler(BUILDER);
    public static final StrainsSectionHandler STRAINS = new StrainsSectionHandler(BUILDER);
    public static final CustomSectionsHandler CUSTOM = new CustomSectionsHandler(BUILDER);

    public static final ForgeConfigSpec SPEC = BUILDER.build();
}
