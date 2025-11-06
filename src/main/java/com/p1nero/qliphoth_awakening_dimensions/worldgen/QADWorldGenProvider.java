package com.p1nero.qliphoth_awakening_dimensions.worldgen;

import com.p1nero.qliphoth_awakening_dimensions.QADimensionsMod;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class QADWorldGenProvider extends DatapackBuiltinEntriesProvider {

    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.DIMENSION_TYPE, QADDimensions::bootstrapType)
            .add(Registries.NOISE_SETTINGS, QADNoiseSettings::bootstrap)
            .add(Registries.BIOME, QADBiomes::boostrap)
            .add(Registries.LEVEL_STEM, QADDimensions::bootstrapStem);

    public QADWorldGenProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(QADimensionsMod.MOD_ID));
    }
}