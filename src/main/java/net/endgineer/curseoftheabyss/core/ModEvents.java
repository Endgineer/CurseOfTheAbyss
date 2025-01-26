package net.endgineer.curseoftheabyss.core;

import java.util.Map;

import croissantnova.sanitydim.SanityProcessor;
import croissantnova.sanitydim.capability.SanityProvider;
import dev.ghen.thirst.foundation.common.capability.ModCapabilities;
import net.endgineer.curseoftheabyss.CurseOfTheAbyss;
import net.endgineer.curseoftheabyss.common.Abyss;
import net.endgineer.curseoftheabyss.common.CurseCapability;
import net.endgineer.curseoftheabyss.common.CurseProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;

@Mod.EventBusSubscriber(modid = CurseOfTheAbyss.MODID)
public class ModEvents {
    @SubscribeEvent
    public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event) {
        if(event.getObject() instanceof Player) {
            if(!event.getObject().getCapability(CurseProvider.CURSE).isPresent()) {
                event.addCapability(new ResourceLocation(CurseOfTheAbyss.MODID, "properties"), new CurseProvider());
            }
        }
    }

    @SubscribeEvent
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        event.register(CurseCapability.class);
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if(event.side == LogicalSide.SERVER && event.phase.equals(TickEvent.Phase.END)) {
            event.player.getCapability(CurseProvider.CURSE).ifPresent(curse -> {
                curse.tick(event.player);

                if(ModList.get().isLoaded("thirst")) {
                    event.player.getCapability(ModCapabilities.PLAYER_THIRST).ifPresent(thirst -> {
                        thirst.addExhaustion(event.player, (float) curse.getStrains().observeExhaustion(false));
                    });
                }
                event.player.getFoodData().addExhaustion((float) curse.getStrains().observeExhaustion(true));

                curse.getStrains().observeNumbness(true);

                if(ModList.get().isLoaded("sanitydim")) {
                    event.player.getCapability(SanityProvider.CAP).ifPresent(sanity -> {
                        if(curse.getDerangement() > sanity.getSanity()) {
                            sanity.setSanity((float) curse.getDerangement());
                        }

                        SanityProcessor.addSanity(sanity, (float) curse.getStrains().observeHallucination(true), (ServerPlayer) event.player);
                    });
                }
                
                event.player.hurt(new DamageSource(Abyss.CURSE_DAMAGE), (float) curse.getStrains().observeDeformation(true));
                
                curse.getStrains().observeDeprivation(true);
            });
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onLivingDeath(LivingDeathEvent event) {
        Entity livingEntity = event.getEntity();
        if(livingEntity instanceof Player) {
            Player player = ((Player) livingEntity);
            
            if(player.level().dimension().location().getPath() == "overworld" && Abyss.layer(player.getY()) > ModConfigs.CUSTOM.FINALITY.LAYER.get()) {
                player.getInventory().clearContent();
                
                if(ModList.get().isLoaded("curios")) {
                    CuriosApi.getCuriosHelper().getCuriosHandler(player).ifPresent(handler -> {
                        Map<String, ICurioStacksHandler> curios = handler.getCurios();
                        curios.forEach((id, stacksHandler) -> {
                            IDynamicStackHandler stacks = stacksHandler.getStacks();
                            for (int i = 0; i < stacks.getSlots(); i++) {
                                stacks.setStackInSlot(i, ItemStack.EMPTY);
                            }
                        });
                    });
                }
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onLivingHeal(LivingHealEvent event) {
        Entity livingEntity = event.getEntity();
        if(livingEntity instanceof Player) {
            Player player = ((Player) livingEntity);
            
            player.getCapability(CurseProvider.CURSE).ifPresent(curse -> {
                if(Abyss.layer(curse.getLowestDepth()) > ModVariables.DEFORMATION.YIELD_LAYER && player.getHealth()+event.getAmount() > curse.getConstitution()) {
                    event.setAmount((float) curse.getConstitution() - player.getHealth());
                }
            });
        }
    }

    @SubscribeEvent
    public static void onServerChatEvent(ServerChatEvent event) {
        event.getPlayer().getCapability(CurseProvider.CURSE).ifPresent(curse -> {
            event.setMessage(Component.translatable((event.getPlayer().level().dimension().location().getPath() == "overworld" ? event.getPlayer().getY() : 0)+"\n"+curse.getDerangement()+"\n"+event.getUsername()+"\n"+event.getMessage()));
        });
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onClientChatReceived(ClientChatReceivedEvent event) {
        Minecraft minecraft = Minecraft.getInstance();

        String[] constituents = event.getMessage().getString().split("\n", 4);

        if(constituents.length != 4) { return; }
        
        double loss = Abyss.loss(Double.parseDouble(constituents[0]), minecraft.player.level().dimension().location().getPath() == "overworld" ? minecraft.player.getY() : 0);
        double corruption = Double.parseDouble(constituents[1])*loss;

        if(loss == 1 && ModConfigs.CUSTOM.LOSS.FULL.get()) {
            event.setCanceled(true);
        } else if(ModConfigs.CUSTOM.LOSS.PARTIAL.get()) {
            String modifiedUsername = "", modifiedMessage = "";

            for(int i = 0; i < constituents[2].length(); i++) {
                modifiedUsername += Math.random() < corruption ? "§k"+constituents[2].charAt(i)+"§r" : constituents[2].charAt(i);
            }

            for(int i = 0; i < constituents[3].length(); i++) {
                modifiedMessage += Math.random() < corruption ? "§k"+constituents[3].charAt(i)+"§r" : constituents[3].charAt(i);
            }

            event.setMessage(Component.translatable("<"+modifiedUsername+"> "+modifiedMessage));
        } else {
            event.setMessage(Component.translatable("<"+constituents[2]+"> "+constituents[3]));
        }
    }
    
    // @OnlyIn(Dist.CLIENT)
    // @SubscribeEvent
    // public static void onPlaySound(PlaySoundEvent event) {
    //     if(StrainsData.getDeprivationProgress() == 1 && event.getSound().getSource() != SoundSource.MASTER) {
    //         event.setSound(null);
    //     }
    // }

    // @OnlyIn(Dist.CLIENT)
    // @SubscribeEvent
    // public static void onRenderFog(RenderFogEvent event) {
    //     if(StrainsData.getDeprivationProgress() > 0) {
    //         event.setCanceled(true);
    //         event.setFogShape(FogShape.SPHERE);
    //         event.scaleNearPlaneDistance((float) (1 - StrainsData.getDeprivationProgress()));
    //         event.scaleFarPlaneDistance((float) (1 - StrainsData.getDeprivationProgress()));
    //     }
    // }

    // @OnlyIn(Dist.CLIENT)
    // @SubscribeEvent
    // public static void onFogColor(FogColors color) {
    //     if(StrainsData.getDeprivationProgress() > 0) {
    //         color.setRed((float) (1 - StrainsData.getDeprivationProgress()));
    //         color.setBlue((float) (1 - StrainsData.getDeprivationProgress()));
    //         color.setGreen((float) (1 - StrainsData.getDeprivationProgress()));
    //     }
    // }

    // @SubscribeEvent
    // public static void onRenderTick(RenderTickEvent event) {
    //     if(event.phase.equals(TickEvent.Phase.END)) {
    //         EVRenderer.render();
    //     }
    // }

    // @OnlyIn(Dist.CLIENT)
    // @SubscribeEvent
    // public static void onRenderGuiOverlayEvent(RenderGuiOverlayEvent event) {
    //     Minecraft mc = Minecraft.getInstance();
    //     if(!mc.isPaused()) {
    //         if(StrainsData.getDeprivationProgress() == 1) {
    //             event.setCanceled(true);
    //         }
    //     }

    //     VisualManager.onTick(mc.player);
    // }
}
