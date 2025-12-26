package com.moepus.fakesight.mixin;

import com.moepus.fakesight.SharedConstant;
import net.caffeinemc.mods.sodium.client.render.chunk.RenderSectionManager;
import net.caffeinemc.mods.sodium.client.render.chunk.map.ChunkTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = RenderSectionManager.class, remap = false)
public abstract class RenderSectionManagerMixin {
    @Inject(method = "onChunkAdded", at = @At(value = "HEAD"), require = 0, cancellable = true)
    private void moepus$onChunkAdd(int x, int z, CallbackInfo ci) {
        Entity camera = Minecraft.getInstance().getCameraEntity();
        if (camera == null) {
            return;
        }

        int camChunkX = Mth.floor(camera.getX()) >> 4;
        int camChunkZ = Mth.floor(camera.getZ()) >> 4;

        int dx = x - camChunkX;
        int dz = z - camChunkZ;

        if (dx * dx + dz * dz > (SharedConstant.renderDistance + 1) * (SharedConstant.renderDistance + 1) * 2) {
            ci.cancel();
        }
    }
}
