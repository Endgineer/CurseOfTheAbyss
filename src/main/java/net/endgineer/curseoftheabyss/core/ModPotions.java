package net.endgineer.curseoftheabyss.core;

import net.endgineer.curseoftheabyss.CurseOfTheAbyss;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModPotions {
    public static final DeferredRegister<Potion> POTIONS = DeferredRegister.create(ForgeRegistries.POTIONS, CurseOfTheAbyss.MODID);

    public static final RegistryObject<Potion> UMBRAL_POTION = POTIONS.register("umbral_potion", () -> new Potion(new MobEffectInstance(ModEffects.CURSE_RESISTANCE.get(), 3600)));
    public static final RegistryObject<Potion> LONG_UMBRAL_POTION = POTIONS.register("long_umbral_potion", () -> new Potion(new MobEffectInstance(ModEffects.CURSE_RESISTANCE.get(), 9600)));
    
    public static void register(IEventBus eventBus) {
        POTIONS.register(eventBus);
    }
}
