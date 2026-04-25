package com.srcfur.badhygiene.mixin;

import com.srcfur.badhygiene.api.HygieneAPI;
import com.srcfur.badhygiene.attributes.HygieneAttributes;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    @Shadow public abstract @Nullable AttributeInstance getAttribute(Holder<Attribute> holder);

    @Shadow public abstract float getMaxHealth();

    @Inject(at = @At("HEAD"), method="setHealth")
    void BadHygiene$setHealth(float health, CallbackInfo ci){
        AttributeInstance inst = this.getAttribute(HygieneAttributes.CONTINENCE);
        if(inst != null){
            float percenthealth = 1 - ((float)health / (float)this.getMaxHealth());
            inst.addOrReplacePermanentModifier(
                    new AttributeModifier(HygieneAPI.WEAK_BLADDER_HEALTH_ID, -percenthealth, AttributeModifier.Operation.ADD_MULTIPLIED_BASE)
            );
        }
    }
}
