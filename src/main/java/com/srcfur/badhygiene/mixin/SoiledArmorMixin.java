package com.srcfur.badhygiene.mixin;

import com.srcfur.badhygiene.BadHygiene;
import com.srcfur.badhygiene.api.HygieneAPI;
import com.srcfur.badhygiene.data.HygieneDataTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Item.class)
public class SoiledArmorMixin {
    private static final int ticks_per_damage_soiling = 20;
    @Inject(at = @At("HEAD"), method = "inventoryTick")
    private void badhygeine$inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected, CallbackInfo ci){
        if(level.isClientSide())
            return;
        if(stack.getOrDefault(HygieneDataTypes.HYGIENE_SOILED_CLOTHING, false) && level.getLevelData().getGameTime() % ticks_per_damage_soiling == 0){
            stack.setDamageValue(stack.getDamageValue() + 1);
            if(stack.getDamageValue() >= stack.getMaxDamage()){
                entity.sendSystemMessage(Component.empty().append(stack.getDisplayName()).append(Component.translatable("msg.badhygiene.soiled_death")));
                stack.setCount(0);
            }
        }
    }
}
