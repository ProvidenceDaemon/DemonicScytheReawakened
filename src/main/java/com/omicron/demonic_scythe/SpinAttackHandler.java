package com.omicron.demonic_scythe;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

public class SpinAttackHandler {
    public static ActionResult<ItemStack> SpinAttack(World worldIn, EntityPlayer player, EnumHand hand, int damage){
        ItemStack stack = player.getHeldItem(hand);
        player.setHeldItem(hand, stack);
        int healAmount = 0;
        for(Entity entity : worldIn.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(player.getPosition(), player.getPosition()).grow(6.0)))
            if(entity instanceof EntityLivingBase)
            {
                EntityLivingBase livingEntity = (EntityLivingBase) entity;
                if(!entity.equals(player))
                {
                    livingEntity.attackEntityFrom(DamageSource.causePlayerDamage(player), damage);
                    healAmount++;
                }
            }
        player.heal(healAmount * Config.healingPerTarget);
        return new ActionResult<>(EnumActionResult.SUCCESS, stack);

    }


}
