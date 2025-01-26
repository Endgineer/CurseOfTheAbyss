package net.endgineer.curseoftheabyss.config.spec.strains.sections;

import net.minecraftforge.common.ForgeConfigSpec;
import java.util.regex.Pattern;

public class DeformationSection {
    public final String DEFAULT_BOUNDARY_LAYERS = "[3, 5]";
    public final int MINIMUM_BOUNDARY_LAYER = 0;
    public final int MAXIMUM_BOUNDARY_LAYER = 7;
    public final ForgeConfigSpec.ConfigValue<String> BOUNDARY_LAYERS;

    public final double DEFAULT_ELASTICITY_MODULUS = 0.07936;
    public final double MINIMUM_ELASTICITY_MODULUS = 0;
    public final ForgeConfigSpec.ConfigValue<Double> ELASTICITY_MODULUS;

    public final double DEFAULT_STRAIN_HARDENING_COEFFICIENT = 0.04121;
    public final double MINIMUM_STRAIN_HARDENING_COEFFICIENT = 0;
    public final ForgeConfigSpec.ConfigValue<Double> STRAIN_HARDENING_COEFFICIENT;

    public DeformationSection(ForgeConfigSpec.Builder builder) {
        builder.push("DEFORMATION");

        BOUNDARY_LAYERS = builder.comment(
            "Layers that mark important boundaries of the Abyss. The first marks the point beyond which the curse results in bodily harm. The second marks the Absolute Boundary beyond which the curse results in loss of humanity.\n"+
            "Values: {\"[m, M]\" | "+MINIMUM_BOUNDARY_LAYER+" <= m < M <= "+MAXIMUM_BOUNDARY_LAYER+"}\n"+
            "Default: \""+DEFAULT_BOUNDARY_LAYERS+"\"")
            .define("BOUNDARY_LAYERS", DEFAULT_BOUNDARY_LAYERS, value -> value != null && Pattern.matches("\\[\\s*["+MINIMUM_BOUNDARY_LAYER+"-"+MAXIMUM_BOUNDARY_LAYER+"],\\s*["+MINIMUM_BOUNDARY_LAYER+"-"+MAXIMUM_BOUNDARY_LAYER+"]\\s*\\]", (String) value) && Integer.parseInt(((String) value).replaceAll("\\s|\\[|\\]", "").split(",")[0]) >= MINIMUM_BOUNDARY_LAYER && Integer.parseInt(((String) value).replaceAll("\\s|\\[|\\]", "").split(",")[1]) <= MAXIMUM_BOUNDARY_LAYER && Integer.parseInt(((String) value).replaceAll("\\s|\\[|\\]", "").split(",")[0]) < Integer.parseInt(((String) value).replaceAll("\\s|\\[|\\]", "").split(",")[1]));

        ELASTICITY_MODULUS = builder.comment(
            "The player's resistance to the linear portion of the curse. The smaller this value, the more damage the player takes from the curse.\n"+
            "Values: ( "+MINIMUM_ELASTICITY_MODULUS+", "+Double.MAX_VALUE+" ]\n"+
            "Default: "+DEFAULT_ELASTICITY_MODULUS)
            .define("ELASTICITY_MODULUS", DEFAULT_ELASTICITY_MODULUS, value -> value != null && (Double) value > MINIMUM_ELASTICITY_MODULUS);
        
        STRAIN_HARDENING_COEFFICIENT = builder.comment(
            "The player's resistance to the exponential portion of the curse. The smaller this value, the more damage the player takes from the curse.\n"+
            "Values: ( "+MINIMUM_STRAIN_HARDENING_COEFFICIENT+", "+Double.MAX_VALUE+" ]\n"+
            "Default: "+DEFAULT_STRAIN_HARDENING_COEFFICIENT)
            .define("STRAIN_HARDENING_COEFFICIENT", DEFAULT_STRAIN_HARDENING_COEFFICIENT, value -> value != null && (Double) value > MINIMUM_STRAIN_HARDENING_COEFFICIENT);

        builder.pop();
    }
}
