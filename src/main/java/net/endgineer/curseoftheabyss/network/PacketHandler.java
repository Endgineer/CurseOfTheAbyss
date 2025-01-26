package net.endgineer.curseoftheabyss.network;

import net.endgineer.curseoftheabyss.CurseOfTheAbyss;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class PacketHandler {
    private static final String PROTOCOL_VERSION = "1.0";

    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(CurseOfTheAbyss.MODID, "abyss_leylines"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    public static void init() {
        INSTANCE.registerMessage(0, CursePacket.class, CursePacket::encode, CursePacket::decode, CursePacket::handle);
        INSTANCE.registerMessage(1, StrainsPacket.class, StrainsPacket::encode, StrainsPacket::decode, StrainsPacket::handle);
    }
}
