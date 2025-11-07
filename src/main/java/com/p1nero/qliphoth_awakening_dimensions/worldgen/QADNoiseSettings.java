package com.p1nero.qliphoth_awakening_dimensions.worldgen;

import com.p1nero.qliphoth_awakening_dimensions.QADimensionsMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.*;

import java.util.List;

public class QADNoiseSettings {
    public static final ResourceKey<NoiseGeneratorSettings> BEDROCK = createNoiseGeneratorKey("bedrock");
    public static final ResourceKey<NoiseGeneratorSettings> SEA = createNoiseGeneratorKey("sea");

    private static ResourceKey<NoiseGeneratorSettings> createNoiseGeneratorKey(String name) {
        return ResourceKey.create(Registries.NOISE_SETTINGS, ResourceLocation.fromNamespaceAndPath(QADimensionsMod.MOD_ID, name));
    }

    public static void bootstrap(BootstrapContext<NoiseGeneratorSettings> context) {
        context.register(BEDROCK, new NoiseGeneratorSettings(
                new NoiseSettings(0, 128, 1, 1),
                Blocks.BEDROCK.defaultBlockState(),
                Blocks.BEDROCK.defaultBlockState(),
                new NoiseRouter(DensityFunctions.zero(), DensityFunctions.zero(), DensityFunctions.zero(), DensityFunctions.zero(), DensityFunctions.zero(), DensityFunctions.zero(), DensityFunctions.zero(), DensityFunctions.zero(), DensityFunctions.zero(), DensityFunctions.zero(), DensityFunctions.zero(), DensityFunctions.zero(), DensityFunctions.zero(), DensityFunctions.zero(), DensityFunctions.zero()),
                SurfaceRules.state(Blocks.BEDROCK.defaultBlockState()),
                List.of(),
                256,
                true,
                false,
                false,
                true));
        context.register(SEA, new NoiseGeneratorSettings(
                new NoiseSettings(0, 128, 1, 1),
                Blocks.WATER.defaultBlockState(),
                Blocks.WATER.defaultBlockState(),
                new NoiseRouter(DensityFunctions.zero(), DensityFunctions.zero(), DensityFunctions.zero(), DensityFunctions.zero(), DensityFunctions.zero(), DensityFunctions.zero(), DensityFunctions.zero(), DensityFunctions.zero(), DensityFunctions.zero(), DensityFunctions.zero(), DensityFunctions.zero(), DensityFunctions.zero(), DensityFunctions.zero(), DensityFunctions.zero(), DensityFunctions.zero()),
                SurfaceRules.state(Blocks.AIR.defaultBlockState()),
                List.of(),
                63,
                true,
                false,
                false,
                true));
    }
}
