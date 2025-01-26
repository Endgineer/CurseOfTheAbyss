package net.endgineer.curseoftheabyss.util.creativemd.enhancedvisuals.api.type;

import java.io.IOException;
import com.google.gson.JsonSyntaxException;
import com.mojang.blaze3d.pipeline.RenderTarget;

import net.endgineer.curseoftheabyss.util.creativemd.enhancedvisuals.api.Visual;
import net.endgineer.curseoftheabyss.util.creativemd.enhancedvisuals.api.VisualHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.PostChain;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public abstract class VisualTypeShader extends VisualType {
    public ResourceLocation location;
    
    public VisualTypeShader(String name, ResourceLocation location) {
        super(name);
        this.location = location;
    }
    
    public Object postChain;
    
    @Override
    @OnlyIn(Dist.CLIENT)
    public void loadResources(ResourceManager manager) {
        Minecraft mc = Minecraft.getInstance();
        if(postChain != null) {
            ((PostChain) postChain).close();
        }
        
        try {
            if(mc.isSameThread()) {
                postChain = new PostChain(mc.getTextureManager(), mc.getResourceManager(), mc.getMainRenderTarget(), location);
                ((PostChain) postChain).resize(mc.getWindow().getWidth(), mc.getWindow().getHeight());
            }
        } catch (JsonSyntaxException | IOException e) {}
    }
    
    @Override
    @OnlyIn(Dist.CLIENT)
    public int getVariantAmount() {
        return 0;
    }
    
    @Override
    @OnlyIn(Dist.CLIENT)
    public void resize(RenderTarget buffer) {
        if(postChain != null) {
            ((PostChain) postChain).resize(Minecraft.getInstance().getWindow().getWidth(), Minecraft.getInstance().getWindow().getHeight());
        }
    }
    
    @Override
    @OnlyIn(Dist.CLIENT)
    public void render(VisualHandler handler, Visual visual, TextureManager manager, int screenWidth, int screenHeight, float partialTicks) {
        if(postChain == null) {
            loadResources(Minecraft.getInstance().getResourceManager());
        }

        if(postChain != null) {
            changeProperties(visual.getOpacity());
            ((PostChain) postChain).process(partialTicks);
        }
    }
    
    @OnlyIn(Dist.CLIENT)
    public abstract void changeProperties(float intensity);
}
