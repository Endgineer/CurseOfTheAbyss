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
        boolean overworld = level.dimension().location().getPath() == "overworld";
        
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

        this.constitution = Math.max(0, this.constitution - this.strains.observeHollowing(true));

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
