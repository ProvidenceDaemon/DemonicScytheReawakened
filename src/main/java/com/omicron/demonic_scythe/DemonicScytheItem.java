package com.omicron.demonic_scythe;

import com.google.common.collect.Multimap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
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

    public static final String COOLDOWN = "cooldown";
    public static final String USE = "use";

    public DemonicScytheItem()
    {
        super(ToolMaterial.DIAMOND);
        this.setUnlocalizedName("demonic_scythe");
        this.setRegistryName(DemonicScythe.MODID, "demonic_scythe");
        this.setMaxStackSize(1);
    }

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
    {
        // Cooldown
        NBTTagCompound tag = new NBTTagCompound();
        if(stack.getTagCompound() != null)
            tag = stack.getTagCompound();
        else
            stack.setTagCompound(tag);
        int use = tag.getInteger(USE);

        if(use > 0)
        {
            tag.setInteger(USE, use - 1);
            stack.setTagCompound(tag);
        }
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
                if(duration > Config.ticksCorruption)
                {
                    PotionEffect effect = new PotionEffect(Registration.DEMONIC_CORRUPTION, Config.ticksAccumulate, amplifier + 1);
                    effect.setCurativeItems(new ArrayList<>());
                    player.addPotionEffect(effect);
                }
                else
                {
                    PotionEffect effect = new PotionEffect(Registration.DEMONIC_CORRUPTION, duration + Config.ticksAccumulate, amplifier);
                    effect.setCurativeItems(new ArrayList<>());
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
        ItemStack stack = player.getHeldItem(hand);
        NBTTagCompound tag = stack.getTagCompound();
        int use = tag.getInteger(USE);
        if(use <= 0)
        {
            player.swingArm(hand);
            tag.setInteger(USE, Config.demonicScytheCooldown);
            stack.setTagCompound(tag);
            player.setHeldItem(hand, stack);
            //player.setHeldItem(hand, new ItemStack(Items.ACACIA_BOAT));
            if(worldIn instanceof WorldServer)
                for(Entity entity : worldIn.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(player.getPosition(), player.getPosition()).grow(6.0)))
                    if(entity instanceof EntityLivingBase)
                    {
                        EntityLivingBase livingEntity = (EntityLivingBase) entity;
                        if(!entity.equals(player))
                            livingEntity.attackEntityFrom(DamageSource.causePlayerDamage(player), 7);
                    }
            return //EnumActionResult.SUCCESS;
            new ActionResult<>(EnumActionResult.SUCCESS, stack);
        }
        return //EnumActionResult.PASS;
                new ActionResult<>(EnumActionResult.PASS, stack);
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
