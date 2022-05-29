package com.omicron.demonic_scythe;

import net.minecraftforge.common.config.Configuration;

public class Config {

    private static final String CATEGORY_GENERAL = "general";

    // This values below you can access elsewhere in your mod:
    public static int demonicScytheCooldown;
    public static int ticksCorruption;
    public static float healingPerTarget;
    public static int ticksAccumulate;

    // Call this from CommonProxy.preInit(). It will create our config if it doesn't
    // exist yet and read the values if it does exist.
    public static void readConfig() {
        Configuration cfg = Registration.config;
        try {
            cfg.load();
            initGeneralConfig(cfg);
        } catch (Exception e1) {
            //ModTut.logger.log(Level.ERROR, "Problem loading config file!", e1);
        } finally {
            if (cfg.hasChanged()) {
                cfg.save();
            }
        }
    }

    private static void initGeneralConfig(Configuration cfg) {
        cfg.addCustomCategoryComment(CATEGORY_GENERAL, "General configuration");
        // cfg.getBoolean() will get the value in the config if it is already specified there. If not it will create the value.
        demonicScytheCooldown = cfg.getInt("demonicScytheCooldown", CATEGORY_GENERAL, 40, 5, Integer.MAX_VALUE, "Cooldown of the spin attack in ticks");
        ticksCorruption = cfg.getInt("secondsCorruption", CATEGORY_GENERAL, 200, 1, Integer.MAX_VALUE, "how many ticks of corruption per level of debuff");
        healingPerTarget = cfg.getFloat("healingPerTarget", CATEGORY_GENERAL, 1, Float.MIN_VALUE, Float.MIN_VALUE, "how much awakened scythe spin attack heals per target hit");
        ticksAccumulate = cfg.getInt("ticksAccumulate", CATEGORY_GENERAL, 2, 1, Integer.MAX_VALUE, "how many ticks of debuff is applied per tick of holding a scythe");
    }
}
