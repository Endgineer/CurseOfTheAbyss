package net.endgineer.curseoftheabyss.mixin;

/*
 * Used directly from https://github.com/CreativeMD/EnhancedVisuals/blob/1.20 by CreativeMD (GNU Lesser General Public License v3.0)
 * 
 * GNU Lesser General Public License v3.0
 * 
 * Copyright (c) 2023 CreativeMD
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

import java.util.Map;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.ChannelAccess;
import net.minecraft.client.sounds.SoundEngine;

@Mixin(SoundEngine.class)
public interface SoundEngineAccessor {
	  @Accessor
		Map<SoundInstance, ChannelAccess.ChannelHandle> getInstanceToChannel();
		
		@Invoker
		float invokeCalculateVolume(SoundInstance soundInstance);
}
