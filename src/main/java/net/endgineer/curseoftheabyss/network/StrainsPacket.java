package net.endgineer.curseoftheabyss.network;

import net.endgineer.curseoftheabyss.client.StrainsData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.api.distmarker.Dist;

public class StrainsPacket {
    public double progress_numbness;
    public double progress_deprivation;

    public StrainsPacket(double progress_numbness, double progress_deprivation) {
        this.progress_numbness = progress_numbness;
        this.progress_deprivation = progress_deprivation;
    }

    public static void encode(StrainsPacket message, FriendlyByteBuf buffer) {
        buffer.writeDouble(message.progress_numbness);
        buffer.writeDouble(message.progress_deprivation);
    }

    public static StrainsPacket decode(FriendlyByteBuf buffer) {
        return new StrainsPacket(buffer.readDouble(), buffer.readDouble());
    }

    public static void handle(StrainsPacket message, Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();

        if(context.getDirection().getReceptionSide().isClient()) {
            context.enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> StrainsData.update(message.progress_numbness, message.progress_deprivation)));
        }

        context.setPacketHandled(true);
    }
}
