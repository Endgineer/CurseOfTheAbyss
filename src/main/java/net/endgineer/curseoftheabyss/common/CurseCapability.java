package net.endgineer.curseoftheabyss.common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.PacketDistributor;
import net.minecraft.server.level.ServerPlayer;
import net.endgineer.curseoftheabyss.core.ModVariables;
import net.endgineer.curseoftheabyss.network.CursePacket;
import net.endgineer.curseoftheabyss.network.PacketHandler;
import net.minecraft.nbt.CompoundTag;

public class CurseCapability implements Serializable {
    private double lowest_depth;
    private double previous_depth;
    private double derangement;
    private double constitution;
    private Strains strains;

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
    }

    public void tick(Player player) {
        if(this.constitution == 0) this.constitution = player.getMaxHealth();

        double x = player.getX(), y = player.getY(), z = player.getZ();

        Level level = player.getCommandSenderWorld();
        boolean overworld = level.dimension().location().getPath() == "overworld";
        
        double field = overworld ? Abyss.field(level.getServer().getLevel(level.dimension()).getSeed(), x, y, z, level.getGameTime(), level.getDayTime()) : 0;
        
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
}
