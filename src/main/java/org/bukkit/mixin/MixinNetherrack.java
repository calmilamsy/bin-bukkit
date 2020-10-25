package org.bukkit.mixin;

import net.minecraft.block.BlockBase;
import net.minecraft.block.Netherrack;
import net.minecraft.block.material.Material;
import net.minecraft.level.Level;
import org.bukkit.block.Block;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Netherrack.class)
public class MixinNetherrack extends BlockBase {

    public MixinNetherrack(int i, int j) {
        super(i, j, Material.STONE);
    }

    @Override
    public void onAdjacentBlockUpdate(Level level, int x, int y, int z, int id) {
        if (BY_ID[id] != null && BY_ID[id].emitsRedstonePower()) {
            Block block = level.
        }
    }
}
