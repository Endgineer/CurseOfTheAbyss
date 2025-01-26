package net.endgineer.curseoftheabyss.util.creativemd.enhancedvisuals.api.type;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import com.mojang.blaze3d.pipeline.RenderTarget;

import net.endgineer.curseoftheabyss.util.creativemd.enhancedvisuals.api.Visual;
import net.endgineer.curseoftheabyss.util.creativemd.enhancedvisuals.api.VisualHandler;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public abstract class VisualType {
    private static List<VisualType> types = new ArrayList<>();
    
    public static Collection<VisualType> getTypes() {
        return types;
    }
    
    public boolean disabled = false;
    
    public float opacity = 1;
    
    public final String name;
    
    public VisualType(String name) {
        this.name = name;
        
        types.add(this);
    }
    
    @OnlyIn(Dist.CLIENT)
    public abstract void loadResources(ResourceManager manager);
    
    @OnlyIn(Dist.CLIENT)
    public abstract void render(VisualHandler handler, Visual visual, TextureManager manager, int screenWidth, int screenHeight, float partialTicks);
    
    @OnlyIn(Dist.CLIENT)
    public int getVariantAmount() {
        return 1;
    }
    
    @OnlyIn(Dist.CLIENT)
    public void resize(RenderTarget buffer) {}
    
    public boolean canRotate() {
        return true;
    }
    
    public boolean isVisible(VisualHandler handler, Visual visual) {
        return visual.getOpacity() > 0;
    }
    
    public boolean scaleVariants() {
        return false;
    }
    
    public double randomScale(Random rand) {
        return 1;
    }
    
    public int getWidth(int screenWidth, int screenHeight) {
        return screenWidth;
    }
    
    public int getHeight(int screenWidth, int screenHeight) {
        return screenHeight;
    }
}
