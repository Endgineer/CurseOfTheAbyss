package net.endgineer.curseoftheabyss.util.creativemd.enhancedvisuals.client;

import java.util.Collection;
import java.util.List;
import java.util.ArrayList;

import javax.annotation.Nullable;

import net.endgineer.curseoftheabyss.util.creativemd.enhancedvisuals.api.Visual;
import net.endgineer.curseoftheabyss.util.creativemd.enhancedvisuals.api.VisualHandler;
import net.endgineer.curseoftheabyss.util.creativemd.enhancedvisuals.common.visual.VisualRegistry;
import net.minecraft.world.entity.player.Player;

public class VisualManager {
    private static List<Visual> shaders = new ArrayList<Visual>();
    
    public static void onTick(@Nullable Player player) {
        synchronized (shaders) {
            for(VisualHandler handler : VisualRegistry.handlers()) {
                handler.tick(player);
            }
        }
    }
    
    public static Collection<Visual> visuals() {
        return shaders;
    }
    
    public static void add(Visual visual) {
        if(!visual.type.disabled) {
            visual.addToDisplay();
            shaders.add(visual);
        }
    }
}
