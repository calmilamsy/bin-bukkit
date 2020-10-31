package org.bukkit.craftbukkit.mixin;

import net.minecraft.block.BlockBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Random;

@Mixin(BlockBase.class)
public class MixinBlockBase {

    @Redirect(method = "beforeDestroyedByExplosion(Lnet/minecraft/level/Level;IIIIF)V", at = @At(value = "INVOKE", target = "Ljava/util/Random;nextFloat()F"))
    private float cb1(Random random) {
        return random.nextFloat() + 1;
    }
}
