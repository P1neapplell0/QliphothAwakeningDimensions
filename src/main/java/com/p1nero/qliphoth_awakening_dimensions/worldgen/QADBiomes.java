package com.p1nero.qliphoth_awakening_dimensions.worldgen;

import com.p1nero.qliphoth_awakening_dimensions.QADimensionsMod;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.Musics;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.biome.*;

public class QADBiomes {

    public static final ResourceKey<Biome> EMPTY_1 = register("empty1");
    public static final ResourceKey<Biome> EMPTY_2 = register("empty2");

    public static ResourceKey<Biome> register(String name){
        return ResourceKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(QADimensionsMod.MOD_ID, name));
    }

    public static void boostrap(BootstapContext<Biome> context) {
        MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder();
        BiomeGenerationSettings.Builder biomeBuilder = new BiomeGenerationSettings.Builder(context.lookup(Registries.PLACED_FEATURE), context.lookup(Registries.CONFIGURED_CARVER));
        context.register(EMPTY_1, new Biome.BiomeBuilder()
                .hasPrecipitation(false)
                .downfall(0.0f)
                .temperature(2.0f)
                .generationSettings(biomeBuilder.build())
                .mobSpawnSettings(spawnBuilder.build())
                .specialEffects((new BiomeSpecialEffects.Builder())
                        .waterColor(4159204)
                        .waterFogColor(329011)
                        .fogColor(1705242)
                        .skyColor(1705242)
                        .ambientLoopSound(SoundEvents.AMBIENT_WARPED_FOREST_LOOP)
                        .ambientMoodSound(new AmbientMoodSettings(SoundEvents.AMBIENT_WARPED_FOREST_MOOD, 6000, 8, 2.0))
                        .ambientAdditionsSound(new AmbientAdditionsSettings(SoundEvents.AMBIENT_WARPED_FOREST_ADDITIONS, 0.0111))
                        .backgroundMusic(Musics.createGameMusic(SoundEvents.MUSIC_BIOME_CRIMSON_FOREST))
                        .build())
                .build());
        context.register(EMPTY_2, new Biome.BiomeBuilder()
                .hasPrecipitation(false)
                .downfall(0.0f)
                .temperature(2.0f)
                .generationSettings(biomeBuilder.build())
                .mobSpawnSettings(spawnBuilder.build())
                .specialEffects((new BiomeSpecialEffects.Builder())
                        .waterColor(4159204)
                        .waterFogColor(329011)
                        .fogColor(1705242)
                        .skyColor(1705242)
                        .ambientParticle(new AmbientParticleSettings(ParticleTypes.ASH, 0.01428F))
                        .ambientLoopSound(SoundEvents.AMBIENT_WARPED_FOREST_LOOP)
                        .ambientMoodSound(new AmbientMoodSettings(SoundEvents.AMBIENT_WARPED_FOREST_MOOD, 6000, 8, 2.0))
                        .ambientAdditionsSound(new AmbientAdditionsSettings(SoundEvents.AMBIENT_WARPED_FOREST_ADDITIONS, 0.0111))
                        .backgroundMusic(Musics.createGameMusic(SoundEvents.MUSIC_BIOME_CRIMSON_FOREST))
                        .build())
                .build());
    }

}
