package com.omicron.demonic_scythe;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.potion.Potion;

public class DemonicCorruption extends Potion {
    protected DemonicCorruption() {
        super(true, 9643043);
        this.setRegistryName(DemonicScythe.MODID, "demonic_corruption");
        this.setPotionName("effect.demonic_corruption");
        this.registerPotionAttributeModifier(SharedMonsterAttributes.MAX_HEALTH, "5D6F0BA2-1186-93AC-B896-C61C5CEE99C1", -2.0D, 0);
    }

    @Override
    public java.util.List<net.minecraft.item.ItemStack> getCurativeItems()
    {
        java.util.ArrayList<net.minecraft.item.ItemStack> ret = new java.util.ArrayList<>();
        return ret;
    }
}
