package com.omicron.demonic_scythe;

import net.minecraftforge.common.config.Configuration;

public class Config {

    private static final String CATEGORY_GENERAL = "general";

    // This values below you can access elsewhere in your mod:
    public static int demonicScytheCooldown;
    public static int ticksCorruption;
    public static int healingPerTarget;
    public static int ticksAccumulate;

    public static int demonicScytheCooldownAwakened;
    public static int ticksCorruptionAwakened;
    public static int ticksAccumulateAwakened;

    public static int minCorruptionHealth;

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
        ticksAccumulate = cfg.getInt("ticksAccumulate", CATEGORY_GENERAL, 2, 1, Integer.MAX_VALUE, "how many ticks of debuff is applied per tick of holding a scythe");

        healingPerTarget = cfg.getInt("healingPerTarget", CATEGORY_GENERAL, 2, 0, Integer.MAX_VALUE, "how much awakened scythe spin attack heals per target hit");
        demonicScytheCooldownAwakened = cfg.getInt("demonicScytheCooldownAwakened", CATEGORY_GENERAL, 30, 5, Integer.MAX_VALUE, "Cooldown of the spin attack in ticks - awakened");
        ticksCorruptionAwakened = cfg.getInt("secondsCorruptionAwakened", CATEGORY_GENERAL, 160, 1, Integer.MAX_VALUE, "how many ticks of corruption per level of debuff - awakened");
        ticksAccumulateAwakened = cfg.getInt("ticksAccumulateAwakened", CATEGORY_GENERAL, 4, 1, Integer.MAX_VALUE, "how many ticks of debuff is applied per tick of holding a scythe - awakened");

        minCorruptionHealth = cfg.getInt("minCorruptionHealth", CATEGORY_GENERAL, 2, 2, Integer.MAX_VALUE, "The Demonic Corruption debuff will not cause the player to go below this amount of health");
    }
}
