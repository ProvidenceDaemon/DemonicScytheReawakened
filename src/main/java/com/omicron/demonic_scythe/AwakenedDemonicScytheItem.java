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
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class AwakenedDemonicScytheItem extends ItemSword {

    public AwakenedDemonicScytheItem()
    {
        super(ToolMaterial.DIAMOND);
        this.setTranslationKey("awakened_demonic_scythe");
        this.setRegistryName(DemonicScythe.MODID, "awakened_demonic_scythe");
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
                if(duration > Config.ticksCorruptionAwakened && player.getMaxHealth() > Config.minCorruptionHealth)
                {
                    player.addPotionEffect(new PotionEffect(Registration.DEMONIC_CORRUPTION, Config.ticksAccumulateAwakened, amplifier + 1));
                }
                else
                    player.addPotionEffect(new PotionEffect(Registration.DEMONIC_CORRUPTION, duration + Config.ticksAccumulateAwakened, amplifier));
            }
        }
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged)
    {
        return false;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand hand)
    {
        player.getCooldownTracker().setCooldown(this, Config.demonicScytheCooldownAwakened);
        player.playSound(SoundEvents.ENTITY_WITHER_AMBIENT, 0.8F, 0.4F );
        return SpinAttackHandler.SpinAttack(worldIn, player, hand, Config.awakenedSpinAttackDamage);
    }

    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack)
    {
        Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(slot);

        if (slot == EntityEquipmentSlot.MAINHAND)
        {
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", 9, 0));
            multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", -2.4000000953674316D, 0));
        }

        return multimap;
    }
}
