package net.endgineer.curseoftheabyss.core;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.endgineer.curseoftheabyss.CurseOfTheAbyss;
import net.endgineer.curseoftheabyss.client.CurseData;
import net.minecraft.client.Minecraft;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraftforge.fml.ModList;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraftforge.eventbus.api.IEventBus;
import top.theillusivec4.curios.api.CuriosApi;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ModOverlays {
    public static final ResourceLocation FIELD_BAR = new ResourceLocation(CurseOfTheAbyss.MODID, "textures/gui/field.png");
    
    static Minecraft minecraft = Minecraft.getInstance();

    public static void register(IEventBus eventBus) {
        eventBus.addListener(ModOverlays::onRegisterOverlay);
    }

    private static void onRegisterOverlay(RegisterGuiOverlaysEvent event) {
        event.registerAboveAll(CurseOfTheAbyss.MODID + "_overlay", (gui, guiGraphics, partialTicks, screenWidth, screenHeight) -> {
            if(!minecraft.options.hideGui && (minecraft.player.isHolding(ModItems.STAR_COMPASS.get()) || ModList.get().isLoaded("curios") && !CuriosApi.getCuriosHelper().findFirstCurio(minecraft.player, ModItems.STAR_COMPASS.get()).isEmpty())) {
                gui.setupOverlayRenderState(true, false);
                render(guiGraphics, screenWidth, screenHeight);
            }
        });
    }

    public static void render(GuiGraphics guiGraphics, int screenWidth, int screenHeight) {
        minecraft.getProfiler().push(CurseOfTheAbyss.MODID + "_overlay");
        
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);

        int xPos = screenWidth / 2;
        int yPos = 2;

        ModOverlays.drawCenteredBarWithHead(guiGraphics, CurseData.getField(), xPos + ModConfigs.OVERLAY.FIELD.XOFFSET.get(), yPos + ModConfigs.OVERLAY.FIELD.YOFFSET.get(), 0xFFFFFF, 0x000000);
        
        RenderSystem.disableBlend();
        
        minecraft.getProfiler().pop();
    }

    public static void drawCenteredBarWithHead(GuiGraphics guiGraphics, double value, int x, int y, int fontColor, int fontOutlineColor) {
        guiGraphics.blit(FIELD_BAR, x - 91, y, 0, 0, 182, 5, 182, 5);
        
        y -= 2;
        x += (int) (Math.ceil(value*179) - 90);

        guiGraphics.drawString(minecraft.font, ":", x + 1, y, fontOutlineColor, false);
        guiGraphics.drawString(minecraft.font, ":", x - 1, y, fontOutlineColor, false);
        guiGraphics.drawString(minecraft.font, ":", x, y + 1, fontOutlineColor, false);
        guiGraphics.drawString(minecraft.font, ":", x, y - 1, fontOutlineColor, false);
        guiGraphics.drawString(minecraft.font, ":", x, y, fontColor, false);
    }
}
