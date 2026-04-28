package com.srcfur.badhygiene.blocks;

import com.srcfur.badhygiene.BadHygiene;
import com.srcfur.badhygiene.blocks.custom.AbstractToiletBlock;
import com.srcfur.badhygiene.blocks.entities.AbstractToiletBlockEntity;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.capabilities.BlockCapability;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;

import javax.annotation.Nullable;

@SuppressWarnings("ALL")
public class ModBlockCapabilities {
    public static final BlockCapability<IFluidHandler, Void> POTTY_HANDLER =
            BlockCapability.createVoid(
                    Identifier.fromNamespaceAndPath(BadHygiene.MODID, "potty_handler"),
                    IFluidHandler.class
            );
    @SubscribeEvent  // on the mod event bus
    public void registerCapabilities(RegisterCapabilitiesEvent event) {
        //event.registerBlockEntity(
        //        Capabilities.FluidHandler.BLOCK, // capability to register for
        //        ModBlockEntities.TOILET_ENTITY.get(), // block entity type to register for
        //        (myBlockEntity, side) -> myBlockEntity
        //);
        for(RegisteredPotty<AbstractToiletBlock, AbstractToiletBlockEntity> potty : RegisteredPotty.allRegisteredPotties){
            //event.registerBlockEntity(
            //        Capabilities.Fluid.BLOCK,
            //        potty.ENTITY.get(),
            //        (myblockentity, side) -> myblockentity
            //);
        }
        BadHygiene.LOGGER.info("Registered Fluid Capabilities for BadHygiene");
    }
    public static void initialize(){

    }
}
