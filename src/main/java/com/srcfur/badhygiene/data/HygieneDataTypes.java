package com.srcfur.badhygiene.data;

import com.mojang.serialization.Codec;
import com.srcfur.badhygiene.BadHygiene;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.network.codec.NeoForgeStreamCodecs;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

@Mod(BadHygiene.MODID)
public class HygieneDataTypes {
    public HygieneDataTypes(IEventBus modbus){
        DATA_COMPONENTS.register(modbus);
    }
    public static final DeferredRegister.DataComponents DATA_COMPONENTS = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, BadHygiene.MODID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Boolean>> HYGIENE_SOILED_CLOTHING = DATA_COMPONENTS.registerComponentType(
            "soiled",
            booleanBuilder -> booleanBuilder.networkSynchronized(ByteBufCodecs.BOOL).persistent(Codec.BOOL));
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> HYGIENE_DIET_EFFECT = DATA_COMPONENTS.registerComponentType(
      "dietary_score",
        integerBuilder -> integerBuilder.persistent(Codec.INT).networkSynchronized(ByteBufCodecs.INT));
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> HYGIENE_BOWEL_FILL = DATA_COMPONENTS.registerComponentType(
            "bowel_fullness",
            integerBuilder -> integerBuilder.persistent(Codec.INT).networkSynchronized(ByteBufCodecs.INT));
}
