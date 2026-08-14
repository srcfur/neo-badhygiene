package com.srcfur.badhygiene.items;

import com.srcfur.badhygiene.api.HygieneAPI;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class LaxativeCookie extends Item {
    public LaxativeCookie(Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
        HygieneAPI.setBowelLevel((Player)livingEntity, 1000);
        return super.finishUsingItem(stack, level, livingEntity);
    }
}
