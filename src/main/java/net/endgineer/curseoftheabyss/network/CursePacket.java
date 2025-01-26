package net.endgineer.curseoftheabyss.network;

import net.endgineer.curseoftheabyss.client.CurseData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.api.distmarker.Dist;

public class CursePacket {
    public double field;

    public CursePacket(double field) {
        this.field = field;
    }

    public static void encode(CursePacket message, FriendlyByteBuf buffer) {
        buffer.writeDouble(message.field);
    }

    public static CursePacket decode(FriendlyByteBuf buffer) {
        return new CursePacket(buffer.readDouble());
    }

    public static void handle(CursePacket message, Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();

        if(context.getDirection().getReceptionSide().isClient()) {
            context.enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> CurseData.update(message.field)));
        }

        context.setPacketHandled(true);
    }
}
