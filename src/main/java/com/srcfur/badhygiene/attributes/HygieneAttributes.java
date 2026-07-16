package com.srcfur.badhygiene.attributes;

import com.srcfur.badhygiene.BadHygiene;
import com.srcfur.badhygiene.effects.IncontinenceEffect;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.awt.*;

import static com.srcfur.badhygiene.BadHygiene.MODID;

public class HygieneAttributes {
    public static DeferredHolder<Attribute, Attribute> CONTINENCE;
    public static DeferredHolder<MobEffect, MobEffect> INCONTINENCE_EFFECT;
    public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(
            BuiltInRegistries.ATTRIBUTE,
            MODID
    );
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(
            BuiltInRegistries.MOB_EFFECT,
            MODID
    );
    public static void initialize(){
        CONTINENCE = ATTRIBUTES.register("hygieneattributes", () -> new RangedAttribute("", 100,10,200));
        INCONTINENCE_EFFECT = EFFECTS.register("incontinence", () -> new IncontinenceEffect(MobEffectCategory.HARMFUL, Color.black.getRGB())
                        .addAttributeModifier(CONTINENCE, ResourceLocation.fromNamespaceAndPath(MODID, "incontinence"), -0.3f, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
    }
}
