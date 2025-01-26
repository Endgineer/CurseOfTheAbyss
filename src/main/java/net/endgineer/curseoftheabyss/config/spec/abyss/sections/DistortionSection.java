package net.endgineer.curseoftheabyss.config.spec.abyss.sections;

import net.minecraftforge.common.ForgeConfigSpec;

public class DistortionSection {
    public final double DEFAULT_LAYER1 = 1.0 / 2304000;
    public final double MINIMUM_LAYER1 = 0;
    public final double MAXIMUM_LAYER1 = 1.0 / 20;
    public final ForgeConfigSpec.ConfigValue<Double> LAYER1;

    public final double DEFAULT_LAYER2 = 2.0 / 2304000;
    public final double MINIMUM_LAYER2 = 0;
    public final double MAXIMUM_LAYER2 = 1.0 / 20;
    public final ForgeConfigSpec.ConfigValue<Double> LAYER2;

    public final double DEFAULT_LAYER3 = 3.0 / 2304000;
    public final double MINIMUM_LAYER3 = 0;
    public final double MAXIMUM_LAYER3 = 1.0 / 20;
    public final ForgeConfigSpec.ConfigValue<Double> LAYER3;

    public final double DEFAULT_LAYER4 = 5.0 / 2304000;
    public final double MINIMUM_LAYER4 = 0;
    public final double MAXIMUM_LAYER4 = 1.0 / 20;
    public final ForgeConfigSpec.ConfigValue<Double> LAYER4;

    public final double DEFAULT_LAYER5 = 8.0 / 2304000;
    public final double MINIMUM_LAYER5 = 0;
    public final double MAXIMUM_LAYER5 = 1.0 / 20;
    public final ForgeConfigSpec.ConfigValue<Double> LAYER5;

    public final double DEFAULT_LAYER6 = 13.0 / 2304000;
    public final double MINIMUM_LAYER6 = 0;
    public final double MAXIMUM_LAYER6 = 1.0 / 20;
    public final ForgeConfigSpec.ConfigValue<Double> LAYER6;

    public final double DEFAULT_LAYER7 = 21.0 / 2304000;
    public final double MINIMUM_LAYER7 = 0;
    public final double MAXIMUM_LAYER7 = 1.0 / 20;
    public final ForgeConfigSpec.ConfigValue<Double> LAYER7;

    public DistortionSection(ForgeConfigSpec.Builder builder) {
        builder.push("DISTORTION");

        LAYER1 = builder.comment(
            "The distortion applied to the player's mind every tick in the 1st layer of the Abyss. When a player's mind is 100% distorted, the player becomes fully insane.\n"+
            "Values: [ "+MINIMUM_LAYER1+", "+MAXIMUM_LAYER1+" ]\n"+
            "Default: "+DEFAULT_LAYER1)
            .define("LAYER1", DEFAULT_LAYER1, value -> value != null && (Double) value >= MINIMUM_LAYER1 && (Double) value <= MAXIMUM_LAYER1);
        
        LAYER2 = builder.comment(
            "The distortion applied to the player's mind every tick in the 2nd layer of the Abyss. When a player's mind is 100% distorted, the player becomes fully insane.\n"+
            "Values: [ "+MINIMUM_LAYER2+", "+MAXIMUM_LAYER2+" ]\n"+
            "Default: "+DEFAULT_LAYER2)
            .define("LAYER2", DEFAULT_LAYER2, value -> value != null && (Double) value >= MINIMUM_LAYER2 && (Double) value <= MAXIMUM_LAYER2);
        
        LAYER3 = builder.comment(
            "The distortion applied to the player's mind every tick in the 3rd layer of the Abyss. When a player's mind is 100% distorted, the player becomes fully insane.\n"+
            "Values: [ "+MINIMUM_LAYER3+", "+MAXIMUM_LAYER3+" ]\n"+
            "Default: "+DEFAULT_LAYER3)
            .define("LAYER3", DEFAULT_LAYER3, value -> value != null && (Double) value >= MINIMUM_LAYER3 && (Double) value <= MAXIMUM_LAYER3);
        
        LAYER4 = builder.comment(
            "The distortion applied to the player's mind every tick in the 4th layer of the Abyss. When a player's mind is 100% distorted, the player becomes fully insane.\n"+
            "Values: [ "+MINIMUM_LAYER4+", "+MAXIMUM_LAYER4+" ]\n"+
            "Default: "+DEFAULT_LAYER4)
            .define("LAYER4", DEFAULT_LAYER4, value -> value != null && (Double) value >= MINIMUM_LAYER4 && (Double) value <= MAXIMUM_LAYER4);
        
        LAYER5 = builder.comment(
            "The distortion applied to the player's mind every tick in the 5th layer of the Abyss. When a player's mind is 100% distorted, the player becomes fully insane.\n"+
            "Values: [ "+MINIMUM_LAYER5+", "+MAXIMUM_LAYER5+" ]\n"+
            "Default: "+DEFAULT_LAYER5)
            .define("LAYER5", DEFAULT_LAYER5, value -> value != null && (Double) value >= MINIMUM_LAYER5 && (Double) value <= MAXIMUM_LAYER5);
        
        LAYER6 = builder.comment(
            "The distortion applied to the player's mind every tick in the 6th layer of the Abyss. When a player's mind is 100% distorted, the player becomes fully insane.\n"+
            "Values: [ "+MINIMUM_LAYER6+", "+MAXIMUM_LAYER6+" ]\n"+
            "Default: "+DEFAULT_LAYER6)
            .define("LAYER6", DEFAULT_LAYER6, value -> value != null && (Double) value >= MINIMUM_LAYER6 && (Double) value <= MAXIMUM_LAYER6);
        
        LAYER7 = builder.comment(
            "The distortion applied to the player's mind every tick in the 7th layer of the Abyss. When a player's mind is 100% distorted, the player becomes fully insane.\n"+
            "Values: [ "+MINIMUM_LAYER7+", "+MAXIMUM_LAYER7+" ]\n"+
            "Default: "+DEFAULT_LAYER7)
            .define("LAYER7", DEFAULT_LAYER7, value -> value != null && (Double) value >= MINIMUM_LAYER7 && (Double) value <= MAXIMUM_LAYER7);

        builder.pop();
    }
}
