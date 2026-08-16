package com.srcfur.badhygiene.fluids.fluidtype;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.fog.FogRenderer;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.fluids.FluidType;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public class UrineFluidType extends FluidType {
    private final Identifier stillTexture;
    private final Identifier flowingTexture;
    private final int tintColor;
    private final Vector3f fogColor;

    public UrineFluidType(final Identifier stillTexture, final Identifier flowingTexture,
                          final int tintColor, final Vector3f fogColor, final Properties properties) {
        super(properties);
        this.stillTexture = stillTexture;
        this.flowingTexture = flowingTexture;
        this.tintColor = tintColor;
        this.fogColor = fogColor;
    }

    public Identifier getStillTexture() {
        return stillTexture;
    }

    public Identifier getFlowingTexture() {
        return flowingTexture;
    }

    public int getTintColor() {
        return tintColor;
    }

    public Identifier getOverlayTexture() {
        return null;
    }

    public Vector3f getFogColor() {
        return fogColor;
    }
}
