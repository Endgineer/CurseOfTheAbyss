package net.endgineer.curseoftheabyss.core;

import net.endgineer.curseoftheabyss.CurseOfTheAbyss;
import net.endgineer.curseoftheabyss.effect.CurseResistanceEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEffects {
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, CurseOfTheAbyss.MODID);

    public static final RegistryObject<MobEffect> CURSE_RESISTANCE = EFFECTS.register("curse_resistance", () -> new CurseResistanceEffect(MobEffectCategory.BENEFICIAL, 0x000000));
    
    public static void register(IEventBus eventBus) {
        EFFECTS.register(eventBus);
    }
}
