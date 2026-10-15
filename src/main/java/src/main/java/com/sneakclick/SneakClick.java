package com.sneakclick;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

@Mod(SneakClick.MOD_ID)
public class SneakClick {
    public static final String MOD_ID = "sneakclick";

    public SneakClick() {
        ModLoadingContext.get().registerConfig(
            ModConfig.Type.CLIENT,
            SneakClickConfig.SPEC,
            "sneakclick-client.toml"
        );
        MinecraftForge.EVENT_BUS.register(new SneakHandler());
        MinecraftForge.EVENT_BUS.register(new ClickHandler());
    }
}
