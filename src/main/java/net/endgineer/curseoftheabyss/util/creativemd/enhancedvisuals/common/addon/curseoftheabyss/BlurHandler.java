package net.endgineer.curseoftheabyss.util.creativemd.enhancedvisuals.common.addon.curseoftheabyss;

import javax.annotation.Nullable;
import com.mojang.blaze3d.shaders.Uniform;

import net.endgineer.curseoftheabyss.client.StrainsData;
import net.endgineer.curseoftheabyss.util.creativemd.enhancedvisuals.api.Visual;
import net.endgineer.curseoftheabyss.util.creativemd.enhancedvisuals.api.VisualHandler;
import net.endgineer.curseoftheabyss.util.creativemd.enhancedvisuals.api.type.VisualType;
import net.endgineer.curseoftheabyss.util.creativemd.enhancedvisuals.api.type.VisualTypeShader;
import net.endgineer.curseoftheabyss.util.creativemd.enhancedvisuals.client.VisualManager;
import net.endgineer.curseoftheabyss.mixin.PostChainAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.PostPass;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BlurHandler extends VisualHandler {
    private double fade = 0.05;
    private double intensity = 1;

    public VisualType focus = new VisualTypeShader("focus", new ResourceLocation("shaders/post/fade_in_blur.json")) {
        @Override
        @OnlyIn(Dist.CLIENT)
        public void changeProperties(float intensity) {
            for(PostPass pass : ((PostChainAccessor) postChain).getPasses()) {
                Uniform shadeUniform = pass.getEffect().getUniform("Progress");

                if(shadeUniform != null) {
                    shadeUniform.set(intensity);
                }
            }
        }
    };

    public Visual focusVisual;

    @Override
    public void tick(@Nullable Player player) {
        if(focusVisual == null) {
            focusVisual = new Visual(focus, this, 0);
            VisualManager.add(focusVisual);
        }

        double saturation = 0;
        Minecraft mc = Minecraft.getInstance();
        if(player != null && player.isAlive() && !mc.isPaused()) {
            saturation = StrainsData.getNumbnessProgress() * intensity;

            if(focusVisual.getOpacityInternal() < saturation) {
                focusVisual.setOpacityInternal((float) Math.min(focusVisual.getOpacityInternal() + fade, saturation));
            } else if(focusVisual.getOpacityInternal() > saturation) {
                focusVisual.setOpacityInternal((float) Math.min(focusVisual.getOpacityInternal() - fade, saturation));
            }
        } else {
            focusVisual.setOpacityInternal(0);
        }
    }
}
