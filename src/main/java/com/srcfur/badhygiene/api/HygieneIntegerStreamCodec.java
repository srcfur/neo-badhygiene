package com.srcfur.badhygiene.api;

import com.mojang.serialization.Codec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.attachment.AttachmentSyncHandler;
import net.neoforged.neoforge.attachment.IAttachmentHolder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/// THIS IS NOT A STREAM CODEC!!!
public class HygieneIntegerStreamCodec implements AttachmentSyncHandler<Integer> {
    @Override
    public boolean sendToPlayer(@NotNull IAttachmentHolder holder, @NotNull ServerPlayer to) {
        return holder == to;
    }

    @Override
    public void write(@NotNull RegistryFriendlyByteBuf registryFriendlyByteBuf, @NotNull Integer integer, boolean b) {
        registryFriendlyByteBuf.writeInt(integer);
    }

    @Override
    public @Nullable Integer read(@NotNull IAttachmentHolder iAttachmentHolder, @NotNull RegistryFriendlyByteBuf registryFriendlyByteBuf, @Nullable Integer integer) {
        return registryFriendlyByteBuf.readInt();
    }
}