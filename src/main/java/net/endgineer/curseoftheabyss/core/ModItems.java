package net.endgineer.curseoftheabyss.core;

import net.endgineer.curseoftheabyss.CurseOfTheAbyss;
import net.endgineer.curseoftheabyss.item.StarCompassItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, CurseOfTheAbyss.MODID);

    public static final RegistryObject<Item> STAR_COMPASS = ITEMS.register("star_compass", () -> new StarCompassItem(new Item.Properties().rarity(Rarity.EPIC).stacksTo(1)));
    
    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
