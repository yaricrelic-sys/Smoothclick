package com.sneakclick;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class SneakClickConfig {
    public static final ForgeConfigSpec SPEC;
    public static final SneakClickConfig INSTANCE;

    public final ForgeConfigSpec.BooleanValue sneakEnabled;
    public final ForgeConfigSpec.DoubleValue sneakSpeed;
    public final ForgeConfigSpec.DoubleValue sneakHeight;

    public final ForgeConfigSpec.BooleanValue clickerEnabled;
    public final ForgeConfigSpec.BooleanValue weaponOnly;
    public final ForgeConfigSpec.IntValue cps;
    public final ForgeConfigSpec.BooleanValue holdMode;
    public final ForgeConfigSpec.ConfigValue<String> activationKey;

    static {
        Pair<SneakClickConfig, ForgeConfigSpec> pair =
            new ForgeConfigSpec.Builder().configure(SneakClickConfig::new);
        INSTANCE = pair.getLeft();
        SPEC = pair.getRight();
    }

    private SneakClickConfig(ForgeConfigSpec.Builder builder) {
        builder.comment("Sneak & Click Mod").push("sneak");
        sneakEnabled = builder.comment("Enable smooth sneaking").define("enabled", true);
        sneakSpeed = builder.comment("Sneak transition speed 0.1 slow - 1.0 fast")
            .defineInRange("speed", 0.3, 0.1, 1.0);
        sneakHeight = builder.comment("Sneak camera drop height")
            .defineInRange("height", 0.3, 0.1, 0.5);
        builder.pop();

        builder.comment("Auto Clicker").push("clicker");
        clickerEnabled = builder.comment("Enable auto clicker").define("enabled", true);
        weaponOnly = builder.comment("Click only with a weapon (sword/axe)")
            .define("weaponOnly", true);
        cps = builder.comment("Clicks per second")
            .defineInRange("cps", 8, 1, 20);
        holdMode = builder.comment("Hold mode. false = toggle")
            .define("holdMode", true);
        activationKey = builder.comment("Activation key (e.g. R, C, V)")
            .define("key", "R");
        builder.pop();
    }
}
