package com.p1nero.qliphoth_awakening_dimensions.worldgen;

import com.p1nero.qliphoth_awakening_dimensions.QADimensionsMod;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.FixedBiomeSource;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;

import java.util.OptionalLong;
import java.util.Set;

public class QADDimensions {

    public static final ResourceKey<Level> CHESED_LEVEL_KEY = ResourceKey.create(Registries.DIMENSION,
            ResourceLocation.fromNamespaceAndPath(QADimensionsMod.MOD_ID, "chesed_dim"));
    public static final ResourceKey<LevelStem> CHESED_KEY = ResourceKey.create(Registries.LEVEL_STEM,
            ResourceLocation.fromNamespaceAndPath(QADimensionsMod.MOD_ID, "chesed_dim"));
    public static final ResourceKey<DimensionType> CHESED_TYPE = ResourceKey.create(Registries.DIMENSION_TYPE,
            ResourceLocation.fromNamespaceAndPath(QADimensionsMod.MOD_ID, "chesed_dim_type"));

    public static final ResourceKey<Level> MALKUTH_LEVEL_KEY = ResourceKey.create(Registries.DIMENSION,
            ResourceLocation.fromNamespaceAndPath(QADimensionsMod.MOD_ID, "malkuth_dim"));
    public static final ResourceKey<LevelStem> MALKUTH_KEY = ResourceKey.create(Registries.LEVEL_STEM,
            ResourceLocation.fromNamespaceAndPath(QADimensionsMod.MOD_ID, "malkuth_dim"));
    public static final ResourceKey<DimensionType> MALKUTH_TYPE = ResourceKey.create(Registries.DIMENSION_TYPE,
            ResourceLocation.fromNamespaceAndPath(QADimensionsMod.MOD_ID, "malkuth_dim_type"));

    public static final Set<ResourceKey<Level>> LEVELS = Set.of(CHESED_LEVEL_KEY, MALKUTH_LEVEL_KEY);

    public static void bootstrapType(BootstapContext<DimensionType> context) {
        context.register(CHESED_TYPE,
                new DimensionType(
                    OptionalLong.empty(),       // 跟随主世界时间
                    true,                       // 有天空光照
                    false,                      // 无天花板
                    false,                      // 非超高温
                    false,                       // 自然生成结构
                    1.0,                        // 正常坐标缩放
                    false,                       // 床安全
                    false,                      // 重生锚有效
                    0,                        // 最低Y层
                    320,                        // 总高度
                    256,                        // 逻辑高度
                    BlockTags.INFINIBURN_OVERWORLD,
                    BuiltinDimensionTypes.OVERWORLD_EFFECTS,
                        0.5F,                       //自然光
                    new DimensionType.MonsterSettings(false, false, ConstantInt.of(0), 0)
                )
        );
        context.register(MALKUTH_TYPE,
                new DimensionType(
                        OptionalLong.empty(),       // 跟随主世界时间
                        true,                       // 有天空光照
                        false,                      // 无天花板
                        false,                      // 非超高温
                        false,                       // 自然生成结构
                        1.0,                        // 正常坐标缩放
                        false,                       // 床安全
                        false,                      // 重生锚有效
                        0,                        // 最低Y层
                        320,                        // 总高度
                        256,                        // 逻辑高度
                        BlockTags.INFINIBURN_OVERWORLD,
                        BuiltinDimensionTypes.OVERWORLD_EFFECTS,
                        0.5F,                       //自然光
                        new DimensionType.MonsterSettings(false, false, ConstantInt.of(0), 0)
                )
        );
    }

    public static void bootstrapStem(BootstapContext<LevelStem> context) {
        HolderGetter<Biome> biomeRegistry = context.lookup(Registries.BIOME);
        HolderGetter<DimensionType> dimTypes = context.lookup(Registries.DIMENSION_TYPE);
        HolderGetter<NoiseGeneratorSettings> noiseGenSettings = context.lookup(Registries.NOISE_SETTINGS);
        NoiseBasedChunkGenerator chunkGenerator = new NoiseBasedChunkGenerator(new FixedBiomeSource(biomeRegistry.getOrThrow(QADBiomes.EMPTY_1)), noiseGenSettings.getOrThrow(QADNoiseSettings.BEDROCK));
        LevelStem levelStem1 = new LevelStem(dimTypes.getOrThrow(QADDimensions.CHESED_TYPE), chunkGenerator);
        context.register(CHESED_KEY, levelStem1);
        NoiseBasedChunkGenerator chunkGenerator2 = new NoiseBasedChunkGenerator(new FixedBiomeSource(biomeRegistry.getOrThrow(QADBiomes.EMPTY_2)), noiseGenSettings.getOrThrow(QADNoiseSettings.SEA));
        LevelStem levelStem2 = new LevelStem(dimTypes.getOrThrow(QADDimensions.MALKUTH_TYPE), chunkGenerator2);
        context.register(MALKUTH_KEY, levelStem2);
    }
}