package net.endgineer.curseoftheabyss.config.spec.abyss.sections;

import net.minecraftforge.common.ForgeConfigSpec;

public class FieldSection {
    public final int DEFAULT_XZ_PERIOD = 10;
    public final int MINIMUM_XZ_PERIOD = 1;
    public final ForgeConfigSpec.ConfigValue<Integer> XZ_PERIOD;

    public final int DEFAULT_Y_PERIOD = 10;
    public final int MINIMUM_Y_PERIOD = 1;
    public final ForgeConfigSpec.ConfigValue<Integer> Y_PERIOD;

    public final int DEFAULT_T_PERIOD = 20000;
    public final int MINIMUM_T_PERIOD = 1;
    public final ForgeConfigSpec.ConfigValue<Integer> T_PERIOD;

    public FieldSection(ForgeConfigSpec.Builder builder) {
        builder.push("FIELD");

        XZ_PERIOD = builder.comment(
            "Affects the period of the field function with respect to changes in chunk. The lower this value, the higher the field's frequency.\n"+
            "Values: [ "+MINIMUM_XZ_PERIOD+", "+Integer.MAX_VALUE+" ]\n"+
            "Default: "+DEFAULT_XZ_PERIOD)
            .define("XZ_PERIOD", DEFAULT_XZ_PERIOD);
        
        Y_PERIOD = builder.comment(
            "Affects the period of the field function with respect to a changes in depth. The lower this value, the higher the field's frequency.\n"+
            "Values: [ "+MINIMUM_Y_PERIOD+", "+Integer.MAX_VALUE+" ]\n"+
            "Default: "+DEFAULT_Y_PERIOD)
            .define("Y_PERIOD", DEFAULT_Y_PERIOD);
        
        T_PERIOD = builder.comment(
            "Affects the period of the field function with respect to changes in time. The lower this value, the higher the field's frequency.\n"+
            "Values: [ "+MINIMUM_T_PERIOD+", "+Integer.MAX_VALUE+" ]\n"+
            "Default: "+DEFAULT_T_PERIOD)
            .define("T_PERIOD", DEFAULT_T_PERIOD);
        
        builder.pop();
    }
}
