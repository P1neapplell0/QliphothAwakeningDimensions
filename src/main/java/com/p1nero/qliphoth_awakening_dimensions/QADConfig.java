package com.p1nero.qliphoth_awakening_dimensions;


import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

@EventBusSubscriber(modid = QADimensionsMod.MOD_ID)
public class QADConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    private static final ModConfigSpec.BooleanValue ENABLE_TELEPORT_EYE = BUILDER.define("enable_teleport_eye", true);
    private static final ModConfigSpec.IntValue TELEPORT_EYE_COOLDOWN = BUILDER.defineInRange("teleport_eye_cooldown", 600, 0, Integer.MAX_VALUE);

    static final ModConfigSpec SPEC = BUILDER.build();

    public static boolean enableTeleportEye;
    public static int teleportEyeCooldown;

    @SubscribeEvent
    public static void qad$onConfigLoad(ModConfigEvent event) {
        enableTeleportEye = ENABLE_TELEPORT_EYE.get();
        teleportEyeCooldown = TELEPORT_EYE_COOLDOWN.get();
    }

}
