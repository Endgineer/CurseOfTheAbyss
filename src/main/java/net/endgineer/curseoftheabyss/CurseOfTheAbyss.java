package net.endgineer.curseoftheabyss;

import com.mojang.logging.LogUtils;

import croissantnova.sanitydim.item.ItemRegistry;
import net.endgineer.curseoftheabyss.core.ModConfigs;
import net.endgineer.curseoftheabyss.core.ModEffects;
import net.endgineer.curseoftheabyss.core.ModItems;
import net.endgineer.curseoftheabyss.core.ModOverlays;
import net.endgineer.curseoftheabyss.core.ModPotions;
import net.endgineer.curseoftheabyss.network.PacketHandler;
import net.endgineer.curseoftheabyss.util.creativemd.enhancedvisuals.common.addon.curseoftheabyss.CurseOfTheAbyssShaders;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.common.crafting.StrictNBTIngredient;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;

import org.slf4j.Logger;

@Mod(CurseOfTheAbyss.MODID)
public class CurseOfTheAbyss {
    public static final String MODID = "curseoftheabyss";
    
    public static final Logger LOGGER = LogUtils.getLogger();

    public CurseOfTheAbyss() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.register(modEventBus);
        ModEffects.register(modEventBus);
        ModPotions.register(modEventBus);
        if(!FMLEnvironment.dist.isDedicatedServer()) {
            ModOverlays.register(modEventBus);
        }

        modEventBus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(this);
        
        modEventBus.addListener(this::addCreative);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ModConfigs.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            PacketHandler.init();
            if(!FMLEnvironment.dist.isDedicatedServer()) {
                CurseOfTheAbyssShaders.load();
            }
            addBrewingRecipe(Potions.AWKWARD, ItemRegistry.NIGHTMARE_FUEL.get(), ModPotions.UMBRAL_POTION.get());
            addBrewingRecipe(ModPotions.UMBRAL_POTION.get(), Items.REDSTONE, ModPotions.LONG_UMBRAL_POTION.get());
        });
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if(event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            event.accept(ModItems.STAR_COMPASS);
        }
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {}

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {}
    }
    
    private void addBrewingRecipe(Potion inputPotion, Item brewingItem, Potion outputPotion) {
        BrewingRecipeRegistry.addRecipe(StrictNBTIngredient.of(PotionUtils.setPotion(new ItemStack(Items.POTION), inputPotion)), Ingredient.of(brewingItem), PotionUtils.setPotion(new ItemStack(Items.POTION), outputPotion));
    }
}
