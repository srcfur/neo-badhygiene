package com.srcfur.badhygiene.attachments;

import com.mojang.serialization.Codec;
import com.srcfur.badhygiene.BadHygiene;
import com.srcfur.badhygiene.api.HygieneAPI;
import com.srcfur.badhygiene.api.HygieneIntegerStreamCodec;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class HygienePlayerAttachment {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENTS = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, BadHygiene.MODID);
    public static final Supplier<AttachmentType<Integer>> BLADDER_LEVEL = ATTACHMENTS.register("bladderlevel",
            () -> AttachmentType.<Integer>builder(()->0).serialize(Codec.INT.fieldOf("bladder")).sync(new HygieneIntegerStreamCodec()).build());
    public static final Supplier<AttachmentType<Integer>> CONTINENCE_LEVEL = ATTACHMENTS.register("continence",
            () -> AttachmentType.<Integer>builder(()->100).serialize(Codec.INT.fieldOf("continence")).sync(new HygieneIntegerStreamCodec()).build());
    public static final Supplier<AttachmentType<Integer>> CLEAN_LEVEL = ATTACHMENTS.register("cleanliness",
            () -> AttachmentType.<Integer>builder(()-> HygieneAPI.MAX_CLEAN_STAT).serialize(Codec.INT.fieldOf("cleanliness")).sync(new HygieneIntegerStreamCodec()).build());
    public static void initialize(){
    }
}
