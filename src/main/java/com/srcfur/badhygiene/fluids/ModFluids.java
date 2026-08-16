package com.srcfur.badhygiene.fluids;

import com.srcfur.badhygiene.BadHygiene;
import com.srcfur.badhygiene.fluids.fluidtype.UrineFluidType;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import org.joml.Vector3f;

public class ModFluids {
    public static final ResourceLocation WATER_STILL_RL = ResourceLocation.withDefaultNamespace("block/water_still");
    public static final ResourceLocation WATER_FLOWING_RL = ResourceLocation.withDefaultNamespace("block/water_flow");
    public static final DeferredRegister<Fluid> HYGIENE_FLUIDS = DeferredRegister.create(BuiltInRegistries.FLUID, BadHygiene.MODID);
    public static final DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(NeoForgeRegistries.FLUID_TYPES, BadHygiene.MODID);
    //public static final FluidType URINE_TYPE = new FluidType(FluidType.Properties.create().canDrown(true).canPushEntity(false));
    public static final DeferredHolder<FluidType, FluidType> URINE_TYPE = FLUID_TYPES.register("urine", () -> new UrineFluidType(WATER_STILL_RL, WATER_FLOWING_RL,
            0xDCF393, new Vector3f(220f / 255f, 243f / 255f, 147f / 255f), FluidType.Properties.create().canDrown(true)));
    public static final DeferredHolder<Fluid, FlowingFluid> URINE_FLOWING = HYGIENE_FLUIDS.register("urine_flowing", UrineFluid.Flowing::new);
    public static final DeferredHolder<Fluid, FlowingFluid> URINE_STILL = HYGIENE_FLUIDS.register("urine_still", UrineFluid.Source::new);
    private static <T extends Fluid> T register(String string, T fluid) {
        return Registry.register(BuiltInRegistries.FLUID, string, fluid);
    }
    public static void initialize(){

    }
}