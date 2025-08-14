package net.endgineer.curseoftheabyss.common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.network.PacketDistributor;
import net.minecraft.server.level.ServerPlayer;
import net.endgineer.curseoftheabyss.core.ModConfigs;
import net.endgineer.curseoftheabyss.core.ModVariables;
import net.endgineer.curseoftheabyss.network.CursePacket;
import net.endgineer.curseoftheabyss.network.PacketHandler;
import net.minecraft.nbt.CompoundTag;

import com.google.gson.JsonObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;

public class CurseCapability implements Serializable {
    private double lowest_depth;
    private double previous_depth;
    private double derangement;
    private double constitution;
    private Strains strains;
    private JsonObject log;

    public CurseCapability() {
        this.reset();
    }

    public double getPreviousDepth() { return this.previous_depth; }

    public double getLowestDepth() { return this.lowest_depth; }

    public double getConstitution() { return this.constitution; }

    public double getDerangement() { return this.derangement; }

    public Strains getStrains() { return this.strains; }

    public void reset() {
        this.lowest_depth = 0;
        this.previous_depth = 0;
        this.derangement = 0;
        this.constitution = 0;
        this.strains = new Strains();
        this.log = null;
    }

    public void tick(Player player) {
        if(this.constitution == 0) this.constitution = player.getMaxHealth();

        double x = player.getX(), y = player.getY(), z = player.getZ();

        Level level = player.getCommandSenderWorld();
        boolean overworld = level.dimension().location().getPath().equals("overworld");
        
        long gt = level.getGameTime();
        long dt = level.getDayTime();
        double field = overworld ? Abyss.field(level.getServer().getLevel(level.dimension()).getSeed(), x, y, z, gt, dt) : 0;
        
        double current_depth = Math.min(y, 0);

        double stress = 0;

        if(overworld && current_depth < 0) {
            this.lowest_depth = Math.min(current_depth, this.lowest_depth);

            this.derangement = Math.min(this.derangement + Abyss.distortion(field, y), 1);
            
            if(current_depth - this.lowest_depth > ModVariables.ABYSS.LONGING && this.previous_depth < current_depth) {
                stress = current_depth - previous_depth;
            }

            this.previous_depth = current_depth;
        } else if(this.strains.empty() && this.lowest_depth >= Abyss.boundary(ModVariables.DEFORMATION.YIELD_LAYER)) {
            this.lowest_depth = 0;
            this.previous_depth = 0;
        }

        this.strains.tick(stress, current_depth, field);

        this.constitution = Math.max(0, this.constitution - this.strains.observeHollowing(false));
        
        sync(player, field);
        this.strains.sync(player);

        if(this.log != null) {
            JsonObject data = new JsonObject();
            data.addProperty("x", x);
            data.addProperty("y", y);
            data.addProperty("z", z);
            data.addProperty("gt", gt);
            data.addProperty("dt", dt);
            data.addProperty("f", field);
            data.addProperty("s", stress);
            data.addProperty("S1", this.strains.observeExhaustion(false));
            data.addProperty("S2", this.strains.observeNumbness(false));
            data.addProperty("S3", this.strains.observeHallucination(false));
            data.addProperty("S4", this.strains.observeDeformation(false));
            data.addProperty("S5", this.strains.observeDeprivation(false));
            data.addProperty("S6", this.strains.observeHollowing(false));
            this.log.get("data").getAsJsonArray().add(data);
        }
    }

    public void sync(Player player, double field) {
        PacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) player), new CursePacket(field));
    }

    public void save(CompoundTag data) {
        byte[] bytes = null;
        try (ByteArrayOutputStream bytesOutStream = new ByteArrayOutputStream();  ObjectOutputStream objectOutStream = new ObjectOutputStream(bytesOutStream)) {
            objectOutStream.writeObject(this);
            bytes = bytesOutStream.toByteArray();
        } catch(IOException exception) { System.out.println(exception); }
        data.putByteArray("curse", bytes);
    }

    public void load(CompoundTag data) {
        byte[] bytes = data.getByteArray("curse");
        try (ByteArrayInputStream bytesInStream = new ByteArrayInputStream(bytes); ObjectInputStream objectInStream = new ObjectInputStream(bytesInStream)) {
            CurseCapability curse = (CurseCapability) objectInStream.readObject();
            this.lowest_depth = curse.lowest_depth;
            this.previous_depth = curse.previous_depth;
            this.derangement = curse.derangement;
            this.strains = curse.strains;
            this.constitution = curse.constitution;
        } catch(IOException | ClassNotFoundException exception) { System.out.println(exception); }
    }

    public boolean startMeasuring(ServerPlayer player) {
        if(this.log != null) return false;
        
        this.log = new JsonObject();
        this.log.addProperty("seed", player.getServer().getLevel(player.getCommandSenderWorld().dimension()).getSeed());
        this.log.addProperty("player", player.getName().getString());
        this.log.addProperty("time", player.getCommandSenderWorld().getGameTime());
        JsonObject config = new JsonObject();
        config.addProperty("abyss_span", ModVariables.ABYSS.SPAN);
        config.addProperty("abyss_longing", ModVariables.ABYSS.LONGING);
        config.addProperty("field_xzperiod", ModConfigs.ABYSS.FIELD.XZ_PERIOD.get());
        config.addProperty("field_yperiod", ModConfigs.ABYSS.FIELD.Y_PERIOD.get());
        config.addProperty("field_tperiod", ModConfigs.ABYSS.FIELD.T_PERIOD.get());
        config.addProperty("deformation_defiancelayer", ModVariables.DEFORMATION.DEFIANCE_LAYER);
        config.addProperty("deformation_yieldlayer", ModVariables.DEFORMATION.YIELD_LAYER);
        config.addProperty("deformation_elasticitymodulus", ModVariables.DEFORMATION.ELASTICITY_MODULUS);
        config.addProperty("deformation_strainhardeningindex", ModVariables.DEFORMATION.STRAIN_HARDENING_COEFFICIENT);
        JsonArray exhaustion_layerbounds = new JsonArray();
        exhaustion_layerbounds.add(new JsonArray());
        exhaustion_layerbounds.add(new JsonArray());
        exhaustion_layerbounds.add(new JsonArray());
        exhaustion_layerbounds.add(new JsonArray());
        exhaustion_layerbounds.add(new JsonArray());
        exhaustion_layerbounds.add(new JsonArray());
        exhaustion_layerbounds.add(new JsonArray());
        exhaustion_layerbounds.get(0).getAsJsonArray().add(ModVariables.EXHAUSTION.LAYER[1][0]);
        exhaustion_layerbounds.get(0).getAsJsonArray().add(ModVariables.EXHAUSTION.LAYER[1][1]);
        exhaustion_layerbounds.get(1).getAsJsonArray().add(ModVariables.EXHAUSTION.LAYER[2][0]);
        exhaustion_layerbounds.get(1).getAsJsonArray().add(ModVariables.EXHAUSTION.LAYER[2][1]);
        exhaustion_layerbounds.get(2).getAsJsonArray().add(ModVariables.EXHAUSTION.LAYER[3][0]);
        exhaustion_layerbounds.get(2).getAsJsonArray().add(ModVariables.EXHAUSTION.LAYER[3][1]);
        exhaustion_layerbounds.get(3).getAsJsonArray().add(ModVariables.EXHAUSTION.LAYER[4][0]);
        exhaustion_layerbounds.get(3).getAsJsonArray().add(ModVariables.EXHAUSTION.LAYER[4][1]);
        exhaustion_layerbounds.get(4).getAsJsonArray().add(ModVariables.EXHAUSTION.LAYER[5][0]);
        exhaustion_layerbounds.get(4).getAsJsonArray().add(ModVariables.EXHAUSTION.LAYER[5][1]);
        exhaustion_layerbounds.get(5).getAsJsonArray().add(ModVariables.EXHAUSTION.LAYER[6][0]);
        exhaustion_layerbounds.get(5).getAsJsonArray().add(ModVariables.EXHAUSTION.LAYER[6][1]);
        exhaustion_layerbounds.get(6).getAsJsonArray().add(ModVariables.EXHAUSTION.LAYER[7][0]);
        exhaustion_layerbounds.get(6).getAsJsonArray().add(ModVariables.EXHAUSTION.LAYER[7][1]);
        config.add("exhaustion_layerbounds", exhaustion_layerbounds);
        JsonArray numbness_layerbounds = new JsonArray();
        numbness_layerbounds.add(new JsonArray());
        numbness_layerbounds.add(new JsonArray());
        numbness_layerbounds.add(new JsonArray());
        numbness_layerbounds.add(new JsonArray());
        numbness_layerbounds.add(new JsonArray());
        numbness_layerbounds.add(new JsonArray());
        numbness_layerbounds.add(new JsonArray());
        numbness_layerbounds.get(0).getAsJsonArray().add(ModVariables.NUMBNESS.LAYER[1][0]);
        numbness_layerbounds.get(0).getAsJsonArray().add(ModVariables.NUMBNESS.LAYER[1][1]);
        numbness_layerbounds.get(1).getAsJsonArray().add(ModVariables.NUMBNESS.LAYER[2][0]);
        numbness_layerbounds.get(1).getAsJsonArray().add(ModVariables.NUMBNESS.LAYER[2][1]);
        numbness_layerbounds.get(2).getAsJsonArray().add(ModVariables.NUMBNESS.LAYER[3][0]);
        numbness_layerbounds.get(2).getAsJsonArray().add(ModVariables.NUMBNESS.LAYER[3][1]);
        numbness_layerbounds.get(3).getAsJsonArray().add(ModVariables.NUMBNESS.LAYER[4][0]);
        numbness_layerbounds.get(3).getAsJsonArray().add(ModVariables.NUMBNESS.LAYER[4][1]);
        numbness_layerbounds.get(4).getAsJsonArray().add(ModVariables.NUMBNESS.LAYER[5][0]);
        numbness_layerbounds.get(4).getAsJsonArray().add(ModVariables.NUMBNESS.LAYER[5][1]);
        numbness_layerbounds.get(5).getAsJsonArray().add(ModVariables.NUMBNESS.LAYER[6][0]);
        numbness_layerbounds.get(5).getAsJsonArray().add(ModVariables.NUMBNESS.LAYER[6][1]);
        numbness_layerbounds.get(6).getAsJsonArray().add(ModVariables.NUMBNESS.LAYER[7][0]);
        numbness_layerbounds.get(6).getAsJsonArray().add(ModVariables.NUMBNESS.LAYER[7][1]);
        config.add("numbness_layerbounds", numbness_layerbounds);
        JsonArray hallucination_layerbounds = new JsonArray();
        hallucination_layerbounds.add(new JsonArray());
        hallucination_layerbounds.add(new JsonArray());
        hallucination_layerbounds.add(new JsonArray());
        hallucination_layerbounds.add(new JsonArray());
        hallucination_layerbounds.add(new JsonArray());
        hallucination_layerbounds.add(new JsonArray());
        hallucination_layerbounds.add(new JsonArray());
        hallucination_layerbounds.get(0).getAsJsonArray().add(ModVariables.HALLUCINATION.LAYER[1][0]);
        hallucination_layerbounds.get(0).getAsJsonArray().add(ModVariables.HALLUCINATION.LAYER[1][1]);
        hallucination_layerbounds.get(1).getAsJsonArray().add(ModVariables.HALLUCINATION.LAYER[2][0]);
        hallucination_layerbounds.get(1).getAsJsonArray().add(ModVariables.HALLUCINATION.LAYER[2][1]);
        hallucination_layerbounds.get(2).getAsJsonArray().add(ModVariables.HALLUCINATION.LAYER[3][0]);
        hallucination_layerbounds.get(2).getAsJsonArray().add(ModVariables.HALLUCINATION.LAYER[3][1]);
        hallucination_layerbounds.get(3).getAsJsonArray().add(ModVariables.HALLUCINATION.LAYER[4][0]);
        hallucination_layerbounds.get(3).getAsJsonArray().add(ModVariables.HALLUCINATION.LAYER[4][1]);
        hallucination_layerbounds.get(4).getAsJsonArray().add(ModVariables.HALLUCINATION.LAYER[5][0]);
        hallucination_layerbounds.get(4).getAsJsonArray().add(ModVariables.HALLUCINATION.LAYER[5][1]);
        hallucination_layerbounds.get(5).getAsJsonArray().add(ModVariables.HALLUCINATION.LAYER[6][0]);
        hallucination_layerbounds.get(5).getAsJsonArray().add(ModVariables.HALLUCINATION.LAYER[6][1]);
        hallucination_layerbounds.get(6).getAsJsonArray().add(ModVariables.HALLUCINATION.LAYER[7][0]);
        hallucination_layerbounds.get(6).getAsJsonArray().add(ModVariables.HALLUCINATION.LAYER[7][1]);
        config.add("hallucination_layerbounds", hallucination_layerbounds);
        JsonArray deprivation_layerbounds = new JsonArray();
        deprivation_layerbounds.add(new JsonArray());
        deprivation_layerbounds.add(new JsonArray());
        deprivation_layerbounds.add(new JsonArray());
        deprivation_layerbounds.add(new JsonArray());
        deprivation_layerbounds.add(new JsonArray());
        deprivation_layerbounds.add(new JsonArray());
        deprivation_layerbounds.add(new JsonArray());
        deprivation_layerbounds.get(0).getAsJsonArray().add(ModVariables.DEPRIVATION.LAYER[1][0]);
        deprivation_layerbounds.get(0).getAsJsonArray().add(ModVariables.DEPRIVATION.LAYER[1][1]);
        deprivation_layerbounds.get(1).getAsJsonArray().add(ModVariables.DEPRIVATION.LAYER[2][0]);
        deprivation_layerbounds.get(1).getAsJsonArray().add(ModVariables.DEPRIVATION.LAYER[2][1]);
        deprivation_layerbounds.get(2).getAsJsonArray().add(ModVariables.DEPRIVATION.LAYER[3][0]);
        deprivation_layerbounds.get(2).getAsJsonArray().add(ModVariables.DEPRIVATION.LAYER[3][1]);
        deprivation_layerbounds.get(3).getAsJsonArray().add(ModVariables.DEPRIVATION.LAYER[4][0]);
        deprivation_layerbounds.get(3).getAsJsonArray().add(ModVariables.DEPRIVATION.LAYER[4][1]);
        deprivation_layerbounds.get(4).getAsJsonArray().add(ModVariables.DEPRIVATION.LAYER[5][0]);
        deprivation_layerbounds.get(4).getAsJsonArray().add(ModVariables.DEPRIVATION.LAYER[5][1]);
        deprivation_layerbounds.get(5).getAsJsonArray().add(ModVariables.DEPRIVATION.LAYER[6][0]);
        deprivation_layerbounds.get(5).getAsJsonArray().add(ModVariables.DEPRIVATION.LAYER[6][1]);
        deprivation_layerbounds.get(6).getAsJsonArray().add(ModVariables.DEPRIVATION.LAYER[7][0]);
        deprivation_layerbounds.get(6).getAsJsonArray().add(ModVariables.DEPRIVATION.LAYER[7][1]);
        config.add("deprivation_layerbounds", deprivation_layerbounds);
        this.log.add("cfg", config);
        this.log.add("data", new JsonArray());

        return true;
    }

    public boolean stopMeasuring(ServerPlayer player) {
        if(this.log == null) return false;
        
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        
        File json_file = new File(FMLPaths.GAMEDIR.get().toFile(), "abyss_log/" + String.valueOf(this.log.get("seed").getAsLong()) + "_" + this.log.get("player").getAsString() + "_" + String.valueOf(this.log.get("time").getAsLong()) + ".json");
        if(!json_file.getParentFile().exists()) json_file.getParentFile().mkdir();

        try(FileWriter writer = new FileWriter(json_file)) {
            gson.toJson(this.log, writer);
        } catch(IOException e) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            System.out.println(sw.toString());
        }
        
        this.log = null;
        return true;
    }
}
