package net.endgineer.curseoftheabyss.util.creativemd.enhancedvisuals.client.render;

import java.util.Collection;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexSorting;

import org.joml.Matrix4f;

import net.endgineer.curseoftheabyss.util.creativemd.enhancedvisuals.api.Visual;
import net.endgineer.curseoftheabyss.util.creativemd.enhancedvisuals.api.type.VisualType;
import net.endgineer.curseoftheabyss.util.creativemd.enhancedvisuals.client.VisualManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.DeathScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class EVRenderer {
    private static Minecraft mc = Minecraft.getInstance();
    
    private static int framebufferWidth;
    private static int framebufferHeight;
    
    public static boolean reloadResources = false;
    
    public static void render() {
        if(reloadResources) {
            for(VisualType type : VisualType.getTypes()) {
                type.loadResources(mc.getResourceManager());
            }

            reloadResources = false;
        }
        
        if(!(mc.screen instanceof DeathScreen) && mc.level != null && !mc.player.isCreative() && !mc.player.isSpectator()) {
            float partialTicks = Minecraft.getInstance().getFrameTime();
            
            if(mc.getMainRenderTarget().width != framebufferWidth || mc.getMainRenderTarget().height != framebufferHeight) {
                for(VisualType type : VisualType.getTypes()) {
                    type.resize(mc.getMainRenderTarget());
                }

                framebufferWidth = mc.getMainRenderTarget().width;
                framebufferHeight = mc.getMainRenderTarget().height;
            }
            
            int screenWidth = mc.getWindow().getWidth();
            int screenHeight = mc.getWindow().getHeight();
            
            TextureManager manager = mc.getTextureManager();
            
            RenderSystem.clear(256, Minecraft.ON_OSX);
            Matrix4f matrix4f = new Matrix4f().setOrtho(0.0F, screenWidth, screenHeight, 0.0F, 1000.0F, ForgeHooksClient.getGuiFarPlane());
            RenderSystem.setProjectionMatrix(matrix4f, VertexSorting.ORTHOGRAPHIC_Z);
            PoseStack stack = RenderSystem.getModelViewStack();
            stack.pushPose();
            stack.setIdentity();
            stack.translate(0.0D, 0.0D, 1000F - ForgeHooksClient.getGuiFarPlane());
            RenderSystem.applyModelViewMatrix();
            Lighting.setupFor3DItems();
            
            RenderSystem.disableDepthTest();
            RenderSystem.depthMask(false);
            RenderSystem.enableBlend();
            RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.ZERO, GlStateManager.DestFactor.ONE_MINUS_SRC_COLOR, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1F);
            RenderSystem.setShader(GameRenderer::getPositionColorShader);
            Matrix4f pose = new PoseStack().last().pose();
            BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();
            bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
            int color = -16777216;
            int z = -90;
            
            bufferbuilder.vertex(pose, screenWidth, screenHeight, z).color(color).endVertex();
            bufferbuilder.vertex(pose, screenWidth, 0, z).color(color).endVertex();
            bufferbuilder.vertex(pose, 0, 0, z).color(color).endVertex();
            bufferbuilder.vertex(pose, 0, screenHeight, z).color(color).endVertex();
            BufferUploader.drawWithShader(bufferbuilder.end());
            
            RenderSystem.disableBlend();
            RenderSystem.resetTextureMatrix();
            RenderSystem.disableDepthTest();
            renderVisuals(stack, VisualManager.visuals(), manager, screenWidth, screenHeight, partialTicks);

            RenderSystem.applyModelViewMatrix();
                
            Window window = mc.getWindow();
            RenderSystem.clear(256, Minecraft.ON_OSX);
            RenderSystem.setProjectionMatrix(new Matrix4f().setOrtho(0.0F, (float) (window.getWidth() / window.getGuiScale()), (float) (window.getHeight() / window.getGuiScale()), 0.0F, 1000.0F, ForgeHooksClient.getGuiFarPlane()), VertexSorting.ORTHOGRAPHIC_Z);
            stack.popPose();
            RenderSystem.applyModelViewMatrix();
            Lighting.setupFor3DItems();
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            mc.getMainRenderTarget().bindWrite(true);
        }
    }

private static void renderVisuals(PoseStack stack, Collection<Visual> visuals, TextureManager manager, int screenWidth, int screenHeight, float partialTicks) {
        if(visuals == null || visuals.isEmpty()) return;
        
        try {
            for(Visual visual : visuals) {
                if(visual.isVisible()) {
                    visual.render(stack, manager, screenWidth, screenHeight, partialTicks);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
