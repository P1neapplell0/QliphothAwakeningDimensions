package com.p1nero.qliphoth_awakening_dimensions.placement;

import com.p1nero.qliphoth_awakening_dimensions.QADimensionsMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacementType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class QADPlacementTypes {
    public static final DeferredRegister<StructurePlacementType<?>> STRUCTURE_PLACEMENT_TYPES = DeferredRegister.create(Registries.STRUCTURE_PLACEMENT, QADimensionsMod.MOD_ID);
    public static final RegistryObject<StructurePlacementType<O_OPosPlacement>> SPAWN_POS_PLACEMENT =
            STRUCTURE_PLACEMENT_TYPES.register("0_0_placement", () -> O_OPosPlacement.CODEC::codec);
}
