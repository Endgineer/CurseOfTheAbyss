package net.endgineer.curseoftheabyss.config.variables.abyss;

import net.endgineer.curseoftheabyss.core.ModConfigs;

public class FieldVariables {
    public final double MOON_BRIGHTNESS_GAIN = 48.0D / 11.0D;
    public final double MOON_BRIGHTNESS_BIAS = -13.0D / 11.0D;

    public final double TICK_IN_DAYS = 1.0D / 24000.0D;
    public final double TICK_IN_MONTHS = 1.0D / 192000.0D;

    public final double DEPTH_SIGMOID_BIAS = 64.0D / 7.0D;
    public final double DEPTH_SIGMOID_TEMPERATURE = Math.log(99.0D) / (512.0D / ModConfigs.ABYSS.ABYSS.SPAN.get() + DEPTH_SIGMOID_BIAS);

    public final double[] HARMONIC_AMPLITUDES = {
        1.0D / Math.pow(2, 6),
        1.0D / Math.pow(2, 5),
        1.0D / Math.pow(2, 4),
        1.0D / Math.pow(2, 3),
        1.0D / Math.pow(2, 2),
        1.0D / Math.pow(2, 1),
        1.0D / Math.pow(2, 0)
    };

    public final double HARMONIC_MAXFIELD = 63.0D / 64.0D;

    public final double EXPECTED_FIELD = 0.522556 / (1 + HARMONIC_MAXFIELD * 425.0/480.0);
    
    public final double[] XZ_FREQUENCIES = {
        1.0D / (ModConfigs.ABYSS.FIELD.XZ_PERIOD.get() * Math.pow(2, 0)),
        1.0D / (ModConfigs.ABYSS.FIELD.XZ_PERIOD.get() * Math.pow(2, 1)),
        1.0D / (ModConfigs.ABYSS.FIELD.XZ_PERIOD.get() * Math.pow(2, 2)),
        1.0D / (ModConfigs.ABYSS.FIELD.XZ_PERIOD.get() * Math.pow(2, 3)),
        1.0D / (ModConfigs.ABYSS.FIELD.XZ_PERIOD.get() * Math.pow(2, 4)),
        1.0D / (ModConfigs.ABYSS.FIELD.XZ_PERIOD.get() * Math.pow(2, 5)),
        1.0D / (ModConfigs.ABYSS.FIELD.XZ_PERIOD.get() * Math.pow(2, 6))
    };

    public final double[] Y_FREQUENCIES = {
        1.0D / (ModConfigs.ABYSS.FIELD.Y_PERIOD.get() * Math.pow(2, 0)),
        1.0D / (ModConfigs.ABYSS.FIELD.Y_PERIOD.get() * Math.pow(2, 1)),
        1.0D / (ModConfigs.ABYSS.FIELD.Y_PERIOD.get() * Math.pow(2, 2)),
        1.0D / (ModConfigs.ABYSS.FIELD.Y_PERIOD.get() * Math.pow(2, 3)),
        1.0D / (ModConfigs.ABYSS.FIELD.Y_PERIOD.get() * Math.pow(2, 4)),
        1.0D / (ModConfigs.ABYSS.FIELD.Y_PERIOD.get() * Math.pow(2, 5)),
        1.0D / (ModConfigs.ABYSS.FIELD.Y_PERIOD.get() * Math.pow(2, 6))
    };
    
    public final double T_FREQUENCY = 1.0D / ModConfigs.ABYSS.FIELD.T_PERIOD.get();
}
