package com.github.dragoni7.rpgskillable.common.capabilities;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

public class SkillProvider implements ICapabilitySerializable<CompoundTag> {
	
	private final SkillModel skillModel;
	private final LazyOptional<SkillModel> optional;
	
	public SkillProvider(SkillModel skillModel) {
		this.skillModel = skillModel;
		optional = LazyOptional.of(() -> skillModel);
	}
	
	public void invalidate() {
		optional.invalidate();
	}

	@Override
	public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
		return SkillCapability.SKILL_MODEL.orEmpty(cap, this.optional);
	}

	@Override
	public CompoundTag serializeNBT() {
		return skillModel.serializeNBT();
	}

	@Override
	public void deserializeNBT(CompoundTag nbt) {
		skillModel.deserializeNBT(nbt);
	}

}
