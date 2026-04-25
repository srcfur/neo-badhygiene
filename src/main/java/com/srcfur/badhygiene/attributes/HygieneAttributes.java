package com.srcfur.badhygiene.attributes;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.srcfur.badhygiene.BadHygiene.MODID;

public class HygieneAttributes {
    public static Holder<Attribute> CONTINENCE;
    public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(
            BuiltInRegistries.ATTRIBUTE,
            MODID
    );
    public static void initialize(){
        CONTINENCE = ATTRIBUTES.register("hygieneattributes", () -> new RangedAttribute("", 0,0,0));
    }
}
