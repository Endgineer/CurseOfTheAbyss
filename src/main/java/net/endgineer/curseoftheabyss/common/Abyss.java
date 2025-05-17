package net.endgineer.curseoftheabyss.common;

import net.endgineer.curseoftheabyss.CurseOfTheAbyss;
import net.endgineer.curseoftheabyss.core.ModVariables;
import net.endgineer.curseoftheabyss.util.kdotjpg.OpenSimplex2.OpenSimplex2S;
import net.minecraft.core.Holder;
import net.minecraft.world.damagesource.DamageEffects;
import net.minecraft.world.damagesource.DamageScaling;
import net.minecraft.world.damagesource.DamageType;

public class Abyss {
    public static final Holder<DamageType> CURSE_DAMAGE = Holder.direct(
        new DamageType(CurseOfTheAbyss.MODID+"_cursed", DamageScaling.NEVER, 0.0F, DamageEffects.HURT)
    );

    public static int layer(double y) {
        return (int) Math.ceil(7 * Abyss.pressure(y));
    }

    private static double pressure(double y) {
        return Math.min(Math.max(0, -y * ModVariables.ABYSS.INVERSE_SPAN), 1);
    }

    public static double boundary(int layer) {
        return -ModVariables.ABYSS.LEVEL_DEPTH * layer;
    }

    public static double distortion(double field, double y) {
        return field * ModVariables.DISTORTION.LAYER[Abyss.layer(y)];
    }

    public static double strain_deformation(double field, double y) {
        return Abyss.layer(y) <= ModVariables.DEFORMATION.DEFIANCE_LAYER ? 0 : field * (- (y - Abyss.boundary(ModVariables.DEFORMATION.DEFIANCE_LAYER)) / (ModVariables.DEFORMATION.ELASTICITY_MODULUS * ModVariables.ABYSS.SPAN) + Math.pow((y - Abyss.boundary(ModVariables.DEFORMATION.DEFIANCE_LAYER)) / Abyss.boundary(ModVariables.DEFORMATION.YIELD_LAYER - ModVariables.DEFORMATION.DEFIANCE_LAYER), 1 / ModVariables.DEFORMATION.STRAIN_HARDENING_COEFFICIENT));
    }

    public static double strain_deprivation(double field, double y) {
        return field * ((-y % -Abyss.boundary(1)) / (-Abyss.boundary(1)) * (ModVariables.DEPRIVATION.LAYER[Abyss.layer(y)][1] - ModVariables.DEPRIVATION.LAYER[Abyss.layer(y)][0]) + ModVariables.DEPRIVATION.LAYER[Abyss.layer(y)][0]);
    }

    public static double strain_hallucination(double field, double y) {
        return field * ((-y % -Abyss.boundary(1)) / (-Abyss.boundary(1)) * (ModVariables.HALLUCINATION.LAYER[Abyss.layer(y)][1] - ModVariables.HALLUCINATION.LAYER[Abyss.layer(y)][0]) + ModVariables.HALLUCINATION.LAYER[Abyss.layer(y)][0]);
    }

    public static double strain_numbness(double field, double y) {
        return field * ((-y % -Abyss.boundary(1)) / (-Abyss.boundary(1)) * (ModVariables.NUMBNESS.LAYER[Abyss.layer(y)][1] - ModVariables.NUMBNESS.LAYER[Abyss.layer(y)][0]) + ModVariables.NUMBNESS.LAYER[Abyss.layer(y)][0]);
    }

    public static double strain_exhaustion(double field, double y) {
        return field * ((-y % -Abyss.boundary(1)) / (-Abyss.boundary(1)) * (ModVariables.EXHAUSTION.LAYER[Abyss.layer(y)][1] - ModVariables.EXHAUSTION.LAYER[Abyss.layer(y)][0]) + ModVariables.EXHAUSTION.LAYER[Abyss.layer(y)][0]);
    }

    public static double loss(double sender_y, double receiver_y) {
        double integrity = 1;

        int receiver_layer = Abyss.layer(receiver_y);
        int sender_layer = Abyss.layer(sender_y);

        int lower_layer = Math.min(sender_layer, receiver_layer), upper_layer = Math.max(sender_layer, receiver_layer);

        for(int i = lower_layer; i < upper_layer; i++) {
            double propagation = 0;

            switch(i) {
                case 0:
                    propagation = 1.00;
                    break;
                case 1:
                    propagation = 0.97;
                    break;
                case 2:
                    propagation = 0.94;
                    break;
                case 3:
                    propagation = 0.88;
                    break;
                case 4:
                    propagation = 0.76;
                    break;
                case 5:
                    propagation = 0.52;
                    break;
                case 6:
                    propagation = 0.04;
                    break;
            }
            
            if(Math.random() > propagation) {
                return 1;
            } else {
                integrity *= propagation;
            }
        }

        return 1-integrity;
    }

    public static double moon_presence(long daytime) {
        double moon_brightness = Math.max(0, ModVariables.FIELD.MOON_BRIGHTNESS_GAIN * Math.abs((daytime - 6000) * ModVariables.FIELD.TICK_IN_DAYS - Math.floor((daytime + 6000) * ModVariables.FIELD.TICK_IN_DAYS)) + ModVariables.FIELD.MOON_BRIGHTNESS_BIAS);
        double moon_phase = 2 * Math.abs((daytime - 114000) * ModVariables.FIELD.TICK_IN_MONTHS - Math.floor((daytime - 18000) * ModVariables.FIELD.TICK_IN_MONTHS));
        return moon_brightness * moon_phase;
    }

    public static double column_depth(double y) {
        return 1.0D / (1.0D + Math.exp(ModVariables.FIELD.DEPTH_SIGMOID_TEMPERATURE * (64 * y * ModVariables.ABYSS.INVERSE_SPAN + ModVariables.FIELD.DEPTH_SIGMOID_BIAS)));
    }

    public static double expected_field(double y) {
        final double moon_absence = 425.0/480.0;
        final double field = 0.522556 / (1 + ModVariables.FIELD.HARMONIC_MAXFIELD * moon_absence);
        final double background_gradient = Abyss.pressure(y);
        final double field_density = Math.min(background_gradient + field * (1 - background_gradient), 1);
        final double field_column = Abyss.column_depth(y);
        
        return field_density * field_column;
    }

    public static double field(long seed, double x, double y, double z, long gametime, long daytime) {
        double field = 0;
        
        for(int octave = 0; octave < 7; octave++) {
            field += Math.abs(
                OpenSimplex2S.noise3_ImproveXZ(
                    seed,
                    x * ModVariables.FIELD.XZ_FREQUENCIES[octave],
                    y * ModVariables.FIELD.Y_FREQUENCIES[octave] - gametime * ModVariables.FIELD.T_FREQUENCY,
                    z * ModVariables.FIELD.XZ_FREQUENCIES[octave]
                )
            ) * ModVariables.FIELD.HARMONIC_AMPLITUDES[octave];
        }

        double moon_absence = 1 - Abyss.moon_presence(daytime);
        field /= (1 + ModVariables.FIELD.HARMONIC_MAXFIELD * moon_absence);
        
        double background_gradient = Abyss.pressure(y);
        double field_density = Math.min(background_gradient + field * (1 - background_gradient), 1);
        double field_column = Abyss.column_depth(y);
        
        return field_density * field_column;
    }
}
