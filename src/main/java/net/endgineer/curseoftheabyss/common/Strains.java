package net.endgineer.curseoftheabyss.common;

import java.io.Serializable;

import net.endgineer.curseoftheabyss.core.ModVariables;
import net.endgineer.curseoftheabyss.network.PacketHandler;
import net.endgineer.curseoftheabyss.network.StrainsPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.PacketDistributor;

public class Strains implements Serializable {
    private static double[] log_normal_distribution = {
        0.010651099117787,
        0.014002205350036,
        0.018788392590152,
        0.025832766580701,
        0.036585751693173,
        0.053760310227836,
        0.082828518777967,
        0.135968607417927,
        0.244108595562770,
        0.499999999779401,
        0.999999999776070,
    };

    private double strain_hollowing;
    private double[] buffer_hollowing;

    private double strain_deformation;
    private double[] buffer_deformation;

    private double strain_deprivation;
    private double[] buffer_deprivation;

    private double strain_exhaustion;
    private double[] buffer_exhaustion;

    private double strain_hallucination;
    private double[] buffer_hallucination;

    private double strain_numbness;
    private double[] buffer_numbness;

    private double stress;
    private int buffer_tick;
    private int stress_tick;

    private double progress_deprivation;

    private double progress_numbness;
    private int target_numbness_width;
    private int trailing_numbness_width;

    public Strains() {
        this.strain_hollowing = 0;
        this.buffer_hollowing = new double[11];

        this.strain_deformation = 0;
        this.buffer_deformation = new double[11];

        this.strain_deprivation = 0;
        this.buffer_deprivation = new double[11];

        this.strain_exhaustion = 0;
        this.buffer_exhaustion = new double[11];

        this.strain_hallucination = 0;
        this.buffer_hallucination = new double[11];

        this.strain_numbness = 0;
        this.buffer_numbness = new double[11];

        this.stress = 0;
        this.buffer_tick = 0;
        this.stress_tick = 0;
    }

    public double observeHollowing(boolean alter) {
        double temp = this.strain_hollowing;
        if(alter) { this.strain_hollowing = 0; }
        return temp;
    }

    public double observeDeformation(boolean alter) {
        double temp = this.strain_deformation;
        if(alter) { this.strain_deformation = 0; }
        return temp;
    }

    public double observeDeprivation(boolean alter) {
        double temp = this.strain_deprivation;
        if(alter) { this.strain_deprivation -= Math.min(this.strain_deprivation, 0.05); }
        return temp;
    }

    public double observeExhaustion(boolean alter) {
        double temp = this.strain_exhaustion;
        if(alter) { this.strain_exhaustion = 0; }
        return temp;
    }

    public double observeHallucination(boolean alter) {
        double temp = this.strain_hallucination;
        if(alter) { this.strain_hallucination = 0; }
        return temp;
    }

    public double observeNumbness(boolean alter) {
        double temp = this.strain_numbness;
        if(alter) { this.strain_numbness -= Math.min(this.strain_numbness, 0.05); }
        return temp;
    }

    public void tick(double stress, double depth, double field) {
        this.stress += stress;
        this.stress_tick++;

        int buffer_second = (int) Math.ceil(this.buffer_tick / 20.0);

        if(this.stress_tick == 20) {
            for(int second = 0; second < 11; second++) {
                int buffer_step = (buffer_second + second) % 11;
                double convolved_stress = Strains.log_normal_distribution[second] * this.stress;
                double deformation = convolved_stress * Abyss.strain_deformation(field, depth);

                this.buffer_hollowing[buffer_step] += Abyss.layer(depth) > ModVariables.DEFORMATION.YIELD_LAYER ? deformation : 0;
                this.buffer_deformation[buffer_step] += deformation;
                this.buffer_deprivation[buffer_step] += convolved_stress * Abyss.strain_deprivation(field, depth);
                this.buffer_exhaustion[buffer_step] += convolved_stress * Abyss.strain_exhaustion(field, depth);
                this.buffer_hallucination[buffer_step] += convolved_stress * Abyss.strain_hallucination(field, depth);
                this.buffer_numbness[buffer_step] += convolved_stress * Abyss.strain_numbness(field, depth);
            }

            this.stress = 0;
            this.stress_tick = 0;
        }

        if(this.buffer_tick % 20 == 0) {
            this.strain_hollowing += this.buffer_hollowing[buffer_second];
            this.buffer_hollowing[buffer_second] = 0;
            this.strain_deformation += this.buffer_deformation[buffer_second];
            this.buffer_deformation[buffer_second] = 0;
            this.strain_deprivation += this.buffer_deprivation[buffer_second];
            this.buffer_deprivation[buffer_second] = 0;
            this.strain_exhaustion += this.buffer_exhaustion[buffer_second];
            this.buffer_exhaustion[buffer_second] = 0;
            this.strain_hallucination += this.buffer_hallucination[buffer_second];
            this.buffer_hallucination[buffer_second] = 0;
            this.strain_numbness += this.buffer_numbness[buffer_second];
            this.buffer_numbness[buffer_second] = 0;
        }

        if(this.strain_numbness > 0 && Math.random() < 0.01) {
            this.target_numbness_width++;
        }

        if(this.progress_numbness == this.target_numbness_width) {
            this.trailing_numbness_width = 0;
            this.target_numbness_width = 0;
            this.progress_numbness = 0;
        } else if(this.target_numbness_width > 0) {
            this.progress_numbness = Math.min(this.progress_numbness + 0.05, this.target_numbness_width);
        }

        this.progress_deprivation = this.strain_deprivation > 0 ? Math.min(this.progress_deprivation + 0.05, 1) : Math.max(0, this.progress_deprivation - 0.05);

        this.buffer_tick = ++this.buffer_tick % 220;
    }

    public void sync(Player player) {
        PacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) player), new StrainsPacket(this.numbness_signal(), this.progress_deprivation));
    }

    public boolean empty() {
        for(int i = 0; i < 11; i++) {
            if(this.buffer_hollowing[i] > 0) { return false; }
            if(this.buffer_deformation[i] > 0) { return false; }
            if(this.buffer_deprivation[i] > 0) { return false; }
            if(this.buffer_exhaustion[i] > 0) { return false; }
            if(this.buffer_hallucination[i] > 0) { return false; }
            if(this.buffer_numbness[i] > 0) { return false; }
        }

        if(this.strain_hollowing > 0) { return false; }
        if(this.strain_deformation > 0) { return false; }
        if(this.strain_deprivation > 0) { return false; }
        if(this.strain_exhaustion > 0) { return false; }
        if(this.strain_hallucination > 0) { return false; }
        if(this.strain_numbness > 0) { return false; }

        if(this.progress_deprivation > 0) { return false; }
        if(this.numbness_signal() > 0) { return false; }

        return true;
    }

    private double numbness_signal() {
        if(this.target_numbness_width == 0 || this.progress_numbness < 0 || this.progress_numbness > this.target_numbness_width) {
            return 0;
        } else if(this.trailing_numbness_width-this.progress_numbness <= 0.5 && this.target_numbness_width > this.trailing_numbness_width) {
            if(this.progress_numbness < 0.5) {
                return 2*this.progress_numbness;
            } else {
                this.trailing_numbness_width = this.target_numbness_width;
            }
        }

        return this.progress_numbness <= 0.5 ? 2*this.progress_numbness : this.progress_numbness <= 0.5+this.target_numbness_width-1 ? 1 : 2*(this.target_numbness_width-this.progress_numbness);
    }
}
