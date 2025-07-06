package com.omicron.demonic_scythe;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.ArrayList;

public class DemonicCorruption extends Potion {
    public static final ResourceLocation CORRUPTION_ICON = new ResourceLocation(DemonicScythe.MODID, "textures/gui/demonic_corruption.png");
    protected DemonicCorruption() {
        super(true, 9643043);
        this.setRegistryName(DemonicScythe.MODID, "demonic_corruption");
        this.setPotionName("effect.demonic_corruption");
        this.registerPotionAttributeModifier(SharedMonsterAttributes.MAX_HEALTH, "5D6F0BA2-1186-93AC-B896-C61C5CEE99C1", -1.0D, 0);
    }

    @Override
    @Nonnull
    public java.util.List<net.minecraft.item.ItemStack> getCurativeItems()
    {
        return new ArrayList<>();
    }
    //Rendering code from ACGaming's Default Potion Tweaks
    @Override
    @SideOnly(Side.CLIENT)
    public void renderInventoryEffect(PotionEffect effect, Gui gui, int x, int y, float z)
    {
        Minecraft.getMinecraft().renderEngine.bindTexture(CORRUPTION_ICON);
        Gui.drawModalRectWithCustomSizedTexture(x + 6, y + 7, 0, 0, 18, 18, 18, 18);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void renderHUDEffect(PotionEffect effect, Gui gui, int x, int y, float z, float alpha)
    {
        Minecraft.getMinecraft().renderEngine.bindTexture(CORRUPTION_ICON);
        Gui.drawModalRectWithCustomSizedTexture(x + 3, y + 3, 0, 0, 18, 18, 18, 18);
    }
}
