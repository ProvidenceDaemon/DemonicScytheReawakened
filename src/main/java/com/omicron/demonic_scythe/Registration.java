package com.omicron.demonic_scythe;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.living.PotionEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = DemonicScythe.MODID)
public class Registration {

    public static Configuration config;

    public static DemonicScytheItem DEMONIC_SCYTHE;

    public static AwakenedDemonicScytheItem AWAKENED_DEMONIC_SCYTHE;

    public static DemonicCorruption DEMONIC_CORRUPTION;

    public static void init()
    {
        DEMONIC_SCYTHE = new DemonicScytheItem();
        AWAKENED_DEMONIC_SCYTHE = new AwakenedDemonicScytheItem();
        DEMONIC_CORRUPTION = new DemonicCorruption();
    }

    @SubscribeEvent
    public static void registerPotions(RegistryEvent.Register<Potion> event)
    {
        event.getRegistry().register(DEMONIC_CORRUPTION);
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event)
    {
        event.getRegistry().register(DEMONIC_SCYTHE);
        event.getRegistry().register(AWAKENED_DEMONIC_SCYTHE);
    }

    @SubscribeEvent
    public static void registerRenderers(ModelRegistryEvent event)
    {
        ModelLoader.setCustomModelResourceLocation(DEMONIC_SCYTHE, 0, new ModelResourceLocation(DEMONIC_SCYTHE.getRegistryName(), "inventory"));
        ModelLoader.setCustomModelResourceLocation(AWAKENED_DEMONIC_SCYTHE, 0, new ModelResourceLocation(AWAKENED_DEMONIC_SCYTHE.getRegistryName(), "inventory"));
    }

    @SubscribeEvent
    public static void onPotionExpire(PotionEvent.PotionExpiryEvent event)
    {
        if(event.getPotionEffect().getPotion() == DEMONIC_CORRUPTION && event.getPotionEffect().getAmplifier() > 0)
        {
            event.getEntityLiving().removePotionEffect(DEMONIC_CORRUPTION);
            event.getEntityLiving().addPotionEffect(new PotionEffect(DEMONIC_CORRUPTION, Config.ticksCorruption, event.getPotionEffect().getAmplifier() - 1));
        }

    }
}
