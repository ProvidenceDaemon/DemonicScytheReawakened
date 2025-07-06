package com.omicron.demonic_scythe;

import com.google.common.collect.Multimap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import java.util.ArrayList;

public class DemonicScytheItem extends ItemSword {
    public DemonicScytheItem()
    {
        super(ToolMaterial.DIAMOND);
        this.setTranslationKey("demonic_scythe");
        this.setRegistryName(DemonicScythe.MODID, "demonic_scythe");
        this.setMaxStackSize(1);
    }

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
    {
        // Corruption
        if(entityIn != null && isSelected)
        {
            if(entityIn instanceof EntityPlayer)
            {
                EntityPlayer player = (EntityPlayer) entityIn;
                int duration = 0;
                int amplifier = 0;
                if(player.getActivePotionEffect(Registration.DEMONIC_CORRUPTION) != null)
                {
                    duration =  player.getActivePotionEffect(Registration.DEMONIC_CORRUPTION).getDuration();
                    amplifier = player.getActivePotionEffect(Registration.DEMONIC_CORRUPTION).getAmplifier();
                }
                if(duration > Config.ticksCorruption && player.getMaxHealth() > Config.minCorruptionHealth)
                {
                    PotionEffect effect = new PotionEffect(Registration.DEMONIC_CORRUPTION, Config.ticksAccumulate, amplifier + 1);
                    player.addPotionEffect(effect);
                }
                else
                {
                    PotionEffect effect = new PotionEffect(Registration.DEMONIC_CORRUPTION, duration + Config.ticksAccumulate, amplifier);
                    player.addPotionEffect(effect);
                }
            }
        }
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged)
    {
        return false; //!ItemStack.areItemStacksEqual(oldStack, newStack); n
    }

    @Override
    //public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand hand)
    {
        player.getCooldownTracker().setCooldown(this, Config.demonicScytheCooldown);
        player.playSound(SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, 1.0F, 0.6F );
        return SpinAttackHandler.SpinAttack(worldIn, player, hand, Config.spinAttackDamage);
    }

    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack)
    {
        Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(slot);

        if (slot == EntityEquipmentSlot.MAINHAND)
        {
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", 7, 0));
            multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", -2.4000000953674316D, 0));
        }

        return multimap;
    }
}
