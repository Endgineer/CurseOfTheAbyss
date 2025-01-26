package net.endgineer.curseoftheabyss.common;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CurseProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<CurseCapability> CURSE = CapabilityManager.get(new CapabilityToken<CurseCapability>() {});

    private CurseCapability curse = null;
    private final LazyOptional<CurseCapability> optional = LazyOptional.of(this::spawn);

    private CurseCapability spawn() {
        if(this.curse == null) {
            this.curse = new CurseCapability();
        }

        return this.curse;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> capability, @Nullable Direction side) {
        if(capability == CURSE) {
            return optional.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        spawn().save(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        spawn().load(nbt);
    }
}
